plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-parcelize")
}

android {
    namespace = "com.bioceuticamilano"
    compileSdk = 36

    buildFeatures {
        viewBinding = true
    }

    defaultConfig {
        applicationId = "com.bioceuticamilano"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.appcompat)
//    implementation(libs.material)
//    implementation(libs.androidx.activity)
//    implementation(libs.androidx.constraintlayout)
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)
//    // Add splashscreen support for installSplashScreen()
//    implementation("androidx.core:core-splashscreen:1.0.1")
//    implementation("com.intuit.sdp:sdp-android:1.0.6")
//    implementation("com.google.code.gson:gson:2.10.1")


    var kotlin_version = "1.8.0-RC2"

    implementation ("androidx.core:core-ktx:1.12.0")
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("com.google.android.material:material:1.10.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("androidx.legacy:legacy-support-v4:1.0.0")
    implementation ("androidx.coordinatorlayout:coordinatorlayout:1.2.0")
    implementation ("androidx.navigation:navigation-fragment-ktx:2.7.4")
    implementation ("androidx.navigation:navigation-ui-ktx:2.7.4")
    implementation ("org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version")
    implementation ("com.google.firebase:firebase-messaging:23.3.0")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation ("com.google.android.gms:play-services-cast-framework:21.3.0")
    implementation ("com.google.android.gms:play-services-auth:20.7.0")
    implementation ("com.google.android.gms:play-services-maps:18.2.0")
    implementation ("com.google.android.gms:play-services-location:21.0.1")
    implementation ("com.google.android.libraries.places:places:3.3.0")
    testImplementation ("junit:junit:4.13.2")
    implementation ("com.intuit.ssp:ssp-android:1.0.6")
    //glide
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    implementation ("com.google.code.gson:gson:2.9.0")
    implementation ("com.intuit.sdp:sdp-android:1.0.6")
    //Retrofit with rx Java)
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.3")
    implementation ("io.reactivex.rxjava2:rxandroid:2.1.1")
    implementation ("io.reactivex.rxjava2:rxjava:2.2.21")
    implementation ("com.squareup.retrofit2:adapter-rxjava2:2.9.0")
//    implementation 'com.cepheuen.elegant-number-button:lib:1.0.2")
    implementation ("com.github.mukeshsolanki.android-otpview-pinview:otpview:3.1.0")


    implementation ("de.hdodenhof:circleimageview:3.1.0")
    implementation ("com.github.dhaval2404:imagepicker:2.1")
    implementation ("com.github.smarteist:Android-Image-Slider:1.4.0")
    implementation ("com.facebook.android:facebook-android-sdk:11.1.0")
    implementation ("androidx.leanback:leanback:1.2.0-alpha03")
    implementation ("com.facebook.android:facebook-login:11.1.0")
    implementation ("com.google.android.gms:play-services-auth:20.7.0")
    implementation ("com.github.Jay-Goo:RangeSeekBar:3.0.0")
    implementation ("com.github.kizitonwose:CalendarView:1.1.0")
    implementation ("com.google.android.gms:play-services-maps:18.2.0")
    implementation ("com.google.android.libraries.places:places:3.2.0")
    implementation ("com.github.mukeshsolanki:Google-Places-AutoComplete-EditText:0.0.8")
    implementation ("com.hbb20:ccp:2.7.0")
    implementation ("androidx.core:core-splashscreen:1.0.1")
    implementation ("com.facebook.shimmer:shimmer:0.5.0")
}