package Reversi;

import java.util.Objects;

public class FieldsCoordinats {

    int i;
    int j;

    public FieldsCoordinats(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public int backKey(){
        return 10*(i+1)+(j+1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FieldsCoordinats key = (FieldsCoordinats) o;
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
