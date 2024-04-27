import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.KotlinCompile

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
    kotlin("plugin.serialization") version "1.8.20"
   // id("app.cash.sqldelight") version "2.0.0-alpha05"
}

group = "com.utm.application"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    implementation(compose.desktop.currentOs)

    val voyagerVersion = "1.0.0-rc10"
    implementation("cafe.adriel.voyager:voyager-navigator:$voyagerVersion")
    implementation("cafe.adriel.voyager:voyager-tab-navigator:$voyagerVersion")
    implementation("cafe.adriel.voyager:voyager-transitions:$voyagerVersion")

    implementation("dev.gitlive:firebase-auth:1.12.0")

    //implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:0.30.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0") // Ensure version matches with coroutines usage
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.1")

    implementation("io.ktor:ktor-client-cio-jvm:2.3.2")
    implementation("io.ktor:ktor-client-okhttp:2.3.6")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.2")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.2")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation ("io.ktor:ktor-client-logging:2.3.2")
    implementation("org.slf4j:slf4j-simple:2.0.9")

    implementation("org.jetbrains.exposed:exposed-core:0.40.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.40.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.40.1")
    implementation("org.xerial:sqlite-jdbc:3.45.3.0")

    //SQL delight
    /*implementation("app.cash.sqldelight:sqlite-driver:2.0.0-alpha05")
    implementation("app.cash.sqldelight:runtime:2.0.0-alpha05")*/

    /*implementation("io.ktor:ktor-client-core:1.5.4")
    implementation("io.ktor:ktor-client-cio:1.5.4")

    implementation ("io.ktor:ktor-client-json:1.5.4")
    implementation ("io.ktor:ktor-client-serialization:1.5.4")
    implementation("io.ktor:ktor-client-serialization:1.5.4")*/





}


compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Exe , TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "UTM"
            packageVersion = "1.0.0"
            windows {
                packageVersion = "1.0.0"
                msiPackageVersion = "1.0.0"
                exePackageVersion = "1.0.0"
            }
        }
    }
}
