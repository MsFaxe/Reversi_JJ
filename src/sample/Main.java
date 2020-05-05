package sample;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class Main extends Application /*implements EventHandler<ActionEvent> */{

    static Stage stage;
    static Scene scene1;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        stage.setTitle("Reversi");

        StartGameScene startGameScene = new StartGameScene();
        startGameScene.loadStartGameScene();

        GameRulesScene gameRulesScene = new GameRulesScene();
        gameRulesScene.loadGameRulesScene();

        Board board = new Board();
        board.loadBoard();

        //close program - closed by "x"
        stage.setOnCloseRequest(e -> {
            e.consume(); //bez tego się zamknie nawet jak jednak się rozmyślę
            closeProgram();
        });
        GameRulesScene.buttonClose.setOnAction(e -> closeProgram());


        stage.setScene(startGameScene.scene);
        stage.show();
    }


    // 1st way - button
    /*@Override
    public void handle(ActionEvent event) {
        if (event.getSource()==buttonStart){
            System.out.println("Game start");
        }
    }*/


    private void closeProgram(){
        Boolean answer = ConfirmBox.choice("close the game", "Sure you want to exit");
        if (answer) {
            stage.close();
        }
    }
}
