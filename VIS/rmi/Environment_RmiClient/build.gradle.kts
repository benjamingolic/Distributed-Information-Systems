plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    //implementation(project(":rmi:Hello_RmiInterface"))
    //implementation(files("${rootProject.projectDir.absolutePath}/libs/Hello_RmiInterface_amel.jar"))
    //implementation(files("${rootProject.projectDir.absolutePath}/libs/Hello_RmiInterface_mo.jar"))
    //implementation(files("${rootProject.projectDir.absolutePath}/libs/Hello_RmiInterface_teo.jar"))
    //implementation(files("${rootProject.projectDir.absolutePath}/libs/Hello_RmiInterface_sebi.jar"))
    implementation(project(":general:EnvironmentI"))
    implementation("com.google.guava:guava:31.0.1-jre")
}

application {
    mainClass.set("at.fhooe.sail.vis.environmentrmiclient.Environment_RmiClient")
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}