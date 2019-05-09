#!/bin/sh
if [ -e ~/.hebe/hebe.pid ]; then
    PID=`cat ~/.hebe/hebe.pid`
    ps -p $PID > /dev/null
    STATUS=$?
    echo "stopping"
    while [ $STATUS -eq 0 ]; do
        kill `cat ~/.hebe/hebe.pid` > /dev/null
        sleep 5
        ps -p $PID > /dev/null
        STATUS=$?
    done
    rm -f ~/.hebe/hebe.pid
    echo "Hebe server stopped"
fi

