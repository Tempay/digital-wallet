#!/bin/bash

# before run, make sure output files are clean
:> paymo_output/output1.txt
:> paymo_output/output2.txt
:> paymo_output/output3.txt

# compile .java files
javac -cp src/opencsv.jar:src/commons-lang.jar src/*.java

# run
java -cp src/opencsv.jar:src/commons-lang.jar:. src/Main