plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    // JAXB (XML)
    // https://mvnrepository.com/artifact/org.glassfish.jaxb/jaxb-runtime

    implementation("com.google.guava:guava:31.0.1-jre")
    implementation("org.glassfish.jersey.media:jersey-media-moxy:3.1.5")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.0")
    implementation(project(":rest:Environment_RestServer"))
    implementation(project(":general:EnvironmentI"))
}

application {
    mainClass.set("at.fhooe.sail.vis.rest.environmentrestclient.Environment_RestClient")
}