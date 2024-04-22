@file:Suppress("UnstableApiUsage")


/*
* Copyright 2024 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     https://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
plugins {
    id("com.android.test")
    kotlin("android")
}

android {
    namespace = "com.example.simpledigital"
    compileSdk = 34

    defaultConfig {
        minSdk = 33
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.majorVersion
        allWarningsAsErrors = true
    }

    targetProjectPath = ":watchface"

    dependencies {
        implementation("androidx.test.ext:junit-ktx:1.1.5")
        implementation("androidx.test.uiautomator:uiautomator:2.3.0")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
        implementation("androidx.test:runner:1.5.2")
    }

    testOptions {
        managedDevices {
            devices {
                localDevices {
                    create("roundApi33") {
                        device = "Wear OS Large Round"
                        apiLevel = 33
                        systemImageSource = "android-wear"
                    }
                }
            }
        }
    }
}
