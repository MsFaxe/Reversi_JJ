package test3;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;


public class BoarGameScene {
    private final Image imageBack = new Image("file:resources/background.jpg");
    private final Label[][] tabLabel = new Label[8][8];

    private Button buttonReset(){
        Button button = new Button("Reset");
        GameLogic gameLogic = new GameLogic();
        button.setOnAction(e -> {
            Main.stage.setScene(loadGame());
            gameLogic.startGame();
        });
        return button;
    }

    private Label labelEvent(){
        Label label = new Label();
        label.setMinSize(100, 100);
        label.setStyle("-fx-background-color: rgba(0,0,0,0)");
        label.setStyle("-fx-border-color: rgb(255,255,255)");

        return label;
    }

    private Parent startingBoard(){
        BorderPane board = new BorderPane();
        VBox vBox = new VBox();

        FieldsMap mapOfFields = new FieldsMap();

        for (int i = 0; i < 8; i++) { // i - rows
            HBox hBox = new HBox();
            for(int j = 0; j < 8; j++) { //j - columns
                tabLabel[i][j] = labelEvent();
                hBox.getChildren().add(tabLabel[i][j]);
                mapOfFields.addField(i*10+j, new Field(i, j, tabLabel[i][j]));
            }
            vBox.getChildren().add(hBox);
        }

        vBox.setAlignment(Pos.CENTER);
        board.setCenter(vBox);

        return board;
    }

    public Scene loadGame(){
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(imageBack, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
        Background background = new Background(backgroundImage);

        BorderPane bord = new BorderPane();
        bord.setBackground(background);
        bord.setTop(startingBoard());


        Button button2 = buttonReset();

        HBox bottomBox = new HBox();
        bottomBox.getChildren().addAll(button2/*, button3*/);
        bord.setBottom(bottomBox);

        return new Scene(bord, 800, 850);
    }
}
