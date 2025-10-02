// Top-level build file where you can add configuration options common to all sub-projects/modules. plugins {

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    kotlin("plugin.compose") version libs.versions.kotlin.get() apply false
    kotlin("jvm") version libs.versions.kotlin.get() apply false

    id("com.google.gms.google-services") version "4.4.3" apply false
    id("com.google.dagger.hilt.android") version "2.56.1" apply false
    alias(libs.plugins.devtools.ksp) apply false
}
