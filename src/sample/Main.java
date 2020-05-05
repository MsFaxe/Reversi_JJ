package sample;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.*;

public class Main extends Application /*implements EventHandler<ActionEvent> */{

    Stage stage;
    Scene scene1, scene2;
    Button buttonStart, buttonReset, buttonAlert, buttonConfirm, buttonClose;

    private Image imageback = new Image("file:resources/background.jpg");
    private Image blackDot = new Image("file:resources/black_dot100.jpg");
    private Image whiteDot = new Image("file:resources/white_dot100.jpg");
    private FlowPane dots = new FlowPane(Orientation.HORIZONTAL);

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
        buttonStart.setOnAction(e -> stage.setScene(scene2));

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

        //Button2
        buttonReset = new Button("BACK");
        buttonReset.setOnAction(e -> stage.setScene(scene1));

        //layaout2
        GridPane layout2 = new GridPane();
        layout2.setAlignment(Pos.CENTER);
        layout2.setPadding(new Insets(10,10,10,10));
        layout2.setVgap(10);
        layout2.setHgap(10);

        ImageView pown1 = new ImageView(whiteDot);
        dots.getChildren().add(pown1);
        GridPane.setConstraints(pown1, 0,0);
        GridPane.setConstraints(buttonReset, 7,7);

        BackgroundSize backgroundSize = new BackgroundSize(800, 800, true, true, true, false);
        layout2.setBackground(new Background(new BackgroundImage(imageback,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                backgroundSize)));

        layout2.add(dots,1,1,1,1);
        layout2.getChildren().add(buttonReset);
        scene2 = new Scene(layout2, 800,800);


        Scene scene = new Scene(grid, 500,300);

        stage.setScene(scene);
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
