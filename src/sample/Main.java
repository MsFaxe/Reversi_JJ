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
    Button buttonStart, buttonAlert, buttonConfirm, buttonClose;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        stage.setTitle("Reversi");


        //username screen
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(8);
        grid.setHgap(10);
        Scene scene = new Scene(grid, 500,300);

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
            stage.setScene(scene1);
            System.out.println(nameInput.getText());
        });

        GridPane.setConstraints(buttonGoIn, 0,5);
        grid.getChildren().addAll(usernameLabel, nameInput, buttonGoIn, chooseColorText, choiceBlack, choiceWhite, comboBox);


        Label label1 = new Label("Reversi game - game rules");

        buttonStart = new Button("START");
        buttonStart.setOnAction(e -> stage.setScene(Board.scene2));

        buttonAlert = new Button("Alert");
        buttonAlert.setOnAction(e -> AlertBox.badMovement("Alert", "You can't do this"));

        buttonConfirm = new Button("confirm your choice");
        buttonConfirm.setOnAction(e ->{
            boolean result = ConfirmBox.choice("Title", "do you confirm?");
            System.out.println(result);
        });

        //close program - closed by "x"
        stage.setOnCloseRequest(e -> {
            e.consume(); //bez tego się zamknie nawet jak jednak się rozmyślę
            closeProgram();
        });
        buttonClose = new Button("Close program");
        buttonClose.setOnAction(e -> closeProgram());

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

        scene1 = new Scene(borderPane, 300,300);


        Board board = new Board();
        board.loadBoard();
        stage.setScene(board.scene2);
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
