# Android Vertical Stepper View

> A vertical stepper implementation of the [material design specification][1]

[![](https://jitpack.io/v/liefery/android-vertical-stepper-view.svg)](https://jitpack.io/#liefery/android-vertical-stepper-view)

![Sample app screenshots](https://liefery.github.io/android-vertical-stepper-view/screenshots.png)

## Feature Overview

- Sticks closely to the [material design guidelines][1]
- Maintains state across configuration changes
- Allows to go back to completed steps
- Highly customizable (e.g. the navigation buttons are provided by the user)

The library is currently lacking animations between step transitions.

## Installation

### sbt

```scala
resolvers += "jitpack" at "https://jitpack.io"

libraryDependencies += "com.github.liefery" % "android-vertical-stepper-view" % "0.2.1"
```

### Gradle

```groovy
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}

dependencies {
    compile 'com.github.liefery:android-vertical-stepper-view:0.2.1'
}
```

## Usage

Please take a look at the [sample][2] application.

[1]: https://material.io/guidelines/components/steppers.html
[2]: /sample/src/main/java/com/liefery/android/vertical_stepper_view/sample
