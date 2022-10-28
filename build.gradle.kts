plugins {
    kotlin("js") version "1.7.10"
}

group = "com.jtw"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

kotlin {
    js(IR) {
        binaries.executable()
        nodejs {
        }
    }
}

val outputTask = tasks.register("outputJS") {
    description = "TODO"
    group = "build"

    dependsOn(tasks.findByPath("nodeRun"))

    this.doFirst {
        val builtFile = File("build/js/packages/ElevatorSagaKt/kotlin/ElevatorSagaKt.js")
        val outputFile = File("build/output.js")
        builtFile.copyTo(outputFile, overwrite = true)
        outputFile.appendText(
            """
                
                
            new ElevatorSagaKt.ElevatorCallbacks()
            """.trimIndent()
        )

        println("=====")
        println("Now paste the contents of [build/output.js] into https://play.elevatorsaga.com/")
    }
}
