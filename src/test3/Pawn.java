package test3;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class Pawn {
    private String colorOfPawn;
    private String black = "black";
    private String white = "white";
    private ImageView pawnIcon;

    public Pawn(String colorOfPuttedPawn) {
        this.colorOfPawn = colorOfPuttedPawn;
    }

    public String getColorOfPawn() {
        return colorOfPawn;
    }

    public void appears(Label label){
        ShowPawn show = new ShowPawn();
        pawnIcon = show.setPawnImage(colorOfPawn);
        label.setGraphic(pawnIcon);
    }

    public void reverse(Label label) {
        ShowPawn show = new ShowPawn();

        if (colorOfPawn.equals(black)) {
            pawnIcon = show.setPawnImage(white);
            colorOfPawn = white;
        } else if (colorOfPawn.equals(white)) {
            pawnIcon = show.setPawnImage(black);
            colorOfPawn = black;
        }

        label.setGraphic(pawnIcon);
    }

    @Override
    public String toString() {
        String color;
        if (colorOfPawn.equals(white)) {
            color = white;
        } else {
            color = black;
        }
        return color + " pawn";
    }
}
