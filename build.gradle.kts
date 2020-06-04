import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.3.0.RELEASE"
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
	war
	kotlin("jvm") version "1.3.72"
	kotlin("plugin.spring") version "1.3.72"
	id("com.expediagroup.graphql") version "3.0.0-RC8"
}

group = "com.gtomato.projects.backend"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}

	runtimeOnly ("org.postgresql:postgresql")

	implementation("com.expediagroup:graphql-kotlin-schema-generator:3.0.0-RC8")
	implementation("com.expediagroup:graphql-kotlin-spring-server:3.0.0-RC8")
	implementation("com.expediagroup:graphql-kotlin-client:3.0.0-RC8")

	implementation ("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation ( "com.squareup:kotlinpoet:1.6.0")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}

graphql {
	client {
		converters.put("UUID", com.expediagroup.graphql.plugin.generator.ScalarConverterMapping("java.util.UUID", "com.gtomato.projects.backend.graphql.UUIDScalarConverter"))
	}
}