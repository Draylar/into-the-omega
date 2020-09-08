package draylar.intotheomega.registry;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.entity.StatueBlockEntity;
import draylar.intotheomega.entity.WatchingEyeBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.registry.Registry;

public class OmegaEntities {

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

    public static final BlockEntityType<WatchingEyeBlockEntity> WATCHING_EYE = register(
            "watching_eye",
            BlockEntityType.Builder.create(WatchingEyeBlockEntity::new, OmegaBlocks.WATCHING_EYE).build(null));

    public static final BlockEntityType<StatueBlockEntity> STATUE = register(
            "statue",
            BlockEntityType.Builder.create(StatueBlockEntity::new, OmegaBlocks.ENDER_DRAGON_STATUE, OmegaBlocks.ENDERMAN_STATUE).build(null));

    private static <T extends Entity> EntityType<T> register(String name, EntityType<T> entity) {
        return Registry.register(Registry.ENTITY_TYPE, IntoTheOmega.id(name), entity);
    }

    private static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType<T> entity) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, IntoTheOmega.id(name), entity);
    }

    public static void init() {
//        FabricDefaultAttributeRegistry.register(TRUE_EYE_OF_ENDER, MobEntity.createMobAttributes());
//        FabricDefaultAttributeRegistry.register(OMEGA_GOD, MobEntity.createMobAttributes());
    }

    private OmegaEntities() {
        // NO-OP
    }
}
