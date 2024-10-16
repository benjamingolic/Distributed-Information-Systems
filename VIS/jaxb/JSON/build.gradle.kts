plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    // Moxy-JAXB (JSON) DON’T mix with standard XML dependency
    // https://mvnrepository.com/artifact/org.glassfish.jersey.media/jersey-media-moxy
    implementation("org.glassfish.jersey.media:jersey-media-moxy:3.1.5")
}


application {
    mainClass.set("at.fhooe.sail.vis.json.Json")
}