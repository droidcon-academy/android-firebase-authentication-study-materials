buildscript {
  dependencies {
//		classpath("com.google.gms:google-services:4.3.15")
  }
}

plugins {
  id("com.android.application") version "8.7.3" apply false
  id("com.android.library") version "8.7.3" apply false
  id("org.jetbrains.kotlin.android") version "2.0.0" apply false
  id("com.google.dagger.hilt.android") version "2.51.1" apply false
}