plugins {
    java
    `maven-publish`
}

group = "br.com.devsrsouza"
version = "1.0.0-SNAPSHOT"

repositories {
    jcenter()
    maven {
        name = "spigot"
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }
}

dependencies {
    compileOnly("org.bukkit:bukkit:1.8.8-R0.1-SNAPSHOT")

    testCompile("junit", "junit", "4.12")
    testRuntime("org.bukkit:bukkit:1.8.8-R0.1-SNAPSHOT")
}

dependencies {
    testCompile("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

val sourcesJar by tasks.registering(Jar::class) {
    classifier = "sources"
    from(sourceSets.main.get().allSource)
}

publishing {
    publications {
        register("mavenJava", MavenPublication::class) {
            from(components["java"])
            artifact(sourcesJar.get())
            artifactId = project.name.toLowerCase()
        }
    }
}