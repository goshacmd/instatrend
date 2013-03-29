(ns instatrend.service
  (:require [instatrend.conf :as conf]
            [instatrend.instagram :as instagram]
            [instatrend.models.shot :as shot]
            [clojurewerkz.quartzite.scheduler :as qs]
            [clojurewerkz.quartzite.jobs :as j]
            [clojurewerkz.quartzite.triggers :as t])
  (:use [clojurewerkz.quartzite.jobs :only [defjob]]
        [clojurewerkz.quartzite.schedule.simple :only [schedule repeat-forever with-interval-in-minutes]]))

(defjob FetchPopularPhotos [ctx]
  (doseq [shot (instagram/popular)]
    (shot/create shot)))

(defn -main [& m]
  (qs/initialize)
  (qs/start)
  (let [job (j/build
              (j/of-type FetchPopularPhotos)
              (j/with-identity (j/key "jobs.fetch-pop-shots.1")))
        trigger (t/build
                  (t/with-identity (t/key "periodic-trigger.1"))
                  (t/start-now)
                  (t/with-schedule (schedule
                                     (repeat-forever)
                                     (with-interval-in-minutes 1))))]
    (qs/schedule job trigger)))
