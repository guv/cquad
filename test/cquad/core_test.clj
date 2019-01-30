; Copyright (C) 2017 Gunnar Völkel
;
; This program is free software: you can redistribute it and/or modify
; it under the terms of the GNU Lesser General Public License as published by
; the Free Software Foundation, either version 3 of the License, or
; (at your option) any later version.
;
; This program is distributed in the hope that it will be useful,
; but WITHOUT ANY WARRANTY; without even the implied warranty of
; MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
; GNU Lesser General Public License for more details.

(ns cquad.core-test
  (:require
    [clojure.test :refer :all]
    [cquad.core :as cquad])
  (:import (org.apache.commons.math3.distribution NormalDistribution)
           (cquad Quadpack$Function)))



(defn arctan-derivative
  ^double [^double x]
  (/ 1.0 (inc (* x x))))

(deftest pi-test
  (testing "Integral on 1 / (x^2 + 1)"
    (let [area (cquad/improper-integral arctan-derivative, :negative-infinity, :positive-infinity)]
      (is (< (Math/abs (- area Math/PI)) 1E-14))))
  (testing "Integral on half circle"
    (let [area (cquad/improper-integral
                 (fn ^double [^double x]
                   (if (<= -1.0 x 1.0)
                     (/ 1.0 (inc (* x x)))
                     0.0)),
                 :negative-infinity,
                 :positive-infinity)]
      (is (< (Math/abs (- area (/ Math/PI 2.0))) 1E-14)))))


(deftest normal-density-test
  (testing "integral over normal density"
    (let [nd (NormalDistribution. 0.0, 1.0)
          area (cquad/improper-integral
                 (fn ^double [^double x]
                   (.density nd x))
                 0.0,
                 :positive-infinity)]
      (is (< (Math/abs (- area 0.5)) 1E-6)))))


(deftest argument-checks
  (testing "bound assertions"
    (is
      (thrown? AssertionError
        (cquad/improper-integral arctan-derivative, :positive-infinity, :negative-infinity)))
    (is
      (thrown? AssertionError
        (cquad/improper-integral arctan-derivative, 0.0, :negative-infinity)))
    (is
      (thrown? AssertionError
        (cquad/improper-integral arctan-derivative, :positive-infinity, 0.0)))
    (is
      (thrown? IllegalArgumentException
        (cquad/improper-integral arctan-derivative, 0.0, 1.0)))))


(deftest alternate-fn-definitions-test
  (testing "Function definition via UnivariateFunction"
    (let [area (cquad/improper-integral
                 (reify Quadpack$Function
                   (invoke [_, x]
                     (/ 1.0 (inc (* x x)))))
                 :negative-infinity,
                 :positive-infinity)]
      (is (< (Math/abs (- area Math/PI)) 1E-14))))
  (testing "Function definition via non-primitive Clojure function"
    (let [area (cquad/improper-integral
                 (fn [x] (/ 1.0 (inc (* x x))))
                 :negative-infinity,
                 :positive-infinity)]
      (is (< (Math/abs (- area Math/PI)) 1E-14)))))