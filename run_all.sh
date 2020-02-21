#!/bin/bash
files=($(
  cd input
  echo *.txt
))

exec ./run.sh "${files[@]}"
