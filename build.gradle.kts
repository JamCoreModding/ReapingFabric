plugins {
    id("fabric-loom") version "0.11-SNAPSHOT"
    id("io.github.juuxel.loom-quiltflower") version "1.6.0"
    id("org.quiltmc.quilt-mappings-on-loom") version "4.0.0"
    id("org.cadixdev.licenser") version "0.6.1"
}

val modVersion: String by project

group = "io.github.jamalam360"
version = modVersion

repositories {
    val mavenUrls = listOf(
        "https://maven.terraformersmc.com/releases",
        "https://maven.shedaniel.me/",
        "https://www.cursemaven.com"
    )

    for (url in mavenUrls) {
        maven(url = url)
    }
}

dependencies {
    val minecraftVersion: String by project
    val mappingsVersion: String by project
    val loaderVersion: String by project
    val fabricApiVersion: String by project
    val clothConfigVersion: String by project
    val modMenuVersion: String by project

    minecraft("com.mojang:minecraft:$minecraftVersion")
    mappings(loom.layered {
        addLayer(quiltMappings.mappings("org.quiltmc:quilt-mappings:$minecraftVersion+build.$mappingsVersion:v2"))
    })
    modImplementation("net.fabricmc:fabric-loader:$loaderVersion")
    modImplementation("net.fabricmc.fabric-api:fabric-api:$fabricApiVersion")

    configurations.all {
        resolutionStrategy.force("net.fabricmc.fabric-api:fabric-api:$fabricApiVersion")
    }

    // Required:
    modApi("me.shedaniel.cloth:cloth-config-fabric:$clothConfigVersion")

    // Optional:
    modApi("com.terraformersmc:modmenu:$modMenuVersion")

    // Compatibility:
    // modApi("curse.maven:harvestscythes-412225:3350526")
}

tasks {
    processResources {
        inputs.property("version", project.version)
        filesMatching("fabric.mod.json") {
            expand(
                mutableMapOf(
                    "version" to project.version
                )
            )
        }
    }

    build {
        dependsOn("updateLicenses")
    }

    jar {
        archiveBaseName.set("ReapingMod")
    }

    remapJar {
        archiveBaseName.set("ReapingMod")
    }

    withType<JavaCompile> {
        options.release.set(17)
    }
}