plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.gms.google-services")
    id("maven-publish")
}

android {
    namespace = "com.xynos.analyticservice"
    compileSdk = 34

    defaultConfig {
        minSdk = 29

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {
    implementation("androidx.room:room-runtime:2.5.2")
    implementation("androidx.room:room-ktx:2.5.2")
    kapt("androidx.room:room-compiler:2.5.2")

    implementation ("com.google.dagger:dagger:2.46.1")
    kapt ("com.google.dagger:dagger-compiler:2.46.1")

    implementation("com.google.firebase:firebase-firestore-ktx:24.8.1")
    implementation("androidx.work:work-runtime-ktx:2.8.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.0")

    implementation("androidx.core:core-ktx:1.12.0")
}

afterEvaluate {
    configure<PublishingExtension> {
        publications {
            create<MavenPublication>("mavenJava") {
                groupId = "com.github.vylhart"
                artifactId = "Analytics"
                version = "0.0.9"

                // Customize the POM file
                pom {
                    name.set("Analytics Service")
                    description.set("A library for XYZ functionality.")
                    url.set("https://github.com/vylhart/Analytics")

                    licenses {
                        license {
                            name.set("The Apache License, Version 2.0")
                            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }

                    developers {
                        developer {
                            id.set("vylhart")
                            name.set("Shashank")
                            email.set("shashankcrimson@gmail.com")
                        }
                    }

                    scm {
                        connection.set("scm:git:github.com/vylhart/Analytics.git")
                        developerConnection.set("scm:git:ssh://github.com/vylhart/Analytics.git")
                        url.set("https://github.com/vylhart/Analytics")
                    }
                }
            }
        }
    }
}
