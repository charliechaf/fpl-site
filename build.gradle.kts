plugins {
    kotlin("js") version "2.1.0"
    kotlin("plugin.serialization") version "2.1.0"
}

group = "com.fpl"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
}

kotlin {
    js(IR) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
            }
        }
        useEsModules()
    }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-html:0.11.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
    
    // React dependencies - basic set  
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react:18.2.0-pre.346")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:18.2.0-pre.346")
}

tasks.named("browserDevelopmentRun") {
    doFirst {
        println("Starting development server on http://localhost:3000")
    }
}