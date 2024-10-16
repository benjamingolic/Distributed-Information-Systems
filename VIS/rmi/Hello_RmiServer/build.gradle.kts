plugins {
   application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":rmi:Hello_RmiInterface"))
    implementation("com.google.guava:guava:31.0.1-jre")
}

application {
    mainClass.set("at.fhooe.sail.vis.hellormiserver.Hello_RmiServer")
}