plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.serialization)
    alias(libs.plugins.secrets.gradle.plugin)
    jacoco
}

android {
    namespace = "dev.nhonnq.app"
    compileSdk = 35

    defaultConfig {
        applicationId = "dev.nhonnq.app"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField(
            "String",
            "PEXELS_API_KEY",
            "\"${properties["PEXELS_API_KEY"]}\""
        )
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
    signingConfigs {
        create("production") {
            storeFile = file(System.getenv("KEYSTORE_PATH") ?: "../keystores/production.keystore")
            storePassword = System.getenv("KEYSTORE_PASSWORD") ?: ""
            keyAlias = System.getenv("KEY_ALIAS") ?: ""
            keyPassword = System.getenv("KEY_PASSWORD") ?: ""
        }
    }

    flavorDimensions.add("environment")
    productFlavors {
        create("development") {
            applicationIdSuffix = ".dev"
        }
        create("production") {
            applicationIdSuffix = ".prod"
            signingConfig = signingConfigs.getByName("production")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.13"
    }

    kotlinOptions {
        freeCompilerArgs += listOf("-Xjvm-default=all")
    }
}

jacoco {
    toolVersion = "0.8.7"
}

tasks.register("jacocoTestReport", JacocoReport::class) {
    dependsOn("testDevelopmentDebugUnitTest")

    reports {
        xml.required.set(true)
        html.required.set(true)
    }

    val fileFilter = listOf("**/R.class", "**/R$*.class", "**/BuildConfig.*", "**/Manifest*.*")
    val debugTree = fileTree("${layout.buildDirectory}/intermediates/javac/developmentDebug") {
        exclude(fileFilter)
    }
    val mainSrc = "${project.projectDir}/src/main/java"

    sourceDirectories.setFrom(files(mainSrc))
    classDirectories.setFrom(files(debugTree))
    executionData.setFrom(files("${layout.buildDirectory}/jacoco/testDevelopmentDebugUnitTest.exec"))
}

dependencies {

    implementation(project(":data"))

    // AndroidX
    implementation(libs.appcompat)
    implementation(libs.lifecycle.viewmodel.ktx)

    // Extensions
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.extensions)

    // okHttp
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    // GSON
    implementation(libs.gson)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.retrofit2.kotlin.coroutines.adapter)

    // Room
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)

    // Hilt
    implementation(libs.hilt.dagger.android)
    ksp(libs.hilt.dagger.compiler)
    ksp(libs.hilt.compiler)

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)

    // Paging
    implementation(libs.paging.compose)

    // Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.ui.util)
    implementation(libs.compose.animation)
    implementation(libs.compose.runtime)
    implementation(libs.compose.runtime.saveable)
    implementation(libs.compose.material3)
    implementation(libs.compose.material)
    implementation(libs.compose.material.icons.extended)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.accompanist.systemuicontroller)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)

    // Coil
    implementation(libs.coil.compose)

    // Navigation
    implementation(libs.navigation.compose)
    implementation(libs.hilt.navigation.compose)

    // Serialization
    implementation(libs.kotlinx.serialization.json)

    // UnitTest
    testImplementation(project(":testing"))
    testImplementation(libs.androidx.ui.test.junit4.android)
    testImplementation(libs.robolectric)

    // Android test
    androidTestImplementation(libs.monitor)
    androidTestImplementation(libs.junit.ktx)

}

// 2. Optionally configure the plugin
secrets {
    // Optionally specify a different file name containing your secrets.
    // The plugin defaults to "local.properties"
    propertiesFileName = "secrets.properties"
    defaultPropertiesFileName = "secrets.properties"

    // Add keys that the plugin should ignore from the properties file
    ignoreList.add("keyToIgnore")
    ignoreList.add("ignore*")
}