package draylar.intotheomega.registry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.EntityDamageSource;

public class OmegaDamageSources {

    public static final String MATRIX_LASER = "matrix_laser";
    public static final String FROSTBUSTER = "frostbuster";

    public static EntityDamageSource createMatrixLaser(Entity source) {
        return new EntityDamageSource(MATRIX_LASER, source);
    }

    public static EntityDamageSource createFrostbuster(Entity source) {
        return new EntityDamageSource(FROSTBUSTER, source);
    }
}
