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
      after #(System/nanoTime)
      grab-and-process (comp
         (partial map #(clojure.string/replace % #"\?.*" "\n"))
         (partial filter #(not= \# (first %)))
         clojure.string/split-lines
         :body
         http/get)]

      (let [
          playlist (grab-and-process m3u8)
          elapsed (- (after) before)]

         (println "successfully fetched the playlist in" (* elapsed 1e-9) "seconds")
         playlist)))

(defn get-segment
   [seg-url]

   (let [
      before (System/nanoTime)
      after #(System/nanoTime)
      filename (clojure.string/replace (clojure.string/trim-newline seg-url) #".*/" "")]

      (with-open [
         whandle (clojure.java.io/output-stream filename)]

         (let [
            elapsed (- (after) before)
            millisec (long (* elapsed 1e-6))]

            (.write whandle (:body (http/get seg-url {:as :byte-array})))
            (println "successfully fetched" filename "in" millisec "ms (pausing for the same amount of time)")
            (Thread/sleep millisec)))))

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
