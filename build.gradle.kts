import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	java
	id("jvm-test-suite")
	id("java-test-fixtures")
	id("org.springframework.boot") version "3.1.0"
	id("io.spring.dependency-management") version "1.1.0"
	kotlin("jvm") version "1.8.21"
	kotlin("plugin.spring") version "1.8.21"
	kotlin("plugin.jpa") version "1.8.21"
}

group = "com.amm"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

springBoot {
	mainClass.value("com.amm.fever.infrastructure.framework.FeverApplicationKt")
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.flywaydb:flyway-core")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.3")
	// reactive
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("io.reactivex.rxjava3:rxkotlin:3.0.1")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.2")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.7.2")
	// observability
	implementation("org.springframework.boot:spring-boot-starter-aop")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("io.micrometer:micrometer-tracing-bridge-brave")
	implementation("io.zipkin.reporter2:zipkin-reporter-brave")
//	implementation("io.micrometer:micrometer-tracing:1.1.2")
//	implementation("io.micrometer:micrometer-tracing-bridge-otel:1.1.2")
//	implementation("io.opentelemetry:opentelemetry-exporter-zipkin:1.27.0")
	//
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	developmentOnly("org.springframework.boot:spring-boot-docker-compose") // starts docker-compose in local to run the database
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	// test
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.cloud:spring-cloud-contract-mappings:4.0.3")
	testImplementation("org.springframework.boot:spring-boot-testcontainers")
	testFixturesApi("org.springframework.boot:spring-boot-testcontainers")
	testImplementation("org.testcontainers:junit-jupiter")
	testFixturesApi("org.testcontainers:junit-jupiter")
	testImplementation("org.testcontainers:postgresql")

	// kotlin testing
	testImplementation("org.jetbrains.kotlin:kotlin-test")
	testImplementation("com.nhaarman:mockito-kotlin:1.6.0")
	testImplementation("io.mockk:mockk:1.12.5")
	testImplementation("com.willowtreeapps.assertk:assertk-jvm:0.25")
	testImplementation("com.ninja-squad:springmockk:3.1.1")
	testImplementation("org.awaitility:awaitility:4.2.0")
	testImplementation("io.rest-assured:kotlin-extensions:5.1.1")
	testImplementation("io.rest-assured:spring-mock-mvc-kotlin-extensions:5.1.1")
	testImplementation("net.javacrumbs.json-unit:json-unit-fluent:2.35.0")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

testing {
	suites {
		fun setupDependenciesFor(moduleName: String) {
			// this is for lib dependency inheritance
			with(configurations) {
				named("${moduleName}Implementation") {
					extendsFrom(configurations["testImplementation"])
				}
				named("${moduleName}RuntimeOnly") {
					extendsFrom(configurations["testRuntimeOnly"])
				}
			}
		}

		val test by getting(JvmTestSuite::class) {
			useJUnitJupiter()
		}

		configureEach {
			if (this is JvmTestSuite) {
				useJUnitJupiter()
				dependencies {
					implementation(project())
					compileOnly(project())
					runtimeOnly(project())
					annotationProcessor(project())
				}
				targets {
					all {
						testTask.configure {
							shouldRunAfter(test)
						}
					}
				}
			}
		}

		val acceptanceTest by registering(JvmTestSuite::class)
		val contractTest by registering(JvmTestSuite::class)
		val integrationTest by registering(JvmTestSuite::class)
		setupDependenciesFor(acceptanceTest.name)
		setupDependenciesFor(contractTest.name)
		setupDependenciesFor(integrationTest.name)
		tasks.named("check") {
			dependsOn(testing.suites.named(acceptanceTest.name))
			dependsOn(testing.suites.named(contractTest.name))
			dependsOn(testing.suites.named(integrationTest.name))
		}
	}
}