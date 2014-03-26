# Spring for Android Showcase

## Introduction

This showcase includes an Android client and a Spring MVC server. Together these illustrate the interaction of the client and server when using [Spring for Android](http://projects.spring.io/spring-android/).  This Android project requires set up of the Android SDK. See the main [README](../README.md) at the root of this repository for more information about configuring your environment.


## Build and Run the Server

1. Navigate to the server directory:

    ```sh
    $ cd spring-android-showcase/server
    ```

2. Build and run the app:

    ```sh
    $ ./gradlew build bootRun
    ```


## Build and Run the App

1. Navigate to the client directory:

    ```sh
    $ cd client
    ```

2. Start the emulator using the Android Maven Plugin:

    ```sh
    $ mvn android:emulator-start
    ```

    Alternatively, you can start the emulator using the Android command line tools:

    ```sh
    $ emulator @Default
    ```

    > **Important**: Ensure the emulator is fully initialized and ready or the deploy will fail.

3. Build the app:

    ```sh
    $ mvn clean package
    ```

4. Deploy and run the app:

    ```sh
    $ mvn android:deploy android:run
    ```

    > **Note**: the Android Maven Plugin will attempt to deploy the app to all available devices, both emulators and physical devices attached to your computer.

