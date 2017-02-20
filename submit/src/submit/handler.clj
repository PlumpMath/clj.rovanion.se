(ns submit.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [hiccup.core :as hiccup]
            [taoensso.timbre :as log]
            [taoensso.timbre.appenders.core :as appenders]
            [taoensso.timbre.appenders.postal :as postal-appender]
            [postal.core :as postal]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(log/merge-config!
 {:appenders {:spit   (appenders/spit-appender {:fname "/var/log/clj.rovanion.se/log"})
              :postal (postal-appender/postal-appender
                      {:from "clj@rovanion.se" :to "rovanion.luckey@gmail.com"})}})

(defn thank-you [email]
  [:html
   [:head
    [:meta {:charset "utf-8"}]
    [:meta {:http-equiv "x-ua-compatible" :content "ie=edge"}]
    [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
    [:title "Thank you"]
    [:link {:rel "stylesheet" :href "css/main.css"}]]
   [:body
    [:main
     [:h1 "Thank you ❤"]
     [:p "If all goes well you'll receive further details to "
      email " later this spring."]]]])

(defroutes app-routes
  (GET "/signup" [] (fn [{:keys [:params]}]
                      (let [email (:email params)]
                        (log/info email "registered.")
                        (postal/send-message {:from "clj@rovanion.se"
                                              :to "rovanion.luckey@gmail.com"
                                              :subject "Test subject located"
                                              :body email})
                      (hiccup/html (thank-you email)))))

  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
