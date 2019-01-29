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




