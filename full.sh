#! /bin/bash

echo '--- Cleaning with Gradle ---'

./gradlew clean -q --console=plain

cd rust/artist
./build.sh

cd ../..

./run.sh