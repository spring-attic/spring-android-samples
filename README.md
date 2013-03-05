# Spring for Android Samples

## Overview

This repository contains sample projects illustrating usage of [Spring for Android]. Each project requires set up of the [Android SDK], command line tools, [Maven], the [Android Maven Plugin], and Android Maven artifacts such as Spring dependencies. This document walks through that setup.


## Sample Apps

The following sample applications demonstrate the capabilities of [Spring for Android]. See the README in each directory for more information on the sample project and additional instructions.

* spring-android-showcase - The showcase contains client and server apps, and illustrates the features of Spring for Android.

* spring-android-news-reader - The news reader highlights the use of the Android ROME Feed Reader by retrieving RSS and Atom news feeds.

* spring-android-twitter-search - The Twitter search app demonstrates an Android project including Spring for Android jars without the use of Maven's dependency management.

* spring-android-twitter-client - A Twitter client demonstrating Spring Social on Android

* spring-android-facebook-client - A Facebook client demonstrating Spring Social on Android

* spring-android-basic-auth - These client and server applications show how to make Basic Auth requests using RestTemplate on Android and connecting to a Spring MVC site.


## Development Environment

The [Android SDK] is required for developing Android applications. Google provides command line tools, and an [Eclipse plugin] for building Android applications, however you are not restricted to only those options. The [Android Maven Plugin] makes use of the Android SDK command line tools to compile and deploy the app to the emulator, so there is no need for a separate IDE setup or configuration.

### Install the Android SDK

1. Download the correct version of the [Android SDK] for your operating system from the Android web site.

1. Unzip the archive and place it in a location of your choosing. For example on a Mac, you may want to place it in the root of your user directory. See the download web site for additional [installation details].

2. Add Android to your path. The following is an example .bash_profile on a OS X:

		$ export ANDROID_HOME=~/android-sdk-macosx
		$ export PATH=${PATH}:$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools'

### Install Android SDK Platform(s)

The Android SDK download does not include any specific Android platform SDKs. In order to run the sample code you need to download and install the [Android 2.1 SDK Platform] or higher. You accomplish this by using the Android SDK and AVD Manager that was installed from the previous step.

1. Open the Android SDK and AVD Manager window:

		$ android

	_Note: if this command does not open the Android SDK and AVD Manager, then your path is not configured correctly._
	
2. Select the checkbox for "Tools"

2. Select the checkbox for the latest Android SDK, "Android 4.2.2 (API 17)" as of this writing

3. Click the **Install packages...** button to complete the download and installation.

	_Note: you may want to simply install all the available updates, but be aware it will take longer, as each SDK level is a sizable download._


## Configure an Android Virtual Device

The following steps describe how to configure an Android Virtual Device (AVD) for use in running the sample application. More information can be found on the [Managing Virtual Devices] at the Android Developers web site.

1. Open the Android Virtual Device Manager window:

		$ android avd

2. Select the **New…** button

3. Enter "Default" in the Name field

	_Note: The Android Maven Plugin attempts to start an AVD with the name "Default" unless you specify an alternate emulator in the POM._
	
4. Select "Nexus 7" as the device

5. Select "Android 4.2.2 - API Level 17" for the target

6. Select "ARM (armeabi-v7a)" for the CPU

7. Click the **OK** button to finish.

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


## Contributing

[Pull requests] are welcome. See the [contributor guidelines] for details.


## Additional Resources

* [Spring for Android project page]
* [Android Maven Plugin Documentation]
* [Android Developers web site]


## License

[Spring for Android] is released under version 2.0 of the [Apache License].


[Spring for Android]: http://www.springsource.org/spring-android
[Android SDK]: http://developer.android.com/sdk/index.html
[Maven]: http://maven.apache.org
[Android Maven Plugin]: http://code.google.com/p/maven-android-plugin
[Eclipse Plugin]: http://developer.android.com/sdk/eclipse-adt.html
[installation details]: http://developer.android.com/sdk/installing.html
[Android 2.1 SDK Platform]: http://developer.android.com/sdk/android-2.1.html
[Managing Virtual Devices]: http://developer.android.com/tools/devices/index.html
[Pull requests]: http://help.github.com/send-pull-requests
[contributor guidelines]: https://github.com/SpringSource/spring-android/wiki/Contributor-Guidelines
[Spring for Android project page]: http://www.springsource.org/spring-android
[Android Maven Plugin Documentation]: http://maven-android-plugin-m2site.googlecode.com/svn/plugin-info.html
[Android Developers web site]: http://developer.android.com/index.html
[Apache license]: http://www.apache.org/licenses/LICENSE-2.0
