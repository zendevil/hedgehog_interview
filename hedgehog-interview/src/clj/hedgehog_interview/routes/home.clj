(ns hedgehog-interview.routes.home
  (:require
   [hedgehog-interview.layout :as layout]
   [clojure.java.io :as io]
   [hedgehog-interview.middleware :as middleware]
   [ring.util.response :refer [response]]
   [ring.util.http-response :as response]
   [aleph.http :as http]
   [manifold.stream :as s]
   [manifold.deferred :as d]
   [cheshire.core :refer [generate-string]]
   [clj-http.client :as client]
   ))


(defn ws-conn
  "Open a WebSocket connection with the given handlers.

  All handlers take [sock msg] except for :on-connect, which only takes [sock]
  -- `sock` being the duplex stream.

  - :on-connect   Called once when the connection is established.
  - :on-msg       Called with each message.
  - :on-close     Called once upon socket close with a map {:stat _, :desc _}.

  The optional :aleph parameter is configuration to pass through to Aleph's
  WebSocket client."
  [uri & {:keys [on-connect on-msg on-close aleph]}]
  (let [sock (http/websocket-client uri aleph)
        handle-messages (fn [sock]
                          (d/chain
                           (s/consume (fn [msg] (on-msg sock msg)) sock)
                           (fn [sock-closed] sock)))
        handle-shutdown (fn [sock]
                          (let [state (:sink (s/description sock))]
                            (on-close
                             sock {:stat (:websocket-close-code state)
                                   :desc (:websocket-close-msg state)})))]
    (d/chain sock #(doto % on-connect) handle-messages handle-shutdown)
    @sock))

(defn ws-send [sock msg] (s/put! sock msg))

(defn ws-close [sock] (s/close! sock))

(defn on-connect [sock] (prn "connected"))

(defonce sockets (atom #{}))

(defn on-msg [sock msg]
  (prn ">" msg)
  (doall (map #(ws-send (:socket %) msg) @sockets))
  )

(defn on-close [sock msg] (prn "Closed:" msg))


#_(defn get-client-ip [req]
  (if-let [ips (get-in req [:headers "x-forwarded-for"])]
    (-> ips (clojure.string/split #",") first)
    (:remote-addr req)))

(defn toggle-streaming [{:keys [path-params] :as request}]
  (prn "request is " request)
  (let [toggle-pair (:pair path-params)
        client-id (get (:headers request) "client-id")
        old-conn (filter
                  #(= (-> % :client-id) client-id
                     ) @sockets)
        ]
    (prn "client-id is " client-id)
    (if (seq old-conn)
      (let [old-conn- (nth old-conn 0)]
        (swap! sockets disj old-conn-)
        (swap! sockets conj (if (contains?
                                 (:pairs old-conn-)
                                 toggle-pair)
                              (do
                                (ws-send (:coinbase-socket old-conn-)
                                         (generate-string
                                          {:type "unsubscribe"
                                           :product_ids [(:pair path-params)]
                                           :channels ["heartbeat"]}))
                                (assoc old-conn- :pairs
                                     (disj (:pairs old-conn-)
                                           toggle-pair)))
                              (do
                                (ws-send (:coinbase-socket old-conn-)
                                         (generate-string
                                          {:type "subscribe"
                                           :product_ids [(:pair path-params)]
                                           :channels ["heartbeat"]}))
                                (assoc old-conn- :pairs
                                     (conj (:pairs old-conn-)
                                           toggle-pair)))))))
  (response "")
  ))

(defn uppercase-handler
  "Handle a message by upper casing it and echoing it back."
  [socket msg]
  (s/put! socket (clojure.string/upper-case msg)))

(defn server-handler [req]
  (prn "req is!! " req)
  
  (let [client-id (get
                   (:headers req)
                   "client-id")
        old-conn (filter #(= (:client-id %) client-id) @sockets)]
    (d/chain
     (http/websocket-connection req)
     (fn [socket]
       (prn "socket is " socket)
       (if (seq old-conn)
         (swap! sockets disj (nth old-conn 0))
         )
       (swap! sockets conj {:socket socket
                            :client-id client-id
                            :coinbase-socket
                            (ws-conn "wss://ws-feed.pro.coinbase.com"
                                     :on-connect on-connect
                                     :on-msg on-msg
                                     :on-close on-close)
                            :pairs #{}})
       (s/consume (fn [msg] (uppercase-handler socket msg)) socket))))
  )

(defn home-routes []
  [ ""
   {:middleware [middleware/wrap-csrf
                 middleware/wrap-formats]}
   ["/:pair" {:get toggle-streaming}]
   ["/api/v1/subscribe/:pair" {:get toggle-streaming}]])


