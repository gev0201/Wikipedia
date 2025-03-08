# Wikipedia App Automation Framework

This framework based on Page Object Model (POM) design pattern using:
- Java,
- Appium,
- TestNG,
- Gradle,

Framework designed to automate test cases of the Wikipedia mobile application on Android and iOS devices.

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Project Structure](#project-structure)
- [Reporting](#reporting)

## Overview
This framework implemented to create reusable and maintainable test scripts for the Wikipedia app.
It uses Appium for mobile automation, TestNG as a test runner for test management, and Gradle for build.
The framework supporting parallel execution on multiple Android and iOS devices, with detailed logging to a *.log file to make maintaining and debugging process easier.

## Features
- Cross-platform - for Android and iOS.
- Thread-safe parallel execution using `ThreadLocal` for multiple devices.
- Page Object Model for maintainable test scripts.
- Logging with SLF4J.
- TestNG integration.
- TestNG assertion utility with failure logging.
- Gradle-based build tool system.

## Prerequisites
### Software Requirements
- **Java**: JDK 8 or later.
- **Gradle**: verify with "gradlew --version".
- **Node.js**: Required for Appium.
- **Appium**: Install via npm > "npm  install -g appium".
- **Android SDK**: For Android testing.
- **Xcode**: For iOS testing
- **Homebrew**: For macOS dependencies.
- **IDE**: IntelliJ IDEA or similar IDE.

***IMPORTANT***

As a prerequisite you need to have the Fresh version of the `Wikipedia` app installed on you mobile devices.


### Hardware Requirements
- **Android Devices**: At least one Android device with USB debugging enabled.
- **Android Emulator**: Android Studio or similar app for Android emulation.
- **iOS Device**: At least one iOS device (macOS required) with Developer Mode enabled.
- **iOS Device**: IOS Simulator - Xcode.
- **USB Cables**: To connect devices to the machine.

## Project Structure

***HighWayTask/***
- src/test/java/pages/ 
- * WikiHomePage.java 
- * SearchPage.java 
- * ResultPage.java
- tests/
- * WikipediaTests.java
- utils/
- * Driver.java
- src/test/resources/
- * logback.xml        
- * testng.xml
- build.gradle          
- gradlew                
- gradlew.bat            
- logs/                 
- * appium-tests.log

## Reporting

- Standard `TestNG` Reporting with *.HTML format and, test results with "*.XML" file-format.