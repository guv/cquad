(ns cquad.core
  (:import
    net.sourceforge.jdistlib.math.Constants
    cquad.Integrate
    (net.sourceforge.jdistlib.math UnivariateFunction)
    (clojure.lang IFn$DD)))



(defn print-warning
  [& msg]
  (binding [*out* *err*]
    (println (apply str msg))))


(defn improper-integral
  "Integration of the given function over infinite intervals.

  lower-bound - (double or :negative-infinity) lower bound of integration range
  upper-bound - (double or :positive-infinity) upper bound of integration range
  inf    - (double) indicating the kind of integration range involved
           inf = 1 corresponds to  (bound,+infinity),
           inf = -1            to  (-infinity,bound),
           inf = 2             to (-infinity,+infinity).
  epsabs - (double) absolute accuracy requested
  epsrel - (double) relative accuracy requested
           if  epsabs <= 0\n     and epsrel < max(50*rel.mach.acc.,0.5d-28),
           the routine will end with ier = 6.
  limit  - (int) gives an upper bound on the number of subintervals in the partition of (a,b), limit >= 1"
  [f, lower-bound, upper-bound, & {:keys [epsabs, epsrel, limit]
                                   :or {epsabs (* 64.0 Constants/DBL_EPSILON),
                                        epsrel (* 64.0 Constants/DBL_EPSILON),
                                        limit 100}}]
  (assert (or (number? lower-bound) (= lower-bound :negative-infinity))
    "lower-bound must be either a number or :negative-infinity")
  (assert (or (number? upper-bound) (= upper-bound :positive-infinity))
    "upper-bound must be either a number or :positive-infinity")
  (let [inf (if (= lower-bound :negative-infinity)
              (if (= upper-bound :positive-infinity)
                ; doubly infinity
                2.0
                ; only lower bound infinity
                -1.0)
              (if (= upper-bound :positive-infinity)
                ; only upper bound infinity
                1.0
                ; error no improper integral
                (throw (IllegalArgumentException. "At least one of the bounds must be specified as infinite!")))),
        bound (case inf
                1.0 lower-bound,
                -1.0 upper-bound
                ; bound does not matter for inf = 2
                2.0 0.0),
        uf (if (instance? UnivariateFunction f)
             f
             (if (instance? IFn$DD f)
               (reify UnivariateFunction
                 (eval [_, x]
                   (.invokePrim ^IFn$DD f x)))
               (do
                 (print-warning "Warning: improper-integral invoked with a non-primitive function.\n"
                   "This will slow down the calculation. You should provide a primitive function with interface IFn$DD, e.g. (fn ^double [^double x] ...)")
                 (reify UnivariateFunction
                   (eval [_, x]
                     (f x))))))
        ires (Integrate/dqagie uf, bound, inf, epsabs, epsrel, limit)]
    (.result ires)))