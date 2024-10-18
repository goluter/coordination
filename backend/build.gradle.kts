plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
    kotlin("plugin.jpa") version "1.9.25"
    kotlin("kapt") version "1.9.20"  // kapt 플러그인 추가
}

group = "com.github.titainper"
version = "0.0.1-SNAPSHOT"

val queryDslVersion = "5.0.0"
val coroutinesVersion = "1.8.0"  // 코루틴 버전 정의

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}


dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // NOTE: QueryDsl
    implementation("com.querydsl:querydsl-jpa:$queryDslVersion:jakarta")
    kapt("com.querydsl:querydsl-apt:$queryDslVersion:jakarta")

    // NOTE: 코루틴
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive:$coroutinesVersion")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")

    // NOTE:  Lombok
    kapt("org.projectlombok:lombok:1.18.20")
    compileOnly("org.projectlombok:lombok:1.18.20")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    runtimeOnly("com.h2database:h2")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}


tasks.withType<Test> {
    useJUnitPlatform()
}

val querydslDir = "build/generated/source/kapt/main"

sourceSets {
    main {
        java {
            srcDirs(querydslDir)
        }
    }
}

tasks.named("clean") {
    doLast {
        file(querydslDir).deleteRecursively()
    }
}

kapt {
    correctErrorTypes = true
}