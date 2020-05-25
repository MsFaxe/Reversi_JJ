package Reversi;

import java.util.HashMap;
import java.util.Map;

public class FieldsMap {

    private final Map<Integer,Field> mapOfEmptyFields = new HashMap<>();

    public void addField(int fieldIndex, Field fieldsLabel) {
        mapOfEmptyFields.put(fieldIndex, fieldsLabel);
    }

    public Field getField(int rowIndex, int columnIndex){
      return mapOfEmptyFields.get(rowIndex*10+columnIndex);
    }
}
