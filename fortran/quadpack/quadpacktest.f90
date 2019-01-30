! Copyright (c) Gunnar Völkel. All rights reserved.
! The use and distribution terms for this software are covered by the
! Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
! which can be found in the file epl-v1.0.txt at the root of this distribution.
! By using this software in any fashion, you are agreeing to be bound by
! the terms of this license.
! You must not remove this notice, or any other, from this software.

function testfunc(x)
    implicit none
    real (kind=8) x, testfunc

    testfunc = 1.0d0 / (x * x + 1)
end function testfunc

program quadpacktest
    implicit none

    interface
        function testfunc(x)
            implicit none
            real (kind=8) x, testfunc
        end function testfunc
    end interface

    procedure(testfunc), pointer :: f

    double precision result, abserror, y
    double precision alist, blist, elist, rlist
    integer ier, iord, last, neval
    dimension alist(100), blist(100), elist(100), iord(100), rlist(100)

    y = testfunc(1.0d0)

    write(*, *) y
    f => testfunc
    !(f,bound,inf,epsabs,epsrel,limit,result,abserr,neval,ier,alist,blist,rlist,elist,iord,last)
    call dqagie(testfunc, 0, 2, 1.0d-14, 1.0d-14, 100, result, abserror, neval, &
            ier, alist, blist, rlist, elist, iord, last)

    write(*, *) result
    write(*,*) abserror
end program quadpacktest

