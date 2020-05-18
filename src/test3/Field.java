package test3;

import javafx.scene.control.Label;

public class Field {

    private final int rowIndex;
    private final int columnIndex;
    private final Label fieldsLabel;
    private Pawn pawn;

    public Field(int rowIndex, int columnIndex, Label fieldsLabel) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.fieldsLabel = fieldsLabel;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public Label getFieldsLabel() {
        return fieldsLabel;
    }

    public Pawn getPawn() {
        return pawn;
    }

    public void putPawn(String colorOfPuttedPawn) {
        Pawn pawn = new Pawn(colorOfPuttedPawn);
        this.pawn = pawn;
        pawn.appears(fieldsLabel);

        deactivateField();
    }

    public void reversePawn() {
        pawn.reverse(fieldsLabel);
    }

    public void activateTheField(){
        ShowPawn show = new ShowPawn();
        fieldsLabel.setOnMouseClicked(e -> {
            fieldsLabel.setGraphic(show.setPawnImage("black"));
            deactivateField();
        });
    }

    public void deactivateField(){
        fieldsLabel.setPickOnBounds(false);
    }

    @Override
    public String toString() {
        return "Field " + rowIndex +
                "" + columnIndex +
                " = " + pawn;
    }
}


