import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.maven.publish)
    alias(libs.plugins.kover)
    antlr
}

group = "io.github.yamilmedina"
version = findProperty("VERSION_NAME") as String

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    antlr(libs.antlr.antlr4)
    implementation(libs.antlr.runtime)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.junit.params)
}

sourceSets {
    main {
        java.srcDir(file("${rootDir}/generated/antlr/"))
        kotlin.srcDir(file("${rootDir}/generated/antlr/"))
    }
}

tasks.generateGrammarSource {
    maxHeapSize = "64m"
    arguments = arguments + listOf("-visitor", "-long-messages")
    arguments = arguments + listOf("-package", "io.github.yamilmedina.kron.antlr")
    outputDirectory = file("${rootDir}/generated/antlr/io/github/yamilmedina/kron/antlr")
}

tasks.clean {
    delete = setOf(layout.buildDirectory.get(), file("${rootDir}/generated"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.compileKotlin {
    dependsOn(tasks.generateGrammarSource)
}

tasks.compileTestKotlin {
    dependsOn(tasks.generateGrammarSource)
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

