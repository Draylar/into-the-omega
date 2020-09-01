package draylar.intotheomega.registry;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.entity.TrueEyeOfEnderEntity;
import draylar.intotheomega.entity.og.OmegaGodEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.registry.Registry;

public class OmegaEntities {

//    public static final EntityType<OmegaGodEntity> OMEGA_GOD = register(
//            "omega_god",
//            FabricEntityTypeBuilder
//                    .create(SpawnGroup.MONSTER, OmegaGodEntity::new)
//                    .trackable(150, 4)
//                    .dimensions(EntityDimensions.fixed(1, 1))
//                    .build());

//    public static final EntityType<TrueEyeOfEnderEntity> TRUE_EYE_OF_ENDER = register(
//            "true_eye_of_ender",
//            FabricEntityTypeBuilder
//                    .create(SpawnGroup.MONSTER, TrueEyeOfEnderEntity::new)
//                    .trackable(150, 4)
//                    .dimensions(EntityDimensions.fixed(.6f, .6f))
//                    .build());
//
//    public static final EntityType<OmegaGodEntity> OMEGA_GOD = register(
//            "omega_god",
//            FabricEntityTypeBuilder
//                    .create(SpawnGroup.MONSTER, OmegaGodEntity::new)
//                    .trackable(150, 4)
//                    .dimensions(EntityDimensions.fixed(15f, 15f))
//                    .build());

    private static <T extends Entity> EntityType<T> register(String name, EntityType<T> entity) {
        return Registry.register(Registry.ENTITY_TYPE, IntoTheOmega.id(name), entity);
    }

    public static void init() {
//        FabricDefaultAttributeRegistry.register(TRUE_EYE_OF_ENDER, MobEntity.createMobAttributes());
//        FabricDefaultAttributeRegistry.register(OMEGA_GOD, MobEntity.createMobAttributes());
    }

    private OmegaEntities() {
        // NO-OP
    }
}
