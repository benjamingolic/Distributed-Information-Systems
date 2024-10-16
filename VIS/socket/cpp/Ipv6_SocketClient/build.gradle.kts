import org.apache.tools.ant.taskdefs.condition.Os

plugins {
   id("cpp-application")
}

val port: String = "4949"
val ipAddress: String = "::1"

// task to run cpp program
tasks.register("run", Exec::class) {
    dependsOn("build")         // make sure project has been built
    group = "application"                // set task group
    standardInput = System.`in`    // enable commandline input
    val exeDir:  String = "./build/exe/main/debug/"
    var exeFile: String? = null
    when {
        Os.isFamily(Os.FAMILY_WINDOWS) -> {
            exeFile = "Ipv6_SocketClient.exe"
			commandLine("cmd", "/k", exeDir+exeFile, port, ipAddress) // start command in cmd shell
        }
        Os.isFamily(Os.FAMILY_MAC) -> {
            exeFile = "Ipv6_SocketClient"
			commandLine(exeDir+exeFile, port, ipAddress) // start command in cmd shell
        }
        Os.isFamily(Os.FAMILY_UNIX) -> {
            exeFile = "Ipv6_SocketClient"
			commandLine("bash", "-c", exeDir+exeFile, port, ipAddress) // start command in cmd shell
        }
        else -> { throw GradleException(":cpp:Ipv6_SocketClient run-target -> unknown OS family encountered")}
    }
}