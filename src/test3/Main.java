package test3;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    static Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        stage = primaryStage;
        stage.setTitle("Reversi");

        BoarGameScene board = new BoarGameScene();
        stage.setScene(board.loadGame());

        GameLogic game = new GameLogic();

        game.startGame();
        stage.show();
    }
}
