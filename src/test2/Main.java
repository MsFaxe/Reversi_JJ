package test2;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    static Stage stage;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        stage.setTitle("Reversi");

        StartGameScene start = new StartGameScene();

        stage.setScene(start.loadStart());
        stage.show();
    }
}
