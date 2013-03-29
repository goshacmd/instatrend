(ns instatrend.instagram
  (:require [instatrend.conf :as conf])
  (:use clojure.java.data)
  (:import org.jinstagram.Instagram))

(def client (Instagram. conf/instagram-client-id))

(defn media-data->hash-map
  "Convert jInstagram's MediaData into a hash map."
  [x]
  (let [media-type (.getType x)
        id (.getId x)
        image-filter (.getImageFilter x)
        tags (.getTags x)
        caption (.getCaption x)
        caption-text (if caption (.getText caption))
        likes (.. x getLikes getCount)
        comments (.. x getComments getCount)
        link (.getLink x)
        user (.getUser x)
        username (.getUserName user)
        full-name (.getFullName user)
        user-id (.getId user)
        created-time (java.util.Date. (* (Integer/parseInt (.getCreatedTime x)) 1000))
        location (.getLocation x)
        location-name (if location (.getName location))
        location-lat (if location (.getLatitude location))
        location-lng (if location (.getLongitude location))
        images (.getImages x)
        thumbnail (.. images getThumbnail getImageUrl)
        image-low (.. images getLowResolution getImageUrl)
        image-std (.. images getStandardResolution getImageUrl)]
    {:id id :type media-type :filter image-filter :tags tags :caption caption-text
     :likes likes :comments comments :link link :created_time created-time
     :user {:username username :full_name full-name :id user-id}
     :location {:name location-name :lat location-lat :lng location-lng}
     :thumbnail thumbnail :standard image-std :low image-low}))

(defn popular []
  "Get a seq of popular shots."
  (map media-data->hash-map (.. client getPopularMedia getData)))
