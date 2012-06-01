# Spring for Android Facebook Client

## Introduction

This sample app includes an Android client which connects to Twitter. This illustrate the integration of [Spring Social](http://www.springsource.org/spring-social) with [Spring for Android](http://www.springsource.org/spring-android). The Android client project requires set up of the Android SDK. See the main README at the root of this repository for more information about configuring your environment.

## Build and Run the Android Client

1. Navigate to the client directory:

		$ cd spring-android-facebook-client

2. Build the app:

		$ mvn clean install

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

