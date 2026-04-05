group = "com.shopping.inandout"
version = "0.0.1"
description = "A RESTful web service for handling customer queries and computing TSP solutions"

plugins {
	application
    id("software.amazon.smithy.gradle.smithy-base")
    jacoco
}

application {
    mainClass = "com.shopping.inandout.routeservice.RouteServiceWrapper"
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

dependencies {
	val smithyJavaVersion: String by project
	
	// Smithy java model generation
    smithyBuild("software.amazon.smithy.java.codegen:plugins:$smithyJavaVersion")
    implementation(project(":InAndOut-API-Modelling"))

    // Adds an HTTP server implementation based on netty
    implementation("software.amazon.smithy.java:server-netty:$smithyJavaVersion")
    // Adds the server implementation of the `RestJson1` protocol
    implementation("software.amazon.smithy.java:aws-server-restjson:$smithyJavaVersion")

    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")

    implementation("com.google.dagger:dagger:2.51.1")
    annotationProcessor("com.google.dagger:dagger-compiler:2.51.1")
    
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.1")
    testImplementation("org.mockito:mockito-junit-jupiter:5.11.0")
    testImplementation("org.mockito:mockito-core:5.11.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

// Add generated source code to the compilation sourceSet
afterEvaluate {
    val serverPath = smithy.getPluginProjectionPath(smithy.sourceProjection.get(), "java-server-codegen")
    sourceSets.main.get().java.srcDir(serverPath)
}

tasks.named("compileJava") {
    dependsOn("smithyBuild")
}

tasks.named<Test>("test") {
    useJUnitPlatform()

    maxHeapSize = "1G"
    testLogging {
        events("passed", "skipped", "failed")
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }
}
