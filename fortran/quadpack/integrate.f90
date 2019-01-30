! Copyright (c) Gunnar Völkel. All rights reserved.
! The use and distribution terms for this software are covered by the
! Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
! which can be found in the file epl-v1.0.txt at the root of this distribution.
! By using this software in any fashion, you are agreeing to be bound by
! the terms of this license.
! You must not remove this notice, or any other, from this software.

subroutine integrate_inf(cf, bound, inf, epsabs, epsrel, limit, result, abserr) bind(C, name = 'integrate_inf')
    use, intrinsic :: iso_c_binding
    interface
        function target_fun(x)
            implicit none
            real (kind = 8), intent(in), value :: x
            real (kind = 8) :: target_fun
        end function target_fun
    end interface
    type (c_funptr), intent(in), value :: cf
    real (kind = 8), intent(in), value :: bound, epsabs, epsrel
    integer (kind = 4), intent(in), value :: inf, limit
    real (kind = 8) result, abserr
    real (kind = 8) temp_result, temp_abserr;

    real (kind = 8) alist, blist, elist, rlist
    integer (kind = 4) ier, iord, last, neval
    dimension alist(limit), blist(limit), elist(limit), iord(limit), rlist(limit)

    procedure(target_fun), pointer :: f
    ! Convert C to Fortran procedure pointer.
    call c_f_procpointer (cf, f)

    call dqagie(f, bound, inf, epsabs, epsrel, limit, temp_result, temp_abserr, neval, &
            ier, alist, blist, rlist, elist, iord, last)

    result = temp_result
    abserr = temp_abserr
end subroutine integrate_inf




