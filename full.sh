#! /bin/bash

cd rust/artist
./build.sh

cd ../..

./gradlew jar

cp -r nativeLibs build/libs/nativeLibs 2> /dev/null

cd build/libs

java -jar --enable-preview --enable-native-access=ALL-UNNAMED tic-tac-toe-1.0-SNAPSHOT.jar

rm -rf nativeLibs

cd ../..