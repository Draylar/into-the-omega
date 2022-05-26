package draylar.intotheomega.api;

import net.minecraft.util.math.Vec3d;

public class VectorHelper {

    public static Vec3d clampMagnitude(Vec3d base, double cap) {
        double length = base.length();
        double multiplier = 1.0;

        if(length > cap) {
            multiplier = cap / length;
        }

        return base.multiply(multiplier);
    }
}
