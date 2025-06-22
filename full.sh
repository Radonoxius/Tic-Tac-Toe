#! /bin/bash

echo '--- Cleaning with Gradle ---'

./gradlew clean

cd rust/artist
./build.sh

cd ../..

./run.sh