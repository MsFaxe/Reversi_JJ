package Reversi;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class StartGameScene {

    public String pawnColor = "black";

    Label username = new Label("Username");
    TextField nameInput = new TextField();
    ComboBox<String> comboBox = new ComboBox<>();
    Label showChosenPawn = new Label();

    public String getPawnColor() {
        return pawnColor;
    }

    private void comboBoxAction(String chosenColor){
        ShowPawn showPawn = new ShowPawn();

        if (chosenColor.equals("white")){
            showChosenPawn.setGraphic(showPawn.setPawnImage(chosenColor));
            this.pawnColor = "white";
        } else{
            showChosenPawn.setGraphic(showPawn.setPawnImage("black"));
            this.pawnColor = "black";
        }
    }

    FieldsMap mapOfFields = new FieldsMap();

    private Button buttonStartEasyGame(){
        BoardGameScene board = new BoardGameScene();
        GameLogic game = new GameLogic();
        game.setFieldsMap(mapOfFields);

        Button button = new Button("Start easy mode");
        button.setOnAction(e -> {
            Main.stage.setScene(board.loadGame(nameInput.getText(), mapOfFields));
            game.setCOLOR_PAWNS(getPawnColor());
            game.startGame();
        });
        return button;
    }

    private Button buttonLoadGame() {
        BoardGameScene board = new BoardGameScene();
        GameLogic game = new GameLogic();
        game.setFieldsMap(mapOfFields);

        Button button = new Button("Load Game");

        button.setOnAction(e -> {
            Main.stage.setScene(board.loadGame(nameInput.getText(), mapOfFields));
            game.setCOLOR_PAWNS(getPawnColor());
            try {
                game.loadGame();
            }catch (Exception exc){
                //
            }
        });
        return button;
    }

    private Button buttonRecordsBoard() {
        RecordsBoard recordsBoard = new RecordsBoard();

        Button button = new Button("Record board");
        button.setOnAction(e -> {
            try {
                recordsBoard.loadRecords();
            }catch(Exception exc) {
                //
            }
        });
        return button;
    }

    public Scene loadStart (){

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(8);
        grid.setHgap(10);

        GridPane.setConstraints(username, 0,0);
        GridPane.setConstraints(nameInput, 1, 0);

        comboBox.getItems().addAll("black", "white");
        comboBox.setValue("Choose pawns");
        comboBox.setOnAction(e -> comboBoxAction(comboBox.getValue())); // show chosen pawn image
        GridPane.setConstraints(comboBox, 0, 1);

        Button buttonStartEasy = buttonStartEasyGame();

        GridPane.setConstraints(buttonStartEasy, 0, 3);

        Button buttonLoadGame = buttonLoadGame();
        GridPane.setConstraints(buttonLoadGame, 0, 4);

        Button buttonRecordsBoard = buttonRecordsBoard();
        GridPane.setConstraints(buttonRecordsBoard, 1, 4);

        showChosenPawn.setMinSize(100, 100);
        HBox boxToShowChosenPawn = new HBox();
        boxToShowChosenPawn.getChildren().add(showChosenPawn);
        GridPane.setConstraints(boxToShowChosenPawn, 0, 2);
        grid.getChildren().addAll(username, nameInput, comboBox,
                buttonStartEasy, boxToShowChosenPawn, buttonLoadGame, buttonRecordsBoard);

        return new Scene(grid, 300, 250);
    }
}
