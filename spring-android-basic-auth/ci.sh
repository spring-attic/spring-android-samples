#!/bin/sh
cd $(dirname $0)

cd client

mvn clean package
ret=$?
if [ $ret -ne 0 ]; then
    exit $ret
fi
rm -rf target

cd ../server

mvn clean package
ret=$?
if [ $ret -ne 0 ]; then
    exit $ret
fi
rm -rf target
