package test2;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class StartGameScene {
    public static String pawnColor;

    public static String getPawnColor() {
        return pawnColor;
    }

    private String comboBoxAction(String chosenColor){
        if (chosenColor == "White pawns" ){
            return this.pawnColor = "white";
        } else{
            return this.pawnColor = "black";
        }
    }

    private Button buttonStartGame(){
        BoardGameScene boardGameScene = new BoardGameScene();

        Button button = new Button("Start");
        button.setOnAction(e -> {
            Main.stage.setScene(boardGameScene.loadGame());
        });
        return button;
    }


    public Scene loadStart (){

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label label = new Label("Username");
        label.setOnMouseClicked(e -> System.exit(0));
        GridPane.setConstraints(label, 0,0);

        TextField nameInput = new TextField();
        nameInput.setPromptText("username");
        GridPane.setConstraints(nameInput, 1, 0);

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("White pawns", "Black pawns");
        comboBox.setValue("Choose pawns");
        comboBox.setOnAction(e -> comboBoxAction(comboBox.getValue())); // show chosen pown image
        GridPane.setConstraints(comboBox, 0, 1);

        Button buttonStart = buttonStartGame();
        GridPane.setConstraints(buttonStart, 0, 2);


        grid.getChildren().addAll(label, nameInput, comboBox, buttonStart);

        Scene scene = new Scene(grid, 300, 150);

        return scene;
    }
}
