#!/bin/sh


SOURCEDIR="../fortran/quadpack"

mkdir -p target
cd target

gfortran -fno-underscoring -fPIC -c -g "$SOURCEDIR/quadpack_double.f90" "$SOURCEDIR/integrate.f90" && \
gfortran -shared -o libquadpack-linux-amd64.so quadpack_double.o integrate.o && \
cp libquadpack-linux-amd64.so ../resources/ && echo "Created libquadpack-linux-amd64.so" || echo "Failed to create libquadpack-linux-amd64.so"


x86_64-w64-mingw32-gfortran-win32 -fno-underscoring -fPIC -static -m64 -c -g "$SOURCEDIR/quadpack_double.f90" "$SOURCEDIR/integrate.f90" && \
x86_64-w64-mingw32-gfortran-win32 -shared -static -m64 -o quadpack-win-amd64.dll quadpack_double.o integrate.o && \
cp quadpack-win-amd64.dll ../resources/ && echo "Created quadpack-win-amd64.dll" || echo "Failed to create quadpack-win-amd64.dll"
