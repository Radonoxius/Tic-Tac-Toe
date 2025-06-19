#! /bin/bash

cd C


mkdir build 2> /dev/null

cd build

clang -O3 -c ../*.c
ar rcs libhelper.a *.o

rm *.o

cd ../..

cargo b --release

mv target/release/libartist.so ../../nativeLibs/libartist.so 2> /dev/null

cargo clean 1> /dev/null
