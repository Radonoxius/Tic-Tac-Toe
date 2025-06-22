#! /bin/bash

echo '--- Compiling Java ---'

./gradlew jar

cp -r nativeLibs build/libs/nativeLibs 2> /dev/null

cd build/libs

echo '--- Starting the App! ---'

java -jar --enable-preview --enable-native-access=ALL-UNNAMED tic-tac-toe-1.0-SNAPSHOT.jar

rm -rf nativeLibs

cd ../..