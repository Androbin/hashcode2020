#!/bin/bash
javac -d out src/*.java || exit

(
  cd out

  for file in "$@"; do
    java Main ../input/"$file" ../output/"$file"
    echo
  done
)
