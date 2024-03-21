import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.maven.publish)
}

group = "io.github.yamilmedina"
version = findProperty("VERSION_NAME") as String

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    testImplementation(libs.kotlin.test)
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_1_8)
    }
}

afterEvaluate {
    mavenPublishing {
        var isRelease: Boolean by extra { getEnvOrLocalProperty("IS_RELEASE", "false") == "true" }
        if (findProperty("isRelease") == true) {
            signAllPublications()
        }
    }
}

