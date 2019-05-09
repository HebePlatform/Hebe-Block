#!/bin/sh
CP="lib/*;classes"
SP=src/java/

/bin/rm -f hebe.jar
/bin/rm -f hebeservice.jar
/bin/rm -rf classes
/bin/mkdir -p classes/
/bin/rm -rf addons/classes
/bin/mkdir -p addons/classes/

javac -encoding utf8 -sourcepath "${SP}" -classpath "${CP}" -d classes/ src/java/hebe/*.java src/java/hebe/*/*.java src/java/hebe/*/*/*.java src/java/hebedesktop/*.java || exit 1

echo "hebe class files compiled successfully"

ls addons/src/*.java > /dev/null 2>&1 || exit 0
javac -encoding utf8 -sourcepath "${SP}" -classpath "${CP}" -d addons/classes addons/src/*.java || exit 1

echo "addon class files compiled successfully"
