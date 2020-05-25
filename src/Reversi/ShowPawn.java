package Reversi;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ShowPawn {

    private final Image blackDot = new Image("file:resources/blackDot.png");
    private final Image whiteDot = new Image("file:resources/whiteDot.png");

    public ImageView setPawnImage(String dotColor){

        ImageView pawnImage;

        if (dotColor.equals("white")) {
            pawnImage = new ImageView(whiteDot);
            pawnImage.setFitHeight(93);
            pawnImage.setFitWidth(93);
        }else {
            pawnImage = new ImageView(blackDot);
            pawnImage.setFitHeight(98);
            pawnImage.setFitWidth(98);
        }

        return pawnImage;
    }
}
