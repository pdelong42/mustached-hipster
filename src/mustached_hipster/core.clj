(ns mustached-hipster.core
   (:use [clojure.string :only [join split split-lines trim]])
   (:require [clj-http.client :as http :only [get]])
   (:gen-class))

(defn -main
   [url]

   ;; work around dangerous default behaviour in Clojure
   (alter-var-root #'*read-eval* (constantly false))

   (spit "foo.txt"
      (str
         (join
            "\n"
            (map
               #(first (split (trim %) #"\?"))
               (filter
                  #(not= \# (first %))
                  (split-lines (:body (http/get url))))))
         "\n")))
