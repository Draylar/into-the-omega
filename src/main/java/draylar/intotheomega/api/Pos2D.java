package draylar.intotheomega.api;

import java.util.Objects;

public class Pos2D {
    public int x;
    public int z;

    public Pos2D(int x, int z) {
        this.x = x;
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pos2D pos2 = (Pos2D) o;
        return x == pos2.x && z == pos2.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, z);
    }
}
