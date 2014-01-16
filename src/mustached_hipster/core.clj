(ns mustached-hipster.core (:gen-class))

(require 'clj-http.client)
(require 'clojure.string)

(defn -main
   [url]
   ;; work around dangerous default behaviour in Clojure
   (alter-var-root #'*read-eval* (constantly false))
   (print (:body (clj-http.client/get url))))
