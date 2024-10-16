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
    mainClass.set("at.fhooe.sail.vis.hellormiinterface.Hello_RmiInterface")
}

val jarName: String = "Hello_RmiInterface"
tasks.jar {
    archiveBaseName.set(jarName)
    doLast {
        copy {
            println("*** copying jar to libs folder ... ")
            val bDir: String = layout.buildDirectory.get().toString()
            val fromS: String = "${bDir}\\libs\\$jarName.jar"
            val intoS: String = "${rootProject.projectDir.absolutePath}\\libs"
            from(fromS)
            into(intoS)
        }
    }
}
tasks.clean {
    val fN: String = "${rootProject.projectDir.absolutePath}\\libs\\$jarName.jar"
    delete(files(fN))
}