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
        var mavenCentralUsername: String by extra { getEnvOrLocalProperty("OSSRH_USERNAME", "") }
        var mavenCentralPassword: String by extra { getEnvOrLocalProperty("OSSRH_PASSWORD", "") }
        var signingInMemoryKeyId: String by extra { getEnvOrLocalProperty("SIGNING_KEYID", "") }
        var signingInMemoryKey: String by extra { getEnvOrLocalProperty("SIGNING_SECRETKEYRINGFILE", "") }
        var signingInMemoryKeyPassword: String by extra { getEnvOrLocalProperty("SIGNING_PASSWORD", "") }
        var isRelease: Boolean by extra { getEnvOrLocalProperty("IS_RELEASE", "false") == "true" }

        if (findProperty("isRelease") == true) {
            signAllPublications()
        }
    }
}

