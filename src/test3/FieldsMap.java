package test3;

import java.util.HashMap;
import java.util.Map;

public class FieldsMap {

    private static Map<Integer,Field> mapOfEmptyFields = new HashMap<>();

    public void addField(int fieldIndex, Field fieldsLabel) {
        this.mapOfEmptyFields.put(fieldIndex, fieldsLabel);
    }

    public static void setField(int rowIndex, int columnIndex, Field filledField){
        mapOfEmptyFields.put(rowIndex*10+columnIndex, filledField);
    }

    public static Field getField(int rowIndex, int columnIndex){
      Field field =  mapOfEmptyFields.get(rowIndex*10+columnIndex);
      return field;
    }
}
