import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.sqldelight)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    jvm()

    js {
        browser()
        binaries.executable()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }

    sourceSets {
        val sqlDelightMain by creating {
            dependsOn(commonMain.get())
            dependencies {
                // SQLDelight - only for platforms that support it
                implementation(libs.sqldelight.runtime)
                implementation(libs.sqldelight.coroutines)
            }
        }

        val webMain by creating {
            dependsOn(commonMain.get())
        }

        androidMain {
            dependsOn(sqlDelightMain)
            dependencies {
                implementation(compose.preview)
                implementation(libs.androidx.activity.compose)

                // Ktor Android
                implementation(libs.ktor.client.okhttp)

                // SQLDelight Android
                implementation(libs.sqldelight.android)

                // Koin Android
                implementation(libs.koin.android)
            }
        }

        commonMain.dependencies {
            // Compose
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            // Lifecycle
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            // Coroutines
            implementation(libs.kotlinx.coroutines.core)

            // Serialization
            implementation(libs.kotlinx.serialization.json)

            // DateTime
            implementation(libs.kotlinx.datetime)

            // Ktor
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.auth)

            // Koin
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            // Voyager Navigation
            implementation(libs.voyager.navigator)
            implementation(libs.voyager.screenmodel)
            implementation(libs.voyager.bottomsheet)
            implementation(libs.voyager.tab)
            implementation(libs.voyager.transitions)
            implementation(libs.voyager.koin)

            // Image Loading
            implementation(libs.kamel.image)
            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)

            // Logging
            implementation(libs.napier)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        iosMain {
            dependsOn(sqlDelightMain)
            dependencies {
                // Ktor iOS
                implementation(libs.ktor.client.darwin)

                // SQLDelight iOS
                implementation(libs.sqldelight.native)
            }
        }

        jvmMain {
            dependsOn(sqlDelightMain)
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(libs.kotlinx.coroutinesSwing)

                // Ktor JVM
                implementation(libs.ktor.client.okhttp)

                // SQLDelight JVM
                implementation(libs.sqldelight.jvm)
            }
        }

        jsMain {
            dependsOn(webMain)
            dependsOn(sqlDelightMain)
            dependencies {
                // Ktor JS
                implementation(libs.ktor.client.js)

                // SQLDelight JS
                implementation(libs.sqldelight.js)
            }
        }

        wasmJsMain {
            dependsOn(webMain)
            dependencies {
                // Ktor Wasm
                implementation(libs.ktor.client.js)
            }
        }
    }
}

android {
    namespace = "com.elixirgym.elixir"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.elixirgym.elixir"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "com.elixirgym.elixir.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.elixirgym.elixir"
            packageVersion = "1.0.0"
        }
    }
}

sqldelight {
    databases {
        create("ElixirDatabase") {
            packageName.set("com.elixirgym.elixir.database")
        }
    }
}
