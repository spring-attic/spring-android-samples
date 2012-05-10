# Spring for Android Samples

## Overview

This repository contains sample projects illustrating usage of [Spring for Android](http://www.springsource.org/spring-android). Each project requires set up of the [Android SDK](http://developer.android.com/sdk/index.html), command line tools, [Maven](http://maven.apache.org/), the [Android Maven Plugin](http://code.google.com/p/maven-android-plugin/), and Android Maven artifacts such as Spring dependencies. This document walks through that setup.

## Sample Apps

See the README in each directory for more information on the sample project and additional instructions.

* spring-android-showcase - The showcase contains client and server apps, and illustrates the features of Spring for Android.
![Screenshot](/spring-android-showcase/screeshot001.jpg)
* spring-android-news-reader - The news reader highlights the use of the Android ROME Feed Reader by retrieving RSS and Atom news feeds.
* spring-android-twitter-search - The Twitter search app demonstrates an Android project including Spring for Android jars without the use of Maven's dependency management.
* spring-android-basic-auth - These client and server applications show how to make Basic Auth requests using RestTemplate on Android and connecting to a Spring MVC site.

## Development Environment

The [Android SDK](http://developer.android.com/sdk/index.html) is required for developing Android applications. Google provides command line tools, and an [Eclipse plugin](http://developer.android.com/sdk/eclipse-adt.html) for building Android applications, however you are not restricted to only those options. The [Android Maven Plugin](http://code.google.com/p/maven-android-plugin/) makes use of the Android SDK command line tools to compile and deploy the app to the emulator, so there is no need for a separate IDE setup or configuration.


### Install the Android SDK

1. Download the correct version of the Android SDK for your operating system from the Android web site:

	> [http://developer.android.com/sdk/index.html](http://developer.android.com/sdk/index.html)

1. Unzip the archive and place it in a location of your choosing. For example on a Mac, you may want to place it in the root of your user directory. See the download web site for additional [installation details](http://developer.android.com/sdk/installing.html).

2. Add Android to your path. The following is an example .bash_profile on a OS X:

		$ export ANDROID_HOME=~/android-sdk-macosx
		$ export PATH=${PATH}:$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools'

### Install Android SDK Platform(s)

The Android SDK download does not include any specific Android platform SDKs. In order to run the sample code you need to download and install the [Android 2.1 SDK Platform](http://developer.android.com/sdk/android-2.1.html) or higher. You accomplish this by using the Android SDK and AVD Manager that was installed from the previous step.

1. Open the Android SDK and AVD Manager window:

		$ android

	_Note: if this command does not open the Android SDK and AVD Manager, then your path is not configured correctly._

2. Select Available packages from the left hand column

3. Select the checkbox for Android Repository in the main window

4. Select Android 4.0.3 (API 15)

5. Click the **Install packages...** button to complete the download and installation.

	_Note: you may want to simply install all the available updates, but be aware it will take longer, as each SDK level is a sizable download._


## Configure an Android Virtual Device

The following steps describe how to configure an Android Virtual Device (AVD) for use in running the sample application. More information can be found on the [Managing Virtual Devices](http://developer.android.com/guide/developing/devices/index.html) at the Android Developers web site.

1. Open the Android Virtual Device Manager window:

		$ android avd

2. Select the **New…** button.

3. Enter "Default" in the Name field.

	_Note: The Android Maven Plugin attempts to start an AVD with the name "Default" unless you specify an alternate emulator in the POM._

4. Select "Android 4.0.3 - API Level 15" in the Target selector.

5. Click the **Create AVD** button to finish.

	_Note: The sample application is configured for Android 2.1 as the minimum version._


## Build and Run the Android Client

The following instructions apply to each Android sample app. They should be executed from within the sample app's directory, where the project's pom.xml is located.

1. Build the app:

		$ mvn clean install

2. Start the emulator using the Android Maven Plugin:

		$ mvn android:emulator-start

	Alternatively, you can start the emulator using the Android command line tools:

		$ emulator @Default

	_IMPORTANT: Ensure the emulator is fully initialized and ready or the deploy will fail._

3. Deploy the app to the emulator:

		$ mvn android:deploy

	_Note: the Android Maven Plugin will attempt to deploy the app to all available devices, both emulators and physical devices attached to your computer._


## Troubleshooting Failed Deployment

You can view realtime logging of the app using the following command:

	$ adb logcat

If "mvn android:deploy" fails, try stopping and restarting the adb server:

	$ adb kill-server
	$ adb start-server
	$ mvn android:deploy

You can also list the available virtual devices using the following command:

	$ adb devices

## Additional Resources

* [Spring for Android project page](http://www.springsource.org/spring-android)
* [Android Maven Plugin Documentation](http://maven-android-plugin-m2site.googlecode.com/svn/plugin-info.html)
* [Android Developers web site](http://developer.android.com/index.html)
