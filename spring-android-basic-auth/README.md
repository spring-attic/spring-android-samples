# Spring for Android Basic Authentication

## Introduction

This sample includes an Android client and a Spring MVC server. Together these illustrate the interaction of the client and server when using [Spring for Android](http://www.springsource.org/spring-android) to make Basic Auth requests. This Android client project requires set up of the Android SDK. See the main README at the root of this repository for more information about configuring your environment.

## Build and Run the Server

1. Navigate to the server directory:

        $ cd spring-android-basic-auth/server

2. Build the app:

        $ mvn clean install

3. Deploy the .war to a Servlet 2.5 or > ServletContainer. This can be done via Maven on the command-line by running:

        $ mvn tomcat:run


## Build and Run the Android Client

1. Navigate to the client directory:

        $ cd spring-android-basic-auth/client

2. Build the app:

        $ mvn clean package

3. Start the emulator using the Android Maven Plugin:

        $ mvn android:emulator-start

    Alternatively, you can start the emulator using the Android command line tools:

        $ emulator @Default

    _IMPORTANT: Ensure the emulator is fully initialized and ready or the deploy will fail._

4. Deploy the app to the emulator:

        $ mvn android:deploy

5. Start the sample app:

        $ mvn android:run
        
    _Note: the Android Maven Plugin will attempt to deploy and run the app to all available devices, both emulators and physical devices attached to your computer._

## Authenticating

The client app illustrates a very basic interaction with the server. It starts with a screen requesting a username and password. If you enter the correct credentials, the server responds with a success message that is displayed on the screen. If however, you enter incorrect credentials, the server will respond with an HTTP 401, and the corresponding exception details will be displayed on the screen.

    Username: roy   
    Password: spring
    
The username and password are defined in the following Spring Security configuration file:

    /src/main/resources/security.xml
    