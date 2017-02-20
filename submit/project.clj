(defproject submit "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.5.1"]
                 [ring/ring-defaults "0.2.1"]
                 [com.taoensso/timbre "4.8.0"]
                 [ring/ring-core "1.5.1"]
                 [ring/ring-jetty-adapter "1.5.0"]
                 [hiccup "1.0.5"]
                 [com.draines/postal "2.0.2"]]
  :plugins [[lein-ring "0.11.0"]]
  :ring {:handler submit.handler/app
         :auto-refresh? true
         :nrepl {:start? true
                 :port 54321}}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]]}})
