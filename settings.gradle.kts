rootProject.name = "routeservice"

pluginManagement {
    val smithyGradleVersion: String by settings
    plugins {
        id("software.amazon.smithy.gradle.smithy-jar").version(smithyGradleVersion)
        id("software.amazon.smithy.gradle.smithy-base").version(smithyGradleVersion)
    }

    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
    }
}

// Subprojects
include("app")
include("InAndOut-API-Modelling")
