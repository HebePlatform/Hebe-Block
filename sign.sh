#!/bin/sh
java -cp "classes:lib/*:conf" hebe.tools.SignTransactionJSON $@
exit $?
