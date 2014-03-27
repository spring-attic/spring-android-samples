# Spring for Android Samples 

[!["Build Status"](https://build.spring.io/plugins/servlet/buildStatusImage/ANDROID-SAMPLES)](https://build.spring.io/browse/ANDROID-SAMPLES)

This repository contains sample projects illustrating usage of [Spring for Android][spring-android]. Each project requires set up of the [Android SDK], command line tools, [Maven], the [Android Maven Plugin], and Android Maven artifacts such as Spring dependencies. Gradle build files are also available for each project. This document walks through the setup. See [Spring for Android on GitHub] for more information about building from source.


## Sample Applications

The following sample applications demonstrate the capabilities of [Spring for Android]. See the README in each directory for more information on the sample project and additional instructions.

* [spring-android-showcase] - The showcase contains client and server apps, and illustrates the features of Spring for Android.

* [spring-android-news-reader] - The news reader highlights the use of the Android ROME Feed Reader by retrieving RSS and Atom news feeds.

* [spring-android-twitter-search] - The Twitter search app demonstrates an Android project including Spring for Android jars without the use of Maven's dependency management.

* [spring-android-twitter-client] - A Twitter client demonstrating Spring Social on Android

* [spring-android-facebook-client] - A Facebook client demonstrating Spring Social on Android

* [spring-android-basic-auth] - These client and server applications show how to make Basic Auth requests using RestTemplate on Android and connecting to a Spring MVC site.


## Development Environment

The [Android SDK] is required for developing Android applications. Google provides command line tools, and an [Eclipse plugin] for building Android applications, however you are not restricted to only those options. The [Android Maven Plugin] makes use of the Android SDK command line tools to compile and deploy the app to the emulator, so there is no need for a separate IDE setup or configuration.

### Install the Android SDK

1. Download the correct version of the [Android SDK] for your operating system from the Android web site.

2. Unzip the archive and place it in a location of your choosing. For example on a Mac, you may want to place it in the root of your user directory. See the download web site for additional [installation details].

3. Add Android to your path. The following is an example bash configuration on a OS X:

    ```sh
    $ export ANDROID_HOME=~/android-sdk-macosx
    $ export PATH=${PATH}:$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools
    ```

### Install Android SDK Platform(s)

The Android SDK download does not include any specific Android platform SDKs. In order to run the sample code you need to download and install the [Android 2.1 SDK Platform] or higher. You accomplish this by using the Android SDK and AVD Manager that was installed from the previous step.

1. Open the Android SDK Manager window:

    ```sh
    $ android
    ```

    > **Note**: if this command does not open the Android SDK Manager, then your path is not configured correctly.
    
2. Select the checkbox for "Tools"

3. Select the checkbox for the latest Android SDK

4. Click the **Install packages...** button to complete the download and installation.

    > **Note**: you may want to simply install all the available updates, but be aware it will take longer, as each SDK level is a sizable download.


## Configure an Android Virtual Device

The following steps describe how to configure an Android Virtual Device (AVD) for use in running the sample application. More information can be found on the [Managing Virtual Devices] at the Android Developers web site.

1. Open the Android Virtual Device Manager window:

    ```sh
    $ android avd
    ```

2. Select the **New…** button

3. Enter "Default" in the Name field

    > **Note**: The Android Maven Plugin attempts to start an AVD with the name "Default" unless you specify an alternate emulator in the POM.
    
4. Select "Nexus 7" as the device

5. Select the highest available API level for the target

    > **Note**: The sample application is configured for Android 2.1 as the minimum version.

6. Select "ARM (armeabi-v7a)" for the CPU

	> **Note**: The Intel x86 Emulator Accelerator (HAXM) can be used for better performance.

7. Click the **OK** button to finish.


## Build and Run the App

The following instructions apply to each Android sample app. They should be executed from within the sample app's directory, where the project's pom.xml is located.

1. Start the emulator using the Android Maven Plugin:

    ```sh
    $ mvn android:emulator-start
    ```

    Alternatively, you can start the emulator using the Android command line tools:

    ```sh
    $ emulator @Default
    ```

    > **Important**: Ensure the emulator is fully initialized and ready or the deploy will fail.

2. Build the app:

    ```sh
    $ mvn clean package
    ```

3. Deploy and run the app:

    ```sh
    $ mvn android:deploy android:run
    ```

    > **Note**: the Android Maven Plugin will attempt to deploy the app to all available devices, both emulators and physical devices attached to your computer.


## Troubleshoot Failed Deployment

View realtime logging of the app using this command:

```sh
$ adb logcat
```

If "mvn android:deploy" fails, stop and restart the adb server:

```sh
$ adb kill-server
$ adb start-server
$ mvn android:deploy
```

List the available virtual devices using this command:

```sh
$ adb devices
```


## Contribute

[Pull requests] are welcome. See the [contributor guidelines] for details.


## Additional Resources

 - [Spring for Android project page][spring-android]
 - [Gradle Plugin User Guide]
 - [Android Maven Plugin Documentation]
 - [Android Developers web site]


## License

[Spring for Android][spring-android] is released under version 2.0 of the [Apache License].


[spring-android]: http://spring.io/projects/spring-android
[Android SDK]: http://developer.android.com/sdk/index.html
[Maven]: http://maven.apache.org
[Android Maven Plugin]: http://code.google.com/p/maven-android-plugin
[Spring for Android on GitHub]: https://github.com/spring-projects/spring-android
[spring-android-showcase]: https://github.com/spring-projects/spring-android-samples/tree/master/spring-android-showcase
[spring-android-news-reader]: https://github.com/spring-projects/spring-android-samples/tree/master/spring-android-news-reader
[spring-android-twitter-search]: https://github.com/spring-projects/spring-android-samples/tree/master/spring-android-twitter-search
[spring-android-twitter-client]: https://github.com/spring-projects/spring-android-samples/tree/master/spring-android-twitter-client
[spring-android-facebook-client]: https://github.com/spring-projects/spring-android-samples/tree/master/spring-android-facebook-client
[spring-android-basic-auth]: https://github.com/spring-projects/spring-android-samples/tree/master/spring-android-basic-auth
[Eclipse Plugin]: http://developer.android.com/sdk/eclipse-adt.html
[installation details]: http://developer.android.com/sdk/installing.html
[Android 2.1 SDK Platform]: http://developer.android.com/sdk/android-2.1.html
[Managing Virtual Devices]: http://developer.android.com/tools/devices/index.html
[Pull requests]: http://help.github.com/send-pull-requests
[contributor guidelines]: https://github.com/spring-projects/spring-android/wiki/Contributor-Guidelines
[Gradle Plugin User Guide]: http://tools.android.com/tech-docs/new-build-system/user-guide
[Android Maven Plugin Documentation]: http://maven-android-plugin-m2site.googlecode.com/svn/plugin-info.html
[Android Developers web site]: http://developer.android.com/index.html
[Apache license]: http://www.apache.org/licenses/LICENSE-2.0
