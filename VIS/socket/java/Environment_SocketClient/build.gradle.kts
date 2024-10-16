plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // This dependency is used by the application.
    implementation("com.google.guava:guava:31.0.1-jre")
    implementation(project(":general:EnvironmentI"))
}

application {
    // Define the main class for the application.

    /**
     * Normal Environment_SocketClient main
     */
    //mainClass.set("at.fhooe.sail.vis.environmentclient.Environment_SocketClient")

    /**
     * 1.3d) main
     */
    mainClass.set("at.fhooe.sail.vis.environmentclient.environmentTestingMain")
}

tasks.named<JavaExec>("run") {
    // Pass command line arguments to the application.
    standardInput = System.`in`
}
