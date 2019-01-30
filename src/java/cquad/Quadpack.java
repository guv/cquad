/* Copyright (c) Gunnar Völkel. All rights reserved.
 * The use and distribution terms for this software are covered by the
 * Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
 * which can be found in the file epl-v1.0.txt at the root of this distribution.
 * By using this software in any fashion, you are agreeing to be bound by
 * the terms of this license.
 * You must not remove this notice, or any other, from this software.
 */
package cquad;

import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.ptr.ByReference;
import com.sun.jna.ptr.DoubleByReference;


public class Quadpack {

    public interface QuadpackLibrary extends Library {
        Quadpack.QuadpackLibrary lib = (Quadpack.QuadpackLibrary) Native.load("quadpack", Quadpack.QuadpackLibrary.class);

        void integrate_inf(Function f, double bound, int inf, double epsabs, double epsrel, int limit, ByReference result, ByReference abserror);
    }

    public interface Function extends Callback {
        double invoke(double value);
    }

    public static double integrateInf(Function f, double bound, int inf, double epsabs, double epsrel, int limit) {
        DoubleByReference result = new DoubleByReference();
        DoubleByReference abserror = new DoubleByReference();

        QuadpackLibrary.lib.integrate_inf(f, bound, inf, epsabs, epsrel, limit, result, abserror);

        return result.getValue();
    }
}