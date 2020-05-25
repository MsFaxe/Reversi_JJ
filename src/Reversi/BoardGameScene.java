package Reversi;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class BoardGameScene {
    private final Image imageBack = new Image("file:resources/background.jpg");
    private final Label[][] tabLabel = new Label[8][8];

    private Button buttonSaveGame(){
        Button button = new Button("Save Game");

        button.setOnAction(e -> {
            try {
                GameLogic.saveGame();
            }catch (Exception exc){
                //
            }
        });
        return button;
    }

    private Parent scoreBox(String username) {
        BorderPane board = new BorderPane();
        HBox hBox = new HBox(10);

        Label humanScore = new Label();
        Label computerScore = new Label();
        Label user = new Label();

        if(!(username.isEmpty())) {
           user.setText("Nickname: " + username);
        } else{
            user.setText("Nickname: " + "-");
        }

        PlayersScore playersScore = new PlayersScore();
        playersScore.setComputerScore(computerScore);
        playersScore.setPlayerScore(humanScore);
        playersScore.setNick(username);

        hBox.getChildren().addAll(humanScore, computerScore, user, buttonSaveGame());
        board.setCenter(hBox);
        return board;
    }

//    private Button buttonReset(){
//        Button button = new Button("Reset");
//        GameLogic gameLogic = new GameLogic();
//        button.setOnAction(e -> {
//            Main.stage.setScene(loadGame());
//            gameLogic.startGame();
//        });
//        return button;
//    }

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

    public Scene loadGame(String username){
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(imageBack, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
        Background background = new Background(backgroundImage);

        BorderPane bord = new BorderPane();
        bord.setBackground(background);
        bord.setTop(startingBoard());

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(8);
        grid.setHgap(10);

        //Button button2 = buttonReset();
        //GridPane.setConstraints(button2, 0, 0);
        //GridPane.setConstraints(scoreLabels(), 2, 1);

        grid.getChildren().addAll(scoreBox(username));
        bord.setBottom(grid);

        return new Scene(bord, 800, 850);
    }
}
