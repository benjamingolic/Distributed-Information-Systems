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
    implementation("org.glassfish.jaxb:jaxb-runtime:4.0.4")
    implementation("javax.xml.bind:jaxb-api:2.3.1")
    implementation("com.sun.xml.bind:jaxb-core:2.3.0.1")
    implementation("com.sun.xml.bind:jaxb-impl:2.3.0.1")
}

application {
    mainClass.set("at.fhooe.sail.vis.xml.Xml")
}