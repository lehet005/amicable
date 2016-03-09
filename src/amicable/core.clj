(ns amicable.core
  (:gen-class)
  (:require [clojure.math.numeric-tower :as math])
  (:require [clojure.core.reducers :as r]))


(defn upper-half-divisors
  [s x]
  (r/fold + (conj (into s (flatten (pmap (fn [y] (if (= (mod x y) 0) (/ x y) '()))
                                                   s)))
                            1)))

(defn sum-of-divisors
  "Henry helped us with the finding of factors for this part."
  [x]
  (upper-half-divisors (flatten (pmap (fn [y] (if (= (mod x y) 0) y '()))
                           (range 2 (Math/ceil (math/sqrt x)))))
                       x))

(def sum-of-divisors-memo
  (memoize sum-of-divisors))

(defn amicable-finder
  [num]
  (filter (fn [x] (and
                    (= (sum-of-divisors-memo (first x)) (last x))
                    (not= (first x) (last x))
                    (< (first x) (last x))))
            (pmap  #(vector (sum-of-divisors-memo %) %)
                  (range (+ num 1)))))



(time (doall (amicable-finder 180848)))
(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
