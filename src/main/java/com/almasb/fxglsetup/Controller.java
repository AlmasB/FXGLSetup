package com.almasb.fxglsetup;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public class Controller {

    private Stage stage;

    @FXML
    private Label labelOutputDir;
    @FXML
    private TextField fieldPackage;
    @FXML
    private TextField fieldClassName;

    @FXML
    private CheckBox checkMaven;
    @FXML
    private CheckBox checkGradle;

    @FXML
    private Button btnFinish;
    @FXML
    private Button btnExit;

    @FXML
    private TextArea log;

    private Path outputDir;
    private Path classFile;

//    public Controller(Stage stage) {
//        this.stage = stage;
//    }

    public void initialize() {
        btnFinish.disableProperty().bind(
                labelOutputDir.textProperty().isEmpty()
                        .or(fieldPackage.textProperty().isEmpty())
                        .or(fieldClassName.textProperty().isEmpty()));
    }

    @FXML
    private void browseOutputDirectory() {
        outputDir = Paths.get("C:/Users/Almas/Desktop/fxgltest");
        labelOutputDir.setText(outputDir.toAbsolutePath().toString());

//        DirectoryChooser chooser = new DirectoryChooser();
//        chooser.setTitle("Select Output Directory");
//        File dir = chooser.showDialog(stage);
//        if (dir != null) {
//            outputDir = dir.toPath();
//            labelOutputDir.setText(dir.getAbsolutePath());
//        }
    }

//    @FXML
//    private void browseAppFile() {
//        FileChooser chooser = new FileChooser();
//        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JavaFX App File", "*.java"));
//        chooser.setTitle("Select JavaFX Application File");
//        File file = chooser.showOpenDialog(stage);
//        if (file != null) {
//            classFile = file.toPath();
//            fieldClassName.setText(file.getName());
//
//            try (BufferedReader reader = Files.newBufferedReader(classFile)) {
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    if (line.contains("package")) {
//                        String pkg = line.replace("package", "").replace(";", "").trim();
//                        fieldPackage.setText(pkg);
//                        break;
//                    }
//                }
//            } catch (Exception e) {
//                handleException(e);
//            }
//        }
//    }

    @FXML
    private void finish() {


//        btnFinish.disableProperty().unbind();
//        btnFinish.setDisable(true);
//        btnExit.setDisable(true);
//
        ProjectGeneratorTask task = new ProjectGeneratorTask(
                new Settings(outputDir, fieldPackage.getText(), fieldClassName.getText()));

        task.messageProperty().addListener((observable, oldValue, newValue) -> {
            log.appendText(newValue + "\n");
        });

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    private void exit() {
        System.exit(0);
    }

    private void handleException(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }
}
