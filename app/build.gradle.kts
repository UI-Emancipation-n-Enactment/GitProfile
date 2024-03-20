plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs.kotlin")
    id("org.jetbrains.kotlin.plugin.parcelize")
    id("com.google.devtools.ksp")
}

android {
    namespace = "demo.gitprofiles"
    compileSdk = 34

    defaultConfig {
        applicationId = "demo.gitprofiles"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", "${properties["BASE_URL_VALUE"]}")
        }
        release {
            buildConfigField("String", "BASE_URL", "${properties["BASE_URL_VALUE"]}")
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
        viewBinding = true
        buildFeatures {
            buildConfig = true
//        dataBinding = true
        }
    }
}

dependencies {

    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    val archVersion = "2.2.0"
    val coroutineVersion = "1.7.3"
    val roomVersion = "2.6.1"
    val daggerVersion = "2.48"
    val lifecycleVersion = "2.7.0"
    val truthVersion = "1.1.4"
    val navigationVersion = "2.7.6"
    val glideVersion="4.16.0"
    val fragmentTestingVersion = "1.6.2"

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutineVersion")

    //lifeCycle, viewModel, liveData
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
    implementation("androidx.activity:activity-ktx:1.8.0")

    //Room
    implementation("androidx.room:room-runtime:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    ksp("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.5.0")

    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // navigation
    implementation("androidx.navigation:navigation-fragment-ktx:$navigationVersion")
    implementation ("androidx.navigation:navigation-runtime-ktx:$navigationVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navigationVersion")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.github.bumptech.glide:glide:$glideVersion")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")

    implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")

    ksp("com.github.bumptech.glide:ksp:4.14.2")


    //Glide for image-handling from backEnd
    implementation("com.github.bumptech.glide:glide:$glideVersion")

    //dagger
    implementation("com.google.dagger:dagger:$daggerVersion")
    ksp("com.google.dagger:dagger-compiler:$daggerVersion")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")


//    implementation("androidx.core:core-ktx:1.12.0")
//    implementation("androidx.appcompat:appcompat:1.6.1")
//    implementation("com.google.android.material:material:1.11.0")
//    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
//    testImplementation("junit:junit:4.13.2")
//    androidTestImplementation("androidx.test.ext:junit:1.1.5")
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}