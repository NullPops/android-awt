import org.jreleaser.gradle.plugin.tasks.JReleaserDeployTask
import org.jreleaser.model.Active

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("maven-publish")
    id("org.jreleaser") version "1.19.0"
}

group = "io.github.nullpops"
version = "1.0.0"

repositories {
    mavenCentral()
    google()
}

// Note we must always use 1_8 here as it's the only version
// where we can spoof java.awt classes in Android
java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

android {
    namespace = "io.github.nullpops.android.awt"
    compileSdk = 36

    defaultConfig {
        minSdk = 26

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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
        }
    }
    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

// TODO: hook up dokka
tasks.register<Jar>("javadocJar") {
    archiveClassifier.set("javadoc")
}

dependencies {
    with(libs) {
        implementation(sfntly)
        implementation(androidx.core.ktx)
        implementation(androidx.appcompat)
        implementation(material3)
        testImplementation(junit)
        androidTestImplementation(androidx.junit)
        androidTestImplementation(androidx.espresso.core)
    }
}

publishing {
    repositories {
        maven {
            name = "staging"
            url = uri(layout.buildDirectory.dir("staging-deploy"))
        }
    }
    publications {
        register<MavenPublication>("release") {
            afterEvaluate {
                from(components["release"])
            }
            artifact(tasks.named("javadocJar"))

            groupId = "io.github.nullpops"
            artifactId = "android-awt"
            version = project.version.toString()

            pom {
                name.set("android-awt")
                description.set("Android library that spoofs java.awt APIs for compatibility")
                url.set("https://github.com/NullPops/android-awt")
                licenses {
                    license {
                        name.set("AGPL-3.0-only")
                        url.set("https://www.gnu.org/licenses/agpl-3.0.html")
                        distribution.set("repo")
                    }
                    license {
                        name.set("NullPops Commercial License")
                        url.set("https://github.com/NullPops/android-awt/blob/main/LICENSE")
                        distribution.set("repo")
                    }
                }
                developers {
                    developer {
                        id.set("zeruth"); name.set("Tyler Bochard"); email.set("tylerbochard@gmail.com")
                    }
                }
                scm {
                    connection.set("scm:git:https://github.com/NullPops/android-awt.git")
                    developerConnection.set("scm:git:ssh://git@github.com/NullPops/android-awt.git")
                    url.set("https://github.com/NullPops/android-awt")
                }
            }
        }
    }
}

// Ensure the staged repo exists before JReleaser deploys
tasks.withType<JReleaserDeployTask>().configureEach {
    dependsOn("publishReleasePublicationToStagingRepository")
}

jreleaser {
    project {
        name.set("android-awt")
        version.set(project.version.toString())
        description.set("Android library that spoofs java.awt APIs for compatibility")
        links { homepage.set("https://github.com/NullPops/android-awt") }
    }

    signing {
        active.set(Active.ALWAYS)
        armored.set(true)
        mode.set(org.jreleaser.model.Signing.Mode.MEMORY)
        providers.environmentVariable("JRELEASER_GPG_PUBLIC_KEY_PATH").orNull?.let {
            publicKey.set(file(it).readText())
        }
        providers.environmentVariable("JRELEASER_GPG_PRIVATE_KEY_PATH").orNull?.let {
            secretKey.set(file(it).readText())
        }
        passphrase.set(providers.environmentVariable("JRELEASER_GPG_PASSPHRASE"))
    }

    deploy {
        maven {
            mavenCentral {
                create("central") {
                    active.set(Active.ALWAYS)
                    url.set("https://central.sonatype.com/api/v1/publisher")
                    stagingRepository(layout.buildDirectory.dir("staging-deploy").get().asFile.absolutePath)
                    applyMavenCentralRules.set(true)
                }
            }
        }
    }
}