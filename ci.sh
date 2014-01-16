#!/bin/sh
cd $(dirname $0)

function build {
    ./$1/ci.sh
    ret=$?
    if [ $ret -ne 0 ]; then
        exit $ret
    fi
}

build spring-android-basic-auth
build spring-android-facebook-client
build spring-android-news-reader
build spring-android-showcase
build spring-android-twitter-client
build spring-android-twitter-search

exit
