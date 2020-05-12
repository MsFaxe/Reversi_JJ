package test1;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameRulesScene {
    Button buttonStart;
    Button buttonAlert;
    Button buttonConfirm;
    static Button buttonClose;
    static Stage stage;
    static Scene scene1;

    public void loadGameRulesScene() {
        Label label1 = new Label("Reversi game - game rules");

        buttonStart = new Button("START");
        buttonStart.setOnAction(e -> Main.stage.setScene(Board.scene2));

        buttonAlert = new Button("Alert");
        buttonAlert.setOnAction(e -> AlertBox.badMovement("Alert", "You can't do this"));

        buttonConfirm = new Button("confirm your choice");
        buttonConfirm.setOnAction(e -> {
            boolean result = ConfirmBox.choice("Title", "do you confirm?");
            System.out.println(result);
        });


        buttonClose = new Button("Close program");
        //buttonClose.setOnAction(e -> closeProgram());


        //layout1 - games rules
        HBox topMenu = new HBox(10);
        Button buttonA = new Button("buttonA");
        Button buttonB = new Button("buttonB");
        Button buttonC = new Button("buttonC");
        topMenu.getChildren().addAll(buttonA, buttonB, buttonC);

        VBox leftMenu = new VBox(10);
        leftMenu.getChildren().addAll(label1, buttonStart, buttonAlert, buttonConfirm, buttonClose);


        BorderPane borderPane = new BorderPane();
        borderPane.setTop(topMenu);
        borderPane.setLeft(leftMenu);

        stage = new Stage();
        scene1 = new Scene(borderPane, 300, 300);
        stage.setScene(scene1);
        stage.show();
    }
}
