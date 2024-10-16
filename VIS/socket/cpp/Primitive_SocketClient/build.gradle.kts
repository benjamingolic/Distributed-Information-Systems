import org.apache.tools.ant.taskdefs.condition.Os

plugins {
   id("cpp-application")
}

val port: String = "4949"
val ipAddress: String = "127.0.0.1"

// task to run cpp program
tasks.register("run", Exec::class) {
    dependsOn("build")         // make sure project has been built
    group = "application"                // set task group
    standardInput = System.`in`    // enable commandline input
    val exeDir:  String = "./build/exe/main/debug/"
    var exeFile: String? = null
    when {
        Os.isFamily(Os.FAMILY_WINDOWS) -> {
            exeFile = "Primitive_SocketClient.exe"
			commandLine("cmd", "/k", exeDir+exeFile, port, ipAddress) // start command in cmd shell
        }
        Os.isFamily(Os.FAMILY_MAC) -> {
            exeFile = "Primitive_SocketClient"
			commandLine(exeDir+exeFile, port, ipAddress) // start command in cmd shell
        }
        Os.isFamily(Os.FAMILY_UNIX) -> {
            exeFile = "Primitive_SocketClient"
			commandLine("bash", "-c", exeDir+exeFile, port, ipAddress) // start command in cmd shell
        }
        else -> { throw GradleException(":cpp:Primitive_SocketClient run-target -> unknown OS family encountered")}
    }
}

