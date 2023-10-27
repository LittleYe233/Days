import java.util.Properties

val f = rootProject.file("credentials.properties")
var credentials: Properties? = null
if (f.exists() && f.isFile) {
    // See: https://stackoverflow.com/a/71934561/12002560
    credentials = Properties().apply {
        load(rootProject.file("credentials.properties").reader())
    }
}

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.littleye233.days"
    compileSdk = 34

    if (credentials != null) {
        // See: https://gist.github.com/mileskrell/7074c10cb3298a2c9d75e733be7061c2
        // And, how to verify: https://stackoverflow.com/a/7104680/12002560
        signingConfigs {
            create("release") {
                storeFile = credentials!!["RELEASE_STORE_FILE"]?.let { file(it) }
                storePassword = credentials!!["RELEASE_STORE_PASSWORD"].toString()
                keyAlias = credentials!!["RELEASE_KEY_ALIAS"].toString()
                keyPassword = credentials!!["RELEASE_KEY_PASSWORD"].toString()
                enableV1Signing = true
                enableV2Signing = true
                enableV3Signing = true
                enableV4Signing = true
            }
        }
    }

    defaultConfig {
        applicationId = "com.littleye233.days"
        minSdk = 26
        //noinspection EditedTargetSdkVersion
        targetSdk = 34
        // Min SDK (2-digit) + Major (1-digit) + Minor (2-digit) + Patch (1-digit) + Other (2-digit)
        // Other means Alpha or Beta version code, from 01 to 98. 99 means final version.
        // See: https://developer.android.com/google/play/publishing/multiple-apks#VersionCodes
        versionCode = 26001002
        versionName = "0.1.0-alpha-2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        // See: https://stackoverflow.com/a/71046311/12002560
        setProperty("archivesBaseName", "${rootProject.name}-${versionName}(${versionCode})")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            if (credentials != null) {
                // See: https://gist.github.com/mileskrell/7074c10cb3298a2c9d75e733be7061c2
                signingConfig = signingConfigs.getByName("release")
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3:1.1.2")
    implementation("androidx.navigation:navigation-compose:2.7.4")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}