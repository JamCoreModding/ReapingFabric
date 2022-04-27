plugins {
    id("fabric-loom") version "0.11-SNAPSHOT"
    id("io.github.juuxel.loom-quiltflower") version "1.6.0"
    id("org.quiltmc.quilt-mappings-on-loom") version "4.0.0"
    id("org.cadixdev.licenser") version "0.6.1"
}

apply(from = "https://raw.githubusercontent.com/JamCoreModding/Gronk/main/publishing.gradle.kts")
apply(from = "https://raw.githubusercontent.com/JamCoreModding/Gronk/main/misc.gradle.kts")

val mod_version: String by project

group = "io.github.jamalam360"
version = mod_version

repositories {
    val mavenUrls = listOf(
        "https://maven.terraformersmc.com/releases",
        "https://maven.shedaniel.me/",
        "https://www.cursemaven.com",
        "https://jitpack.io"
    )

    for (url in mavenUrls) {
        maven(url = url)
    }
}

dependencies {
    minecraft(libs.minecraft)
    mappings(loom.layered {
        addLayer(quiltMappings.mappings("org.quiltmc:quilt-mappings:1.18.2+build.22:v2"))
    })

    modImplementation(libs.loader)
    modImplementation(libs.fabric.api)

    configurations.all {
        resolutionStrategy.force("net.fabricmc.fabric-api:fabric-api:0.51.0+1.18.2")
    }

    // Required:
    modApi(libs.cloth.config)

    // Optional:
    modApi(libs.mod.menu)

    // Compatibility:
    modLocalRuntime(libs.harvest.scythes)

    // Compatibility Dependencies:
    modLocalRuntime(libs.fabric.asm) // Harvest Scythes dependency
}
