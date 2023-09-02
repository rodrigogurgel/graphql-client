plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.0"
    `java-library`
    `maven-publish`
}

group = "br.com.rodrigogurgel"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val okHttpClientVersion = properties["okHttpClientVersion"]
val jacksonKotlinVersion = properties["jacksonKotlinVersion"]

dependencies {
    implementation("com.squareup.okhttp3:okhttp:$okHttpClientVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonKotlinVersion")

    testImplementation(kotlin("test"))
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = group.toString()
            artifactId = "graphql-client"
            version = version

            from(components["java"])
        }
    }
}
