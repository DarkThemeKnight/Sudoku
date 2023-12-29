package com.sudoku;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

/**
 * Hello world!
 *
 */
public class App extends Application {
    public static void main( String[] args )
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        loadMainMenu(primaryStage);
    }
    private void loadMainMenu(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/WelcomePage.fxml"));
        Scene scene = new Scene(loader.load());
        URL css = getClass().getResource("/cssFiles/style.css");
        assert css != null;
        String cssExt  = css.toExternalForm();
        scene.getStylesheets().add(Objects.requireNonNull(cssExt));
        primaryStage.setTitle("Welcome to Tic Tac Toe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
