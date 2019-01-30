package cquad;

import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.ptr.ByReference;
import com.sun.jna.ptr.DoubleByReference;


public class Quadpack {

    private static String libraryName() {
        String os = (Platform.isLinux() ? "linux" : (Platform.isWindows() ? "win" : (Platform.isMac() ? "mac" : "unknown")));
        String arch = (Platform.is64Bit() ? "amd64" : "x86");
        return "quadpack-" + os + "-" + arch;
    }


    public interface QuadpackLibrary extends Library {
        Quadpack.QuadpackLibrary lib = (Quadpack.QuadpackLibrary) Native.load( libraryName(), Quadpack.QuadpackLibrary.class);

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