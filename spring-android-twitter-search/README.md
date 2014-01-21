# Spring for Android Twitter Search

## Introduction

The Spring for Android Twitter Search sample app demonstrates the use of [Spring for Android](http://projects.spring.io/spring-android/) to make a RESTful request to the Twitter search API, without the use of Maven dependency management. This project requires set up of the Android SDK, and uses Ant to build and deploy the app to the emulator. See the main README at the root of this repository for more information about configuring your environment.

## Build and Run the Android App

1. Build the app in debug mode: 

        $ ant debug

2. Start the emulator:

        $ emulator @Default

    _IMPORTANT: Ensure the emulator is fully initialized and ready or the deploy will fail._

3. Deploy the app to the emulator:

        $ ant installd

4. From the emulator start the app

## Troubleshooting

You may need to update the build.xml for your installed version of the Android SDK.

1. Delete the build.xml file from the root of this project

2. Update your project:

        $ android update project -p <project_directory>
