(ns mustached-hipster.core
   (:require
      [clj-http.client :as http :only [get]]
      [clojure.java.io :only [output-stream]]
      [clojure.string  :only [replace split-lines]])
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

      (with-open
         [whandle (clojure.java.io/output-stream filename)]

         (.write whandle (:body (http/get seg-url {:as :byte-array})))
         (println "successfully fetched" filename))))

(defn -main
   [url]

   ;; work around dangerous default behaviour in Clojure
   (alter-var-root #'*read-eval* (constantly false))

   (let
      [post-process (comp
         (partial dorun)
         (partial map get-segment)
         (partial take 2) ; for debugging purposes only - remove later
         get-playlist)]

      (post-process url)))
