plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.google.dagger.hilt)
}

android {
    namespace = "com.viniciusjanner.apiviacep"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.viniciusjanner.apiviacep"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "BASE_URL", "\"https://viacep.com.br/ws/\"")
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            // Usa a configuração de assinatura de debug padrão
            signingConfig = signingConfigs.getByName("debug")
        }

        getByName("debug") {
            isDebuggable = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    viewBinding {
        enable = true
    }
}

dependencies {
    // AndroidX
    implementation(libs.androidx.activity)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.coreKtx)
    implementation(libs.androidx.fragmentKtx)
    implementation(libs.androidx.lifecycle.livedataKtx)
    implementation(libs.androidx.lifecycle.viewmodelKtx)
    implementation(libs.androidx.navigation)

    // Google
    implementation(libs.google.material)

    // Squareup
    implementation(libs.squareup.okhttp3.loggingInterceptor)
    implementation(libs.squareup.okhttp3.okhttp)
    implementation(platform(libs.squareup.okhttp3.okhttp3))
    implementation(libs.squareup.retrofit2.adapterRxjava3)
    implementation(libs.squareup.retrofit2.converterGson)
    implementation(libs.squareup.retrofit2.retrofit)

    // RxJava
    implementation(libs.rxjava3.rxjava)
    implementation(libs.rxjava3.rxandroid)

    // Dagger 2
    implementation(libs.google.dagger)
    kapt(libs.google.dagger.compiler)

    // Dagger Hilt
    implementation(libs.google.dagger.hilt.android)
    kapt(libs.google.dagger.hilt.compiler)

    // Koin
    implementation(libs.koin.android)
    implementation(libs.koin.core)
}
