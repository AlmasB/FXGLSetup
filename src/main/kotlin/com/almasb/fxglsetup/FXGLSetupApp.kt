package com.almasb.fxglsetup

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage
import java.util.*

/**
 *
 *
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
class FXGLSetupApp : Application() {

    override fun start(stage: Stage) {
        stage.scene = Scene(FXMLLoader.load(javaClass.getResource("ui.fxml")))
        stage.title = "FXGLSetup v.${version()}"
        stage.show()
    }
}

private fun version() = ResourceBundle.getBundle("com.almasb.fxglsetup.version").getString("app.version")

fun main(args: Array<String>) {
    Application.launch(FXGLSetupApp::class.java, *args)
}