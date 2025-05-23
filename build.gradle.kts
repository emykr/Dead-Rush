import java.util.Date
import java.text.SimpleDateFormat

plugins {
    idea
    `maven-publish`
    id("net.minecraftforge.gradle") version "[6.0,6.2)"
    id("org.parchmentmc.librarian.forgegradle") version "1.+"
    id("org.jetbrains.kotlin.jvm") version "2.0.0"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.0"
}

// gradle.properties 변수 선언
val minecraft_version: String by project
val minecraft_version_range: String by project
val forge_version: String by project
val forge_version_range: String by project
val kff_version: String by project
val mapping_channel: String by project
val mapping_version: String by project
val mod_id: String by project
val mod_name: String by project
val mod_version: String by project
val mod_authors: String by project
val mod_description: String by project
val mod_license: String by project
val mod_group_id: String by project

version = project.property("mod_version") as String
group = project.property("mod_group_id") as String

base {
    archivesName.set(mod_id)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

println("Java: ${System.getProperty("java.version")}, JVM: ${System.getProperty("java.vm.version")} (${System.getProperty("java.vendor")}), Arch: ${System.getProperty("os.arch")}")

minecraft {
    mappings(mapping_channel, mapping_version)
    // accessTransformer = "src/main/resources/META-INF/accesstransformer.cfg"

    runs {
        copyIdeResources = true

        create("client") {
            workingDirectory = "run"
            property("forge.logging.markers", "REGISTRIES")
            property("forge.logging.console.level", "debug")
            property("forge.enabledGameTestNamespaces", mod_id)
            mods {
                create(mod_id) {
                    source(sourceSets.main.get())
                }
            }
        }
        create("server") {
            workingDirectory = "run/server"
            property("forge.logging.markers", "REGISTRIES")
            property("forge.logging.console.level", "debug")
            property("forge.enabledGameTestNamespaces", mod_id)
            mods {
                create(mod_id) {
                    source(sourceSets.main.get())
                }
            }
        }
        create("gameTestServer") {
            workingDirectory = "run/server"
            property("forge.logging.markers", "REGISTRIES")
            property("forge.logging.console.level", "debug")
            property("forge.enabledGameTestNamespaces", mod_id)
            mods {
                create(mod_id) {
                    source(sourceSets.main.get())
                }
            }
        }
        create("data") {
            workingDirectory = "run"
            property("forge.logging.markers", "REGISTRIES")
            property("forge.logging.console.level", "debug")
            args("--mod", mod_id, "--all", "--output", "src/generated/resources/", "--existing", "src/main/resources")
            mods {
                create(mod_id) {
                    source(sourceSets.main.get())
                }
            }
        }
    }
}

sourceSets {
    main {
        resources {
            srcDir("src/generated/resources/")
        }
    }
}

repositories {
    maven {
        name = "Kotlin for Forge"
        url = uri("https://thedarkcolour.github.io/KotlinForForge/")
        content {
            includeGroup("thedarkcolour")
        }
    }
}

dependencies {
    minecraft("net.minecraftforge:forge:$minecraft_version-$forge_version")
    implementation("thedarkcolour:kotlinforforge:$kff_version")
    implementation(project(":common"))
    implementation(project(":drLib"))
}

val resourceTargets = listOf("META-INF/mods.toml", "pack.mcmeta")
val replaceProperties = mapOf(
    "minecraft_version" to minecraft_version,
    "minecraft_version_range" to minecraft_version_range,
    "forge_version" to forge_version,
    "forge_version_range" to forge_version_range,
    "mod_id" to mod_id,
    "mod_name" to mod_name,
    "mod_license" to mod_license,
    "mod_version" to mod_version,
    "mod_authors" to mod_authors,
    "mod_description" to mod_description,
    "project" to project
)

tasks.processResources {
    inputs.properties(replaceProperties)
    resourceTargets.forEach { target ->
        filesMatching(target) {
            expand(replaceProperties)
        }
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

tasks.jar {
    manifest {
        attributes(
            mapOf(
                "Specification-Title" to mod_id,
                "Specification-Vendor" to mod_authors,
                "Specification-Version" to "1",
                "Implementation-Title" to project.name,
                "Implementation-Version" to archiveVersion.get(),
                "Implementation-Vendor" to mod_authors,
                "Implementation-Timestamp" to SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(Date())
            )
        )
    }
    finalizedBy("reobfJar")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifact(tasks.jar)
        }
    }
    repositories {
        maven {
            url = uri("file://${project.projectDir}/mcmodsrepo")
        }
    }
}
