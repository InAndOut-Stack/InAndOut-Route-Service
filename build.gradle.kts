plugins {
	java
	application
	id("org.springframework.boot") version "4.0.3"
	id("io.spring.dependency-management") version "1.1.7"
	id("org.graalvm.buildtools.native") version "0.11.4"
}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

application {
    mainClass = "com.shopping.inandout.routeservice.RouteService"
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

group = "com.shopping.inandout"
version = "0.0.1-SNAPSHOT"
description = "A RESTful web service for handling customer queries and computing TSP solutions"

repositories {
	mavenCentral()
    mavenLocal()
}

val smithyJavaVersion: String by project

dependencies {
    implementation("software.amazon.smithy.java:client-core:0.0.3")
    implementation("software.amazon.smithy.java.codegen:plugins:$smithyJavaVersion")
    // Core library for the Java client
    // Adds the server implementation of the `RestJson1` protocol
    implementation("software.amazon.smithy.java:aws-server-restjson:$smithyJavaVersion")
    // Adds an HTTP server implementation based on netty
    implementation("software.amazon.smithy.java:server-netty:$smithyJavaVersion")

	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-r2dbc")
	implementation("org.springframework.boot:spring-boot-starter-restclient")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-security-oauth2-authorization-server")
	implementation("org.springframework.boot:spring-boot-starter-security-oauth2-client")
	implementation("org.springframework.boot:spring-boot-starter-session-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-webclient")
	implementation("org.springframework.boot:spring-boot-starter-webmvc")
	implementation("org.springframework.boot:spring-boot-starter-webservices")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-jdbc-test")
	testImplementation("org.springframework.boot:spring-boot-starter-r2dbc-test")
	testImplementation("org.springframework.boot:spring-boot-starter-restclient-test")
	testImplementation("org.springframework.boot:spring-boot-starter-security-oauth2-authorization-server-test")
	testImplementation("org.springframework.boot:spring-boot-starter-security-oauth2-client-test")
	testImplementation("org.springframework.boot:spring-boot-starter-security-test")
	testImplementation("org.springframework.boot:spring-boot-starter-session-jdbc-test")
	testImplementation("org.springframework.boot:spring-boot-starter-webclient-test")
	testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
	testImplementation("org.springframework.boot:spring-boot-starter-webservices-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testRuntimeOnly("com.h2database:h2")
	testRuntimeOnly("io.r2dbc:r2dbc-h2")
}

afterEvaluate {
    sourceSets {
        main {
            java.srcDir("build/java-route-service/java-server-codegen/com/shopping/inandout")
        }
    }
}

tasks.register<Exec>("smithyBuild") {
    group = "build"
    description = "Java models codegen from Smithy model."
    commandLine("cmd", "/c", "smithy", "build",
		"--config", ".\\InAndOut-API-Modelling\\smithy-build.json",
		"--projection", "java-route-service",
		"--output", "build/")
}

tasks.named("compileJava") {
    dependsOn("smithyBuild")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
