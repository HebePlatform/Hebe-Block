#!/bin/sh
if [ -x jre/bin/java ]; then
    JAVA=./jre/bin/java
else
    JAVA=java
fi
${JAVA} -cp classes:lib/*:conf:addons/classes:addons/lib/* -Dhebe.runtime.mode=desktop -Dhebe.runtime.dirProvider=hebe.env.DefaultDirProvider hebe.HEBE
