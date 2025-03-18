import org.gradle.internal.declarativedsl.schemaBuilder.isPublic

plugins {
	java
	id("org.springframework.boot") version "3.4.3"
	id("io.spring.dependency-management") version "1.1.7"
	id("org.jooq.jooq-codegen-gradle") version "3.20.2"
}

group = "com.idorasi"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
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
	implementation("org.springframework.boot:spring-boot-starter-jooq")
	implementation("org.jooq:jooq-codegen:3.20.2")
	implementation("org.jooq:jooq-meta:3.20.2")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.5")

	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("io.jsonwebtoken:jjwt-api:0.12.6")
	implementation("io.jsonwebtoken:jjwt-impl:0.12.6")
	implementation("io.jsonwebtoken:jjwt-jackson:0.12.6")
	implementation("org.liquibase:liquibase-core")

	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-docker-compose")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	jooqCodegen("org.postgresql:postgresql")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

jooq {
	configuration {
		jdbc {
			url = "jdbc:postgresql://localhost:5432/eligibility"
			driver = "org.postgresql.Driver"
			user = "user"
			password = "secretPass"
		}

		generator {
			name = "org.jooq.codegen.DefaultGenerator"

			database {
				name = "org.jooq.meta.postgres.PostgresDatabase"
				inputSchema = "public"
				excludes = "databasechangelog|databasechangeloglock"
			}
			generate {
				isKeys = false
				isDefaultCatalog = false
			}
			target {
				packageName = "com.idorasi.eligibility.entity"
				directory = "src/main/java"  // default (can be omitted)
			}
		}
	}
}