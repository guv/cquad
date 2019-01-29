package cquad;

import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.Native;
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