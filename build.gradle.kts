group = "com.shopping.inandout"
version = "0.0.1"
description = "A RESTful web service for handling customer queries and computing TSP solutions"

allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
    }
}

plugins {
    `java-library`
	application
}

application {
    mainClass = "com.shopping.inandout.routeservice.RouteServer"
}

repositories {
	mavenCentral()
    mavenLocal()
}

dependencies {
	val smithyJavaVersion: String by project

    implementation("software.amazon.smithy.java:core:$smithyJavaVersion")
	// Adds an HTTP server implementation based on netty
    implementation("software.amazon.smithy.java:server-netty:$smithyJavaVersion")
    // Adds the server implementation of the `RestJson1` protocol
    implementation("software.amazon.smithy.java:aws-server-restjson:$smithyJavaVersion")

    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    
    testCompileOnly("org.projectlombok:lombok:1.18.30")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.30")

    testImplementation("org.junit.jupiter:junit-jupiter:5.7.1")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

afterEvaluate {
    sourceSets {
        main {
            java.srcDir("build/smithyprojections/routeservice/source/java-server-codegen/com/shopping/inandout")
        }
    }
}

tasks.register<Exec>("smithyBuild") {
    group = "build"
    description = "Java models codegen from Smithy model."
    commandLine("cmd", "/c", "smithy", "build")
}

tasks.named("compileJava") {
    dependsOn("smithyBuild")
}

tasks.named<Test>("test") {
    useJUnitPlatform()

    maxHeapSize = "1G"

    testLogging {
        events("passed")
    }
}