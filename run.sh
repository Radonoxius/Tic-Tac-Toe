#! /bin/bash

echo '--- Compiling Java ---'

./gradlew jar -q --console=plain

cp -r nativeLibs build/libs/nativeLibs 2> /dev/null

cd build/libs

echo '--- Starting the Game! ---'

java -jar --enable-native-access=ALL-UNNAMED tic-tac-toe-1.0-SNAPSHOT.jar

rm -rf nativeLibs

cd ../..