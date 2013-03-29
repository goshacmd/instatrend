(ns instatrend.conf
  (:require [monger.core :as mg]))

(defn env [k]
  (System/getenv k))

(defn env! [k]
  (or (env k) (throw (Exception. (str "missing key " k)))))

(def port (Integer/parseInt (or (env "PORT") "8080")))
(def instagram-client-id (env! "INSTAGRAM_CLIENT_ID"))
(def mongo-url (some identity (map env ["MONGOHQ_URL" "MONGOLAB_URI" "MONGO_URL" "MONGO_URI"])))

(if mongo-url
  (mg/connect-via-uri! mongo-url)
  (do (mg/connect!)
      (mg/use-db! "instatrend")))
