(ns mustached-hipster.core
   (:require [clojure.string  :as s    :only [replace split-lines]])
   (:require [clj-http.client :as http :only [get]])
   (:gen-class))

(defn get-playlist
   [m3u8]

   (let [
      before (System/nanoTime)
      after #(* (- (System/nanoTime) before) 1e-9)
      grab-and-process (comp
         (partial map #(s/replace % #"\?.*" "\n"))
         (partial filter #(not= \# (first %)))
         s/split-lines
         :body
         http/get)]

      (let
         [playlist (grab-and-process m3u8)]
         (println "the request took" (after) "seconds")
         playlist)))

(defn -main
   [url]

   ;; work around dangerous default behaviour in Clojure
   (alter-var-root #'*read-eval* (constantly false))

   (let
      [post-process (comp
         (partial spit "foo.txt")
         (partial apply str)
         (partial take 10)
         get-playlist)]

      (post-process url)))
