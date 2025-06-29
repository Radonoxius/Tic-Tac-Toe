#! /bin/bash

cd C

mkdir build 2> /dev/null
cd build

echo '--- Compiling C and Rust ---'

clang -O3 -c ../*.c
ar rcs libhelper.a *.o

rm *.o

clang -O3 ../window.c -lncurses -shared -o libwindow.so
cd ../..
mv C/build/libwindow.so ../../nativeLibs/libwindow.so 2> /dev/null

cargo b --release

cd C
rm -rf build

cd ..

mv target/release/libartist.so ../../nativeLibs/libartist.so 2> /dev/null

cargo clean