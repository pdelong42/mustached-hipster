(ns mustached-hipster.core
   (:require [clojure.string  :only [replace split-lines]])
   (:require [clj-http.client :as http :only [get]])
   (:gen-class))

(defn get-playlist
   [m3u8]

   (let [
      before (System/nanoTime)
      elapsed #(* (- (System/nanoTime) before) 1e-9)
      grab-and-process (comp
         (partial map #(clojure.string/replace % #"\?.*" "\n"))
         (partial filter #(not= \# (first %)))
         clojure.string/split-lines
         :body
         http/get)]

      (let
         [playlist (grab-and-process m3u8)]
         (println "successfully fetched the playlist in" (elapsed) "seconds")
         playlist)))

(defn get-segment
   [seg-url]

   (let
      [filename (clojure.string/replace (clojure.string/trim-newline seg-url) #".*/" "")]
      (spit filename (:body (http/get seg-url)))
      (println "successfully fetched" filename)))

(defn -main
   [url]

   ;; work around dangerous default behaviour in Clojure
   (alter-var-root #'*read-eval* (constantly false))

   (let
      [post-process (comp
         (partial dorun)
         (partial map get-segment)
         (partial take 100) ; for debugging purposes only - remove later
         get-playlist)]

      (post-process url)))
