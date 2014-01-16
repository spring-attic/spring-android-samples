#!/bin/sh
cd $(dirname $0)

ant debug
ret=$?
if [ $ret -ne 0 ]; then
    exit $ret
fi
rm -rf bin
