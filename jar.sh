#!/bin/sh
java -cp classes hebe.tools.ManifestGenerator
/bin/rm -f hebe.jar
jar cfm hebe.jar resource/hebe.manifest.mf -C classes . || exit 1
/bin/rm -f hebeservice.jar
jar cfm hebeservice.jar resource/hebeservice.manifest.mf -C classes . || exit 1

echo "jar files generated successfully"