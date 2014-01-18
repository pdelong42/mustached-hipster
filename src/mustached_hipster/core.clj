(ns mustached-hipster.core
   (:require [clojure.string  :as s    :only [replace split-lines]])
   (:require [clj-http.client :as http :only [get]])
   (:gen-class))

(defn -main
   [url]

   ;; work around dangerous default behaviour in Clojure
   (alter-var-root #'*read-eval* (constantly false))

   (let
      [before (System/nanoTime)]

      (spit "foo.txt"
         (apply str (take 10
            (map
               #(s/replace % #"\?.*" "\n")
               (filter
                  #(not= \# (first %))
                  (s/split-lines (:body (http/get url))))))))

      (println "the whole thing took" (* (- (System/nanoTime) before) 1e-9) "seconds")))
