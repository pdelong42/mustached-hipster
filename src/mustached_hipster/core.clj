(ns mustached-hipster.core
   (:require [clojure.string  :as s    :only [replace split-lines]])
   (:require [clj-http.client :as http :only [get]])
   (:gen-class))

(defn get-playlist
   [m3u8]

   (let
      [before (System/nanoTime)]

      (let
         [response (http/get m3u8)]
         (println "the request took" (* (- (System/nanoTime) before) 1e-9) "seconds")
         response)))

(defn -main
   [url]

   ;; work around dangerous default behaviour in Clojure
   (alter-var-root #'*read-eval* (constantly false))

   (let
      [not-comment #(not= \# (first %))
       chop-query  #(s/replace % #"\?.*" "\n") ]

      (spit "foo.txt"
         (apply str
            (take 10
               (map chop-query
                  (filter not-comment
                     (s/split-lines
                        (:body
                           (get-playlist url))))))))))
