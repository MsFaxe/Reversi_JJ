package Reversi;

import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;

public class RecordsBoard {

    public void showRecords () {
        Stage stage = new Stage();

        //making this window the main one
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Records board");
        stage.setMinWidth(300);

        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 10, 0));


        Text title = new Text("TOP 10!");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        grid.add(title, 1, 1, 3 , 1);


        VBox positionBox = new VBox(10);
        for(int i = 1; i<11; i++){
            positionBox.getChildren().add(new Text(i + "."));
        }
        grid.add(positionBox, 1,2, 1, 19);

        VBox namesBox = new VBox(10);
        recordsList.sort(Collections.reverseOrder());
        for(int i = 0; i<10; i++) {
            if (recordsList.size() > i) {
                namesBox.getChildren().add(new Text(recordsList.get(i)));
            }
        }
        grid.add(namesBox, 2,2, 1, 19);

        Button closeButton = new Button("close the window");
        closeButton.setOnAction(e -> stage.close());

        Scene scene = new Scene(grid);
        stage.setScene(scene);
        stage.showAndWait();
    }


    public void saveRecords(int humanScore, int computerScore) throws IOException {
        String file ="records.txt";

        Scanner scanner = new Scanner(new File(file));
        while(scanner.hasNext()) {
            recordsList.add(scanner.nextLine());
        }
        scanner.close();

        FileWriter fileWriter = new FileWriter(file);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        for (String record: recordsList){
            printWriter.println(record);
        }
        printWriter.print(humanScore + "/" + computerScore + " " + PlayersScore.getNick());
        printWriter.close();

        recordsList.clear();

        loadRecords();
    }

    private final ArrayList <String> recordsList = new ArrayList<>();

    public void loadRecords() throws IOException{
        Scanner scanner = new Scanner(new File("records.txt"));
        while(scanner.hasNext()) {
            recordsList.add(scanner.nextLine());
        }
        scanner.close();

        showRecords();
        recordsList.clear();
    }
}
