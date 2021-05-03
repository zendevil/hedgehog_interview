(ns hedgehog-interview.env
  (:require
    [selmer.parser :as parser]
    [clojure.tools.logging :as log]
    [hedgehog-interview.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[hedgehog-interview started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[hedgehog-interview has shut down successfully]=-"))
   :middleware wrap-dev})
