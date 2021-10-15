package draylar.intotheomega.api;

import net.minecraft.util.math.Direction;

import java.util.Objects;

public class Vec2i {

    public final int x;
    public final int z;

    public Vec2i(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public Vec2i offset(Direction direction) {
        return new Vec2i(x + direction.getOffsetX(), z + direction.getOffsetZ());
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Vec2i vec2i = (Vec2i) o;
        return x == vec2i.x && z == vec2i.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, z);
    }
}
