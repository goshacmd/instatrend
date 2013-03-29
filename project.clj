(defproject instatrend "0.1.0-SNAPSHOT"
  :description "An Instagram trend explorer."
  :url "http://github.com/goshakkk/instatrend"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [com.nesscomputing.instagram/jInstagram "1.3.3"]
                 [com.novemberain/monger "1.5.0-rc1"]
                 [clojurewerkz/quartzite "1.0.1"]
                 [clj-time "0.4.5"]
                 [org.clojure/java.data "0.1.1"]
                 [ring/ring-core "1.1.8"]
                 [ring/ring-jetty-adapter "1.1.8"]
                 [ring/ring-json "0.2.0"]
                 [compojure "1.1.5"]]
  :main instatrend.core)
