package sample;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class StartGameScene {
    Stage stage;
    Scene scene;

    public void loadStartGameScene(){

        //username screen
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(8);
        grid.setHgap(10);
        scene = new Scene(grid, 500,300);

        //username label
        Label usernameLabel = new Label("username");
        GridPane.setConstraints(usernameLabel, 0,0);

        //name input
        TextField nameInput = new TextField("username");
        nameInput.setPromptText("username");
        GridPane.setConstraints(nameInput, 1, 0);

        //choose color
        Label chooseColorText = new Label("Choose color of powns");
        GridPane.setConstraints(chooseColorText, 0, 1);
        CheckBox choiceBlack = new CheckBox("Black pawns");
        GridPane.setConstraints(choiceBlack, 0, 2);
        CheckBox choiceWhite = new CheckBox("White pawns");
        GridPane.setConstraints(choiceWhite, 0, 3);

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("White pawns", "Black powns");
        //default value
        comboBox.setValue("Choose powns");
        comboBox.setOnAction(e -> System.out.println(comboBox.getValue())); // show chosen pown image


        GridPane.setConstraints(comboBox, 0, 4);

        Button buttonGoIn = new Button("start game");
        buttonGoIn.setOnAction(e-> {
            stage.setScene(GameRulesScene.scene1);
            System.out.println(nameInput.getText());
        });

        GridPane.setConstraints(buttonGoIn, 0,5);
        grid.getChildren().addAll(usernameLabel, nameInput, buttonGoIn, chooseColorText, choiceBlack, choiceWhite, comboBox);


    }
}
