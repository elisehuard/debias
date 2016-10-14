(ns word2vec.core
  (:require [clojure.java.io :as io]
            [clojure.core.matrix :as m]
            [clojure.core.matrix.operators :as ops]))

(m/set-current-implementation :vectorz)

(defn parse-line
  [line]
  (let [sline (clojure.string/split line #" ")]
    [(first sline) (m/matrix (map #(Float/parseFloat %) (rest sline)))]))


(def he (last (parse-line (slurp (io/resource "he.txt"))) ))
(def she (last (parse-line (slurp (io/resource "she.txt")))))

(defn she-he-cos
  "cos angle is multiplying vector and then dividing by mult of norm of vectors"
  []
  (let [norm-she (Math/sqrt (m/dot she she))
        norm-he (Math/sqrt (m/dot he he))]
    (/ (m/dot she he) (* norm-she norm-he))))

(defn load-word2vec-format
  "Load word2vec binary format into something useable"
  [filename]
  (with-open [rdr (io/reader (io/resource filename))]
    (let [line (line-seq rdr)
          [words size] (read-string (str "[" (first line) "]"))
          body (rest line)
          parse-fn (fn [line]
                     (clojure.string/split line #" "))
          ]
      (println (str "contains " words " word vectors of size " size))
      (println (clojure.string/split (first body) #" "))
      )

    #_(doseq [line (line-seq rdr)]
        (println line
                 (process-fn line)
                 ))))

()
(load-word2vec-format "GoogleNews-vectors-negative300.txt")
