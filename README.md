# cquad

[![Clojars Project](https://img.shields.io/clojars/v/cquad.svg)](https://clojars.org/cquad)
[![License: LGPL v3](https://img.shields.io/badge/License-EPL%20v1.0-blue.svg)](http://www.gnu.org/licenses/lgpl-3.0)

A Clojure library for the calculation of improper integrals.
The implementation is based on the Fortran library [quadpack](https://people.sc.fsu.edu/~jburkardt/f_src/quadpack_double/quadpack_double.html) ([Source](https://people.sc.fsu.edu/~jburkardt/f_src/quadpack_double/quadpack_double.f90)).

**Currently, only Linux 64 bit and Windows 64 bit are supported.**
More target platforms can be supported by compiling the native library for these.

## Usage

Minimal example:

```clojure
(require '[cquad.core :as cquad])

(cquad/improper-integral
  (fn ^double [^double x] (/ 1.0 (inc (* x x))))
  :negative-infinity,
  :positive-infinity)
;=> 3.141592653589793
```


## License

Copyright (C) 2017-2019 Gunnar Völkel

cquad is licensed under Eclipse Public License 1.0
