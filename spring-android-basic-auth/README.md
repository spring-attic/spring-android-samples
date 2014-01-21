# Spring for Android Basic Authentication

## Introduction

This sample includes an Android client and a Spring MVC server. Together these illustrate the interaction of the client and server when using [Spring for Android](http://projects.spring.io/spring-android/) to make Basic Auth requests. This Android project requires set up of the Android SDK. See the main [README](../README.md) at the root of this repository for more information about configuring your environment.


## Build and Run the Server

1. Navigate to the server directory:

    ```sh
    $ cd server
    ```

2. Build the app:

    ```sh
    $ mvn clean install
    ```

3. Deploy the .war to a Servlet 2.5 or > ServletContainer. This can be done via Maven on the command-line by running:

    ```sh
    $ mvn tomcat:run
    ```


## Build and Run the Android App

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
    

## Authenticate

The client app illustrates a very basic interaction with the server. It starts with a screen requesting a username and password. If you enter the correct credentials, the server responds with a success message that is displayed on the screen. If however, you enter incorrect credentials, the server will respond with an HTTP 401, and the corresponding exception details will be displayed on the screen.

    Username: roy   
    Password: spring
    
The username and password are defined in the following Spring Security configuration file:

```sh
/server/src/main/resources/security.xml
```
    