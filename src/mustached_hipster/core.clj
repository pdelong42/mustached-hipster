(ns mustached-hipster.core
   (:use [clojure.string :only [join split split-lines trim]])
   (:require [clj-http.client :as http :only [get]])
   (:gen-class))

(defn -main
   [url]

   ;; work around dangerous default behaviour in Clojure
   (alter-var-root #'*read-eval* (constantly false))

   (let
      [then (System/nanoTime)]

      (spit "foo.txt"
         (apply str
            (map
               #(str (first (split (trim %) #"\?")) "\n")
               (filter
                  #(not= \# (first %))
                  (split-lines (:body (http/get url)))))))

      (println "the whole thing took" (* (- (System/nanoTime) then) 1e-9) "seconds")))
