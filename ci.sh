#!/bin/bash
cd $(dirname $0)

if [ ! -d "$ANDROID_HOME" ]; then
    if [[ "$OSTYPE" == "linux-gnu" ]]; then
        export ANDROID_HOME=${WORKSPACE}/android-sdk-linux 
        export PATH=${ANDROID_HOME}/tools:${ANDROID_HOME}/platform-tools:${PATH}
        wget http://dl.google.com/android/android-sdk_r22.6.2-linux.tgz -nv
        tar xzf android-sdk_r22.6.2-linux.tgz
        echo "y" | android update sdk -f -u -a -t tools,platform-tools,build-tools-19.0.3,build-tools-19.0.0,build-tools-18.1.1,build-tools-18.1.0,build-tools-18.0.1,build-tools-17.0.0,android-19,extra-android-m2repository,extra-android-support
    #elif [[ "$OSTYPE" == "darwin"* ]]; then
    else
        echo "ANDROID_HOME is not available"
        exit
    fi
fi

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
