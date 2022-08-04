#!/bin/bash
echo "starting client [correct sequence]"
cd ../src/
java client/Client localhost 51000 52000 53000 52000 51000
