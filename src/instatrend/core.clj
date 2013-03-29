(ns instatrend.core
  (:require [instatrend.conf :as conf]
            [instatrend.models.shot :as shot]
            [instatrend.service :as service]
            [compojure.handler :as handler])
  (:use [ring.adapter.jetty :as ring]
        [ring.middleware.json :only [wrap-json-response]]
        [compojure.core :only [defroutes GET]]))

(defroutes routes
  (GET "/" [x] {:body {:status :ok}})
  (GET "/popular" [] {:body (map #(dissoc % :_id) (shot/find-popular-last-hour))}))

(def application (wrap-json-response (handler/api routes)))

(defn start [port]
  (run-jetty application {:port port :join? false}))

(defn -main [& m]
  (future (service/-main))
  (start conf/port))
