package sample;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Board {

    private Image imageback = new Image("file:resources/background.jpg");
    private Image blackDot = new Image("file:resources/blackDot.png");
    private Image whiteDot = new Image("file:resources/whiteDot.png");
    private GridPane dots = new GridPane(/*Orientation.HORIZONTAL*/);

    static Stage stage;
    static Scene scene2;

    public void loadBoard(){

        Pane pane = new HBox(15);
        pane.getChildren().add(new ImageView(blackDot));
        Scene dotScene = new Scene(pane, 600,300);

        //Button2
        Button buttonReset = new Button("BACK");
        buttonReset.setOnAction(e -> stage.setScene(Main.scene1));

        //layaout2
        GridPane layout2 = new GridPane();
        //layout2.setAlignment(Pos.CENTER);
        layout2.setPadding(new Insets(10,10,10,10));
        layout2.setVgap(10);
        layout2.setHgap(10);

        ImageView pown1 = new ImageView(blackDot);
        pown1.setFitHeight(100);
        pown1.setFitWidth(100);
        GridPane.setConstraints(pown1, 1,1);
        ImageView pown2 = new ImageView(whiteDot);
        pown2.setFitHeight(95);
        pown2.setFitWidth(95);
        GridPane.setConstraints(pown1, 1,2);
        ImageView pown3 = new ImageView(blackDot);
        pown3.setFitHeight(100);
        pown3.setFitWidth(100);
        GridPane.setConstraints(pown1, 2,1);
        ImageView pown4 = new ImageView(whiteDot);
        pown4.setFitHeight(95);
        pown4.setFitWidth(95);
        GridPane.setConstraints(pown1, 2,2);


        Button testPown = new Button("", pown1);
        dots.getChildren().addAll(pown1, pown2, pown3, pown4);
        // GridPane.setConstraints(buttonReset, 0,0);

        BackgroundSize backgroundSize = new BackgroundSize(800, 800, true, true, true, false);
        layout2.setBackground(new Background(new BackgroundImage(imageback,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                backgroundSize)));

        layout2.add(dots,1,1,1,1);
        layout2.getChildren().add(testPown);
        scene2 = new Scene(layout2, 800,800);

    }
}
