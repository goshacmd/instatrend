(ns instatrend.models.shot
  (:refer-clojure :exclude [find sort extend])
  (:require [monger.collection :as mc])
  (:use [monger query operators]
        [clj-time core coerce]))

(mc/ensure-index "shots" {:created_time -1} {:likes -1})

(defn find-popular-within [start stop]
  (with-collection "shots"
    (find {:created_time { $gte start $lt stop }})
    (sort {:likes -1})
    (limit 10)))

(defn find-popular-from [start]
  (let [now (to-date (now))
        start (to-date start)]
    (find-popular-within start now)))

(defn find-popular-minus [x]
  (let [now (now)
        start (minus now x)]
    (find-popular-from start)))

(defn find-popular-last-hour []
  (find-popular-minus (hours 1)))

(defn find-popular-last-day []
  (find-popular-minus (days 1)))

(defn generate-id
  "Generate the id of the doc. Just joins media type & id."
  [doc]
  (str (:type doc) "-" (:id doc)))

(defn create
  "Create a shot record."
  [doc]
  (let [basic-doc (select-keys doc [:type :filter :likes :comments :link :created_time :user :location :thumbnail :standard :low])
        id (:id doc)
        doc-with-id (into basic-doc {:_id id})]
    (mc/update "shots" {:_id (:id doc)} doc-with-id :upsert true)))
