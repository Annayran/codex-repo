plugins {
    id("java")
}

group = "br.com.zaya"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-Xlint:unchecked")
}

repositories {
    mavenCentral()
}

// Base das suas dependencias locais no Windows
val baseLibPath = "C:/zy-tools/modulos-sankhya/Dependencias"

dependencies {
    // ===== LIBS LOCAIS (compileOnly) =====
    compileOnly(fileTree(mapOf("dir" to "$baseLibPath/Jar", "include" to listOf("*.jar"))))
    // Ajuste este caminho caso a estrutura de pastas seja diferente.
    compileOnly(fileTree(mapOf("dir" to "$baseLibPath/4_35/4_35", "include" to listOf("*.jar"))))

    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.0")

    // ===== TESTES =====
    testImplementation(fileTree(mapOf("dir" to "$baseLibPath/Jar", "include" to listOf("*.jar"))))
    testImplementation(fileTree(mapOf("dir" to "$baseLibPath/4_35/4_35", "include" to listOf("*.jar"))))

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    archiveFileName.set("Koncili.jar")
}