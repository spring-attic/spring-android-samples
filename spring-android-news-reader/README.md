# Spring for Android News Reader

## Introduction

The Spring for Android News Reader sample app illustrates the use of [Spring for Android](http://www.springsource.org/spring-android) and the [Android ROME Feed Reader](http://code.google.com/p/android-rome-feed-reader/) to retrieve RSS and Atom news feeds. This project requires set up of the Android SDK. See the main README at the root of this repository for more information about configuring your environment.

## Build and Run the Android App
>>>>>>> readme updates

1. Build the app: 

		$ mvn clean install

2. Start the emulator using the Android Maven Plugin:

		$ mvn android:emulator-start

	Alternatively, you can start the emulator using the Android command line tools:

		$ emulator @Default

	_IMPORTANT: Ensure the emulator is fully initialized and ready or the deploy will fail._

3. Deploy the app to the emulator:

		$ mvn android:deploy

4. Start the sample app:

		$ mvn android:run
		
	_Note: the Android Maven Plugin will attempt to deploy and run the app to all available devices, both emulators and physical devices attached to your computer._
