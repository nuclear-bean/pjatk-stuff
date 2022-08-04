#!/bin/bash
javac -version
cd ../src/
javac ./client/Client.java
javac ./server/Server.java
echo "finished compilation"
