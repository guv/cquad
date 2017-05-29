# cquad

[![Clojars Project](https://img.shields.io/clojars/v/cquad.svg)](https://clojars.org/cquad)
[![License: LGPL v3](https://img.shields.io/badge/License-LGPL%20v3-blue.svg)](http://www.gnu.org/licenses/lgpl-3.0)

A Clojure library for the calculation of improper integrals.
The implementation is based on the Java port of quadpack in JDistlib.

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

[JDistlib](http://jdistlib.sourceforge.net/) is licensed under GPL v3

cquad is licensed under LGPL v3.

Copyright (C) 2017 Gunnar Völkel

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
