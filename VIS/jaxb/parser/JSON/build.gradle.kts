plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.guava:guava:31.0.1-jre")
    implementation("org.json:json:20231013")
}


application {
    mainClass.set("at.fhooe.sail.vis.parser.json.JSONParser")
}