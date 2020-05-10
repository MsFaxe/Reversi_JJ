package test;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;

import static test.StartGameScene.getPawnColor;

public class BoardGameScene{

    private Image imageback = new Image("file:resources/background.jpg");

    private ImageView getDot(String dotColor){
        Image blackDot = new Image("file:resources/blackDot.png");
        Image whiteDot = new Image("file:resources/whiteDot.png");

        ImageView pawn;

        if (dotColor == "white") {
            pawn = new ImageView(whiteDot);
        }else {
            pawn = new ImageView(blackDot);
        }
        pawn.setFitHeight(95);
        pawn.setFitWidth(95);

        return pawn;
    }

    private Button buttonCloseGame(){
        Button button = new Button("End");
        button.setOnAction(e -> System.exit(0));
        return button;
    }

    private Button buttonReset(){
        Button button = new Button("Reset");
        button.setOnAction(e -> Main.stage.setScene(loadGame()));
        return button;
    }

    private Label labelEvent(){
        Label label = new Label();
        label.setMinSize(100, 100);
        label.setStyle("-fx-background-color: rgba(0,0,0,0)");
        label.setStyle("-fx-border-color: rgb(255,255,255)");

       // label.setOnMouseClicked(e -> label.setStyle("-fx-background-image: url(\"/resources/blackDot.png\");");

        label.setOnMouseClicked(e -> label.setGraphic(getDot(getPawnColor())));

        return label;
    }

    private Parent layout(){
        BorderPane layout = new BorderPane();
        VBox vBox = new VBox();

        for (int i = 0; i < 8; ++i) {
            HBox hBox = new HBox();
            for(int j = 0; j < 8; j++) {
                Label label = labelEvent();
                hBox.getChildren().add(label);
            }
            vBox.getChildren().add(hBox);
        }

        vBox.setAlignment(Pos.CENTER);
        layout.setCenter(vBox);

        return layout;
    }

    public Scene loadGame(){
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(imageback, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
        Background background = new Background(backgroundImage);

        BorderPane bord = new BorderPane();
        bord.setBackground(background);

        Button button1 = buttonCloseGame();
        Button button2 = buttonReset();

        bord.setTop(layout());
        HBox bottomBox = new HBox();
        bottomBox.getChildren().addAll(button1, button2);
        bord.setBottom(bottomBox);


        Scene scene = new Scene(bord, 800, 850);
        return scene;
    }
}
