package Reversi;

import java.util.Objects;

public class FieldsCoordinates {
    int i;
    int j;

    public FieldsCoordinates(int i, int j) {
        this.i = i;
        this.j = j;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FieldsCoordinates key = (FieldsCoordinates) o;
        return i == key.i &&
                j == key.j;
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, j);
    }

    @Override
    public String toString() {
        return  i + "" + j;
    }
}
