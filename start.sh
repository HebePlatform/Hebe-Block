#!/bin/sh
if [ -e ~/.hebe/hebe.pid ]; then
    PID=`cat ~/.hebe/hebe.pid`
    ps -p $PID > /dev/null
    STATUS=$?
    if [ $STATUS -eq 0 ]; then
        echo "Hebe server already running"
        exit 1
    fi
fi
mkdir -p ~/.hebe/
DIR=`dirname "$0"`
cd "${DIR}"
if [ -x jre/bin/java ]; then
    JAVA=./jre/bin/java
else
    JAVA=java
fi
nohup ${JAVA} -cp classes:lib/*:conf:addons/classes:addons/lib/* -Dhebe.runtime.mode=desktop hebe.HEBE > /dev/null 2>&1 &
echo $! > ~/.hebe/hebe.pid
cd - > /dev/null
