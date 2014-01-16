(ns mustached-hipster.core (:gen-class))

(require 'clj-http.client)
(require 'clojure.string)

(defn -main
  [url]
  ;; work around dangerous default behaviour in Clojure
  (alter-var-root #'*read-eval* (constantly false))
  ;(print (:body (clj-http.client/get "http://nrk.no/nordlandsbanen/")))
  ;(print (:body (clj-http.client/get "http://nrk.no/nordlandsbanen/media/js/videoPlayer.js")))
  (print (:body (clj-http.client/get url))))
