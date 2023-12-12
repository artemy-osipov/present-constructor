import org.springframework.boot.gradle.plugin.SpringBootPlugin
import org.springframework.boot.gradle.tasks.aot.ProcessTestAot
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
    id("org.jetbrains.kotlin.jvm") version ("1.9.21")
    id("org.jetbrains.kotlin.kapt") version ("1.9.21")
    id("org.jetbrains.kotlin.plugin.spring") version ("1.9.21")
    id("com.bnorm.power.kotlin-power-assert") version ("0.13.0")
    id("org.springframework.boot") version ("3.2.0")
    id("org.graalvm.buildtools.native") version ("0.9.28")
    id("com.github.ben-manes.versions") version ("0.50.0")
    id("com.adarshr.test-logger") version ("4.0.0")
}

group = "io.github.artemy.osipov.shop"
version = "1.5.0-SNAPSHOT"

dependencies {
    kapt(enforcedPlatform(SpringBootPlugin.BOM_COORDINATES))
    implementation(enforcedPlatform(SpringBootPlugin.BOM_COORDINATES))

    kapt("org.springframework.boot:spring-boot-configuration-processor")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    val sentry = "7.0.0"
    implementation("io.sentry:sentry-spring-boot-starter-jakarta:$sentry")
    implementation("io.sentry:sentry-logback:$sentry")

    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.postgresql:r2dbc-postgresql")

    implementation("com.github.f4b6a3:ulid-creator:5.2.2")
    implementation("fr.opensagres.xdocreport:fr.opensagres.xdocreport.document.docx:2.0.4")
    implementation("fr.opensagres.xdocreport:fr.opensagres.xdocreport.template.freemarker:2.0.4")

    implementation("io.github.oshai:kotlin-logging-jvm:5.1.1")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("io.projectreactor:reactor-tools")

    val mapstructVersion = "1.5.5.Final"
    implementation("org.mapstruct:mapstruct:$mapstructVersion")
    kapt("org.mapstruct:mapstruct-processor:$mapstructVersion")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.docx4j:docx4j-JAXB-ReferenceImpl:11.4.9")
    testImplementation("org.testcontainers:cockroachdb")
    testImplementation("org.postgresql:postgresql")
    testImplementation("io.projectreactor.tools:blockhound:1.0.8.RELEASE")
    testImplementation("org.junit.platform:junit-platform-launcher")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-debug")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.1.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

kotlin.jvmToolchain(21)

graalvmNative {
    metadataRepository.enabled = true
    binaries.all {
        // https://github.com/oracle/graal/issues/6957
        buildArgs("--strict-image-heap")
    }
}

tasks.named<BootBuildImage>("bootBuildImage") {
    environment.put(
        "BP_NATIVE_IMAGE_BUILD_ARGUMENTS", """
            --strict-image-heap
            --initialize-at-build-time=org.springframework.boot.loader.nio.file.NestedFileSystemProvider
        """
    )
}

// https://github.com/reactor/BlockHound/issues/33
tasks.withType<Test> {
    jvmArgs("-XX:+AllowRedefinitionToAddDeleteMethods")
}
tasks.named<ProcessTestAot>("processTestAot") {
    jvmArgs("-XX:+AllowRedefinitionToAddDeleteMethods")
}
