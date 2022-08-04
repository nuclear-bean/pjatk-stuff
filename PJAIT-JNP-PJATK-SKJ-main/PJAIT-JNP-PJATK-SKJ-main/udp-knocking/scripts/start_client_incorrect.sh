#!/bin/bash
echo "starting client [incorrect sequence]"
cd ../src/
java client/Client localhost 51000 52000 50000 52000 51000
