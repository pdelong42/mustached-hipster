(ns mustached-hipster.core
   (:require clj-http.client clojure.string)
   (:gen-class))

(defn -main
   [url]

   ;; work around dangerous default behaviour in Clojure
   (alter-var-root #'*read-eval* (constantly false))

   (print
      (clojure.string/join
         "\n"
         (map
            #(first (clojure.string/split (clojure.string/trim %) #"\?"))
            (filter
               #(not= \# (first %))
               (clojure.string/split-lines (:body (clj-http.client/get url))))))))
