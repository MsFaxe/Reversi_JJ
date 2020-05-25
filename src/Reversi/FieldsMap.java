package Reversi;

import java.util.HashMap;
import java.util.Map;

public class FieldsMap {

    private final Map<FieldsCoordinates,Field> mapOfEmptyFields = new HashMap<>();

    public void addField(FieldsCoordinates fieldIndex, Field fieldsLabel) {
        mapOfEmptyFields.put(fieldIndex, fieldsLabel);
    }

    public Field getField(int rowIndex, int columnIndex){
        FieldsCoordinates fC = new FieldsCoordinates(rowIndex, columnIndex);
        return mapOfEmptyFields.get(fC);
    }
}
