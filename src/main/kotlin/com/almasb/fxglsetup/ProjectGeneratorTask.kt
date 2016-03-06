package com.almasb.fxglsetup

import javafx.concurrent.Task
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.nio.file.StandardOpenOption
import java.util.*

/**
 *
 *
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
class ProjectGeneratorTask(private val settings: Settings) : Task<Void>() {

    private val vars = HashMap<String, String>()

    init {
        vars["\$PACKAGE"] = settings.packageName
        vars["\$CLASS"] = settings.className
        //vars["\$"]
    }

    override fun call(): Void? {

        createDirectoryTree()
        createMainClass()
        createMavenBuild()

        return null
    }

    override fun succeeded() {
        updateMessage("Project Generation Complete")
    }

    override fun failed() {
        updateMessage("Project Generation Failed: ${getException().message}")
    }

    private fun createDirectoryTree() {
        val dirNames = ArrayList<String>()
        with(dirNames) {
            add("src/main/java")
            add("src/main/resources")
            add("src/main/resources/assets")
            add("src/main/resources/assets/music")
            add("src/main/resources/assets/properties")
            add("src/main/resources/assets/scripts")
            add("src/main/resources/assets/sounds")
            add("src/main/resources/assets/text")
            add("src/main/resources/assets/textures")
            add("src/main/resources/assets/ui")
            add("src/main/resources/assets/ui/css")
            add("src/main/resources/assets/ui/cursors")
            add("src/main/resources/assets/ui/fonts")
            add("src/main/resources/assets/ui/icons")

            forEach { Files.createDirectories(settings.outputDir.resolve(it)) }
        }
    }

    private fun createMavenBuild() {
        createAndReparse("pom.xml", settings.outputDir.resolve("pom.xml"))
    }

    private fun createGradleBuild() {
        // build.gradle
    }

    private fun createMainClass() {
        val finalDir = settings.outputDir.resolve("src/main/java/" + settings.packageName.replace(".", "/"))

        Files.createDirectories(finalDir)

        val mainClass = finalDir.resolve(settings.className + ".java")
        createAndReparse("MainApp.txt", mainClass)
    }

    private fun createAndReparse(resourceName: String, outFile: Path) {
        Files.copy(javaClass.getResourceAsStream(resourceName),
                outFile,
                StandardCopyOption.REPLACE_EXISTING)

        val newLines = Files.readAllLines(outFile).map { replaceVars(it) }

        Files.write(outFile, newLines, StandardOpenOption.CREATE)
    }

    private fun replaceVars(line: String): String {
        var newLine = line
        for ((arg, param) in vars) {
            newLine = newLine.replace(arg, param)
        }
        return newLine
    }
}