@file:Suppress("UnstableApiUsage")

plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.android")
  id("kotlin-kapt")
  id("com.google.dagger.hilt.android")
  id("org.jetbrains.kotlin.plugin.compose") version ("2.0.0")
//	id("com.google.gms.google-services")
}

android {
  namespace = "com.droidcon.authenticate"
  compileSdk = 35

  defaultConfig {
    applicationId = "com.droidcon.authenticate"
    minSdk = 24
    targetSdk = 35
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    vectorDrawables {
      useSupportLibrary = true
    }
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
      )
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
  kotlinOptions {
    jvmTarget = "17"
  }
  buildFeatures {
    compose = true
  }
  composeOptions {
    kotlinCompilerExtensionVersion = "1.4.6"
  }
  packaging {
    resources {
      excludes.add("/META-INF/{AL2.0,LGPL2.1}")
    }
  }
}

dependencies {
  implementation("androidx.core:core-ktx:1.10.1")
  implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
  implementation("androidx.activity:activity-compose:1.7.1")
  implementation(platform("androidx.compose:compose-bom:2022.10.00"))
  implementation("androidx.compose.ui:ui")
  implementation("androidx.compose.ui:ui-graphics")
  implementation("androidx.compose.ui:ui-tooling-preview")
  implementation("androidx.compose.material3:material3-android:1.2.0-alpha02")
  implementation("com.jakewharton.timber:timber:5.0.1")

  implementation(platform("com.google.firebase:firebase-bom:32.0.0"))
  implementation("com.google.firebase:firebase-auth-ktx")
  implementation("com.google.firebase:firebase-firestore-ktx:24.8.1")
  implementation("com.google.android.gms:play-services-auth:20.6.0")
  implementation("com.facebook.android:facebook-android-sdk:16.1.3")
//	implementation("com.firebaseui:firebase-ui-auth:7.2.0")

  implementation("com.google.dagger:hilt-android:2.51.1")
  kapt("com.google.dagger:hilt-compiler:2.51.1")

  androidTestImplementation("com.google.dagger:hilt-android-testing:2.51.1")
  kaptAndroidTest("com.google.dagger:hilt-compiler:2.51.1")

  testImplementation("com.google.dagger:hilt-android-testing:2.51.1")
  kaptTest("com.google.dagger:hilt-compiler:2.51.1")

  implementation("androidx.navigation:navigation-compose:2.6.0")
  implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

  testImplementation("junit:junit:4.13.2")
  androidTestImplementation("androidx.test.ext:junit:1.1.5")
  androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
  androidTestImplementation(platform("androidx.compose:compose-bom:2022.10.00"))
  androidTestImplementation("androidx.compose.ui:ui-test-junit4")

  debugImplementation("androidx.compose.ui:ui-tooling")
  debugImplementation("androidx.compose.ui:ui-test-manifest")
}

kapt {
  correctErrorTypes = true
}