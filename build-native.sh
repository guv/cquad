#!/bin/sh


SOURCEDIR="../fortran/quadpack"

mkdir -p target
cd target

gfortran -fno-underscoring -fPIC -c -g "$SOURCEDIR/quadpack_double.f90" "$SOURCEDIR/integrate.f90" && \
gfortran -shared -o libquadpack.so quadpack_double.o integrate.o && \
cp libquadpack.so ../resources/linux-x86-64/ && echo "Created libquadpack.so" || echo "Failed to create libquadpack.so"


x86_64-w64-mingw32-gfortran-win32 -fno-underscoring -fPIC -static -m64 -c -g "$SOURCEDIR/quadpack_double.f90" "$SOURCEDIR/integrate.f90" && \
x86_64-w64-mingw32-gfortran-win32 -shared -static -m64 -o quadpack.dll quadpack_double.o integrate.o && \
cp quadpack.dll ../resources/win32-x86-64/ && echo "Created quadpack.dll" || echo "Failed to create quadpack.dll"
