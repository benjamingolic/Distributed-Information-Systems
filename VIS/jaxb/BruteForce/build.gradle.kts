plugins {
   application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.guava:guava:31.0.1-jre")
}


application {
    mainClass.set("at.fhooe.sail.vis.bruteforce.BruteForce")
}