(ns mustached-hipster.core
   (:use
      [clojure.string  :only [join split split-lines trim]]
      [clj-http.client :only [get]])
   (:gen-class))

(defn -main
   [url]

   ;; work around dangerous default behaviour in Clojure
   (alter-var-root #'*read-eval* (constantly false))

   (print
      (join
         "\n"
         (map
            #(first (split (trim %) #"\?"))
            (filter
               #(not= \# (first %))
               (split-lines (:body (get url))))))))
