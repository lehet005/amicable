(ns amicable.core
  (:gen-class)
  (:require [clojure.math.numeric-tower :as math])
  (:require [clojure.core.reducers :as r]))

(defn sieve [s]
  (cons (first s)
        (lazy-seq (sieve (filter #(not= 0 (mod % (first s)))
                                 (rest s))))))

;; (defn sum-of-divisors
;;   [x]
;;   (r/fold + (flatten (map (fn [y] (if (= (mod x y) 0) y '()))
;;                  (pmap inc
;;                        (range (int (Math/ceil (/ x 2)))))))))

(defn upper-half-divisors
  [s x]
  (r/fold + (conj (into s (flatten (pmap (fn [y] (if (= (mod x y) 0) (/ x y) '()))
                                                   s)))
                            1)))

(defn sum-of-divisors
  [x]
  (upper-half-divisors (flatten (pmap (fn [y] (if (= (mod x y) 0) y '()))
                           (range 2 (Math/ceil (math/sqrt x)))))
                       x))


(sum-of-divisors 284)

(defn amicable-finder
  [num]
  (filter (fn [x] (and
;;                    (= (first x) (sum-of-divisors (last x)))
                    (= (sum-of-divisors (first x)) (last x))
                    (not= (first x) (last x))
                    (< (first x) (last x))))
            (pmap  #(vector (sum-of-divisors %) %)
                  (range num))))



(amicable-finder 76085)
(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
