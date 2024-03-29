package draylar.intotheomega.registry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import org.jetbrains.annotations.Nullable;

public class OmegaDamageSources {

    public static final String MATRIX_LASER = "matrix_laser";
    public static final String FROSTBUSTER = "frostbuster";
    public static final String NOVA_BURST = "frostbuster";
    public static final String STARFALL_PROJECTILE = "starfall_projectile";
    public static final String QUASAR_SLASH = "quasar_slash";

    public static EntityDamageSource createMatrixLaser(Entity source) {
        return new EntityDamageSource(MATRIX_LASER, source);
    }

    public static EntityDamageSource createFrostbuster(Entity source) {
        return new EntityDamageSource(FROSTBUSTER, source);
    }

    public static EntityDamageSource nova(Entity source) {
        return new EntityDamageSource(NOVA_BURST, source);
    }

    public static EntityDamageSource quasarSlash(Entity source) {
        return new EntityDamageSource(QUASAR_SLASH, source);
    }


    public static DamageSource createStarfallProjectile(@Nullable Entity owner) {
        if(owner == null) {
            return DamageSource.GENERIC;
        }

        return new EntityDamageSource(STARFALL_PROJECTILE, owner);
    }
}
