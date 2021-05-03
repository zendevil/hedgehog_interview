(ns hedgehog-interview.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[hedgehog-interview started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[hedgehog-interview has shut down successfully]=-"))
   :middleware identity})
