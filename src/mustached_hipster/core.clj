(ns mustached-hipster.core
   (:require
      [clj-http.client :as http   :only [get]]
      [clojure.java.io :as io     :only [output-stream]]
      [clojure.string  :as string :only [replace split-lines]])
   (:gen-class))

(defn get-playlist
   [m3u8]

   (let [
      before (System/nanoTime)
      after #(System/nanoTime)
      grab-and-process (comp
         (partial map #(string/replace % #"\?.*" "\n"))
         (partial filter #(not= \# (first %)))
         string/split-lines
         :body
         http/get)]

      (let [
         playlist (grab-and-process m3u8)
         elapsed (- (after) before)
         seconds (* elapsed 1e-9)]

         (println "successfully fetched the playlist in" seconds "seconds")
         playlist)))

(defn get-segment
   [seg-url]

   (let [
      before (System/nanoTime)
      after #(System/nanoTime)
      filename (string/replace (string/trim-newline seg-url) #".*/" "")]

      (with-open
         [whandle (io/output-stream filename)]

         (.write whandle (:body (http/get seg-url {:as :byte-array})))

         (let [
            elapsed (- (after) before)
            seconds (* elapsed 1e-9)]

            (println "successfully fetched" filename "in" seconds "seconds (pausing for the same amount of time)")
            (Thread/sleep (long (* 1000 seconds)))))))

(defn -main
   [url]

   ;; work around dangerous default behaviour in Clojure
   (alter-var-root #'*read-eval* (constantly false))

   (let
      [post-process (comp
         (partial dorun)
         (partial map get-segment)
         (partial take 9) ; for debugging purposes only - remove later
         get-playlist)]

      (post-process url)))
