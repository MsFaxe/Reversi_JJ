package test2;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;

public class BoardGameScene{

    private static Label[][] tabLabel = new Label[8][8];

    public static Label[][] getTabLabel() {
        return tabLabel;
    }

    public static Label getOneLabel(int i, int j) {
        return tabLabel[i][j];
    }


    private Image imageback = new Image("file:resources/background.jpg");

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

//    private Button buttonChangeColorOfPowns(){
//        Button button = new Button("Change color");
//        button.setOnAction(e -> Main.stage.setScene(loadGame()));
//
//        if (getPawnColor() == "white"){
//            StartGameScene.pawnColor = "black";
//        } else {
//            StartGameScene.pawnColor = "white";
//        }
//
//        return button;
//    }

    protected static ImageView getDot(String dotColor){
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

    private Label labelEvent(String string){
        Label label = new Label(string);
        label.setMinSize(100, 100);
        label.setStyle("-fx-background-color: rgba(0,0,0,0)");
        label.setStyle("-fx-border-color: rgb(255,255,255)");

        return label;
    }

    private Parent startingBoard(){
        BorderPane board = new BorderPane();
        VBox vBox = new VBox();

        for (int i = 0; i < 8; i++) { // i - rows
            HBox hBox = new HBox();
            for(int j = 0; j < 8; j++) { //j - columns
                tabLabel[i][j] = labelEvent(i +""+ j);
                hBox.getChildren().add(tabLabel[i][j]);
            }
            vBox.getChildren().add(hBox);
        }

        tabLabel[3][3].setGraphic(getDot("white"));
        tabLabel[3][3].setId("white");
        tabLabel[3][3].setPickOnBounds(false);

        tabLabel[3][4].setGraphic(getDot("black"));
        tabLabel[3][4].setId("black");
        tabLabel[3][4].setPickOnBounds(false);

        tabLabel[4][3].setGraphic(getDot("black"));
        tabLabel[4][3].setId("black");
        tabLabel[4][3].setPickOnBounds(false);

        tabLabel[4][4].setGraphic(getDot("white"));
        tabLabel[4][4].setId("white");
        tabLabel[4][4].setPickOnBounds(false);

        vBox.setAlignment(Pos.CENTER);
        board.setCenter(vBox);

        return board;
    }


    public Scene loadGame(){
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(imageback, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
        Background background = new Background(backgroundImage);

        BorderPane bord = new BorderPane();
        bord.setBackground(background);

        Button button1 = buttonCloseGame();
        Button button2 = buttonReset();
//        Button button3 = buttonChangeColorOfPowns();

        bord.setTop(startingBoard());
        HBox bottomBox = new HBox();
        bottomBox.getChildren().addAll(button1, button2/*, button3*/);
        bord.setBottom(bottomBox);

        Scene scene = new Scene(bord, 800, 850);

        GameLogic gameLogic = new GameLogic();
        gameLogic.humanPlay();

        return scene;
    }
}
