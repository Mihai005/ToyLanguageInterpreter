package com.example.toylanguageinterpretergui;

import controller.IController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MainView extends Application {
    static IController controller;

    public static void setController(IController controller) {
        MainView.controller = controller;
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader mainWindowLoader = new FXMLLoader();

        mainWindowLoader.setLocation(getClass().getResource("MainWindow.fxml"));
        mainWindowLoader.setControllerFactory(c -> new MainWindowController(controller));

        Parent mainWindowRoot = mainWindowLoader.load();
        MainWindowController mainWindowController = mainWindowLoader.getController();

        stage.setTitle("Interpreter MAP 2024 - 2025");
        stage.setScene(new Scene(mainWindowRoot));
        stage.show();

        Stage secondaryStage = new Stage();
        FXMLLoader setProgramLoader = new FXMLLoader();
        stage.getIcons().add(new Image("C:/Users/Mihai/IdeaProjects/ToyLanguageInterpreterGUI/src/icon.png"));
        secondaryStage.getIcons().add(new Image("C:/Users/Mihai/IdeaProjects/ToyLanguageInterpreterGUI/src/icon.png"));

        setProgramLoader.setControllerFactory(c -> new SelectProgramWindowController(controller, mainWindowController));
        setProgramLoader.setLocation(getClass().getResource("SelectProgramWindow.fxml"));

        Parent setProgramRoot = setProgramLoader.load();
        SelectProgramWindowController setProgramController = setProgramLoader.getController();

        secondaryStage.setTitle("Select program");
        secondaryStage.setScene(new Scene(setProgramRoot));
        secondaryStage.show();
    }

    public void run(String[] args) {
        launch(args);
    }

    public static void main(String[] args) {
        launch();
    }
}
