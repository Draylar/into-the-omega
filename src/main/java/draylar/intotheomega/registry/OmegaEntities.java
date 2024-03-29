package draylar.intotheomega.registry;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.enchantment.EndSlimeEntity;
import draylar.intotheomega.entity.*;
import draylar.intotheomega.entity.enigma.EnigmaKingEntity;
import draylar.intotheomega.entity.envoy.EnvoyEntity;
import draylar.intotheomega.entity.envoy.EnvoySegmentEntity;
import draylar.intotheomega.entity.ice.AbyssGlobeEntity;
import draylar.intotheomega.entity.matrite.MatriteEntity;
import draylar.intotheomega.entity.nova.*;
import draylar.intotheomega.entity.slime.OmegaSlimeEmperorEntity;
import draylar.intotheomega.entity.starfall.StarfallProjectileEntity;
import draylar.intotheomega.entity.void_matrix.indicator.MatriteTargetIndicatorEntity;
import draylar.intotheomega.entity.void_matrix.beam.VoidMatrixBeamEntity;
import draylar.intotheomega.entity.void_matrix.VoidMatrixEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;

public class OmegaEntities {

    public static final EntityType<ChorusCowEntity> CHORUS_COW = register(
            "chorus_cow",
            FabricEntityTypeBuilder
                    .create(SpawnGroup.CREATURE, ChorusCowEntity::new)
                    .dimensions(EntityDimensions.fixed(0.9F, 1.4F))
                    .build());

    public static final EntityType<InanisEntity> INANIS = register(
            "inanis",
            FabricEntityTypeBuilder
                    .<InanisEntity>create(SpawnGroup.MISC, InanisEntity::new)
                    .dimensions(EntityDimensions.fixed(.5f, .5f))
                    .build());

    public static final EntityType<DualInanisEntity> DUAL_INANIS = register(
            "dual_inanis",
            FabricEntityTypeBuilder
                    .<DualInanisEntity>create(SpawnGroup.MISC, DualInanisEntity::new)
                    .dimensions(EntityDimensions.fixed(.5f, .5f))
                    .build());

    public static final EntityType<VioletUnionBladeEntity> VIOLET_UNION_BLADE = register(
            "violet_union_blade",
            FabricEntityTypeBuilder
                    .<VioletUnionBladeEntity>create(SpawnGroup.MISC, VioletUnionBladeEntity::new)
                    .dimensions(EntityDimensions.fixed(.5f, .5f))
                    .build());

    public static final EntityType<VoidMatrixEntity> VOID_MATRIX = register(
            "void_matrix",
            FabricEntityTypeBuilder
                    .create(SpawnGroup.MONSTER, VoidMatrixEntity::new)
                    .forceTrackedVelocityUpdates(true)
                    .dimensions(EntityDimensions.fixed(4, 4))
                    .build());

    public static final EntityType<EnigmaKingEntity> ENIGMA_KING = register(
            "enigma_king",
            FabricEntityTypeBuilder
                    .create(SpawnGroup.MONSTER, EnigmaKingEntity::new)
                    .dimensions(EntityDimensions.fixed(1, 2.2f))
                    .build());

    public static final EntityType<OmegaSlimeEmperorEntity> OMEGA_SLIME_EMPEROR = register(
            "omega_slime_emperor",
            FabricEntityTypeBuilder
                    .create(SpawnGroup.MONSTER, OmegaSlimeEmperorEntity::new)
                    .dimensions(EntityDimensions.fixed(6, 6))
                    .build());

    public static final EntityType<MatriteEntity> MATRITE = register(
            "matrite",
            FabricEntityTypeBuilder
                    .<MatriteEntity>create(SpawnGroup.MONSTER, MatriteEntity::new)
                    .dimensions(EntityDimensions.fixed(.5f, .5f))
                    .build());

    public static final EntityType<OmegaSlimeEntity> OMEGA_SLIME = register(
            "omega_slime",
            FabricEntityTypeBuilder
                    .create(SpawnGroup.MONSTER, OmegaSlimeEntity::new)
                    .dimensions(EntityDimensions.fixed(1f, 1f))
                    .build());

    public static final EntityType<MatrixBombEntity> MATRIX_BOMB = register(
            "matrix_bomb",
            FabricEntityTypeBuilder
                    .<MatrixBombEntity>create(SpawnGroup.MISC, MatrixBombEntity::new)
                    .dimensions(EntityDimensions.fixed(.25f, .25f))
                    .build());

    public static final EntityType<ObsidianThornEntity> OBSIDIAN_THORN = register(
            "obsidian_thorn",
            FabricEntityTypeBuilder
                    .<ObsidianThornEntity>create(SpawnGroup.MISC, ObsidianThornEntity::new)
                    .dimensions(EntityDimensions.changing(.25f, .25f))
                    .build());

    public static final EntityType<OmegaSlimeMountEntity> OMEGA_SLIME_MOUNT = register(
            "omega_slime_mount",
            FabricEntityTypeBuilder
                    .<OmegaSlimeMountEntity>create(SpawnGroup.MISC, OmegaSlimeMountEntity::new)
                    .trackRangeBlocks(128)
                    .trackedUpdateRate(4)
                    .dimensions(EntityDimensions.fixed(.75f, .75f))
                    .build());

    public static final EntityType<FrostedEyeEntity> FROSTED_EYE = register(
            "frosted_eye",
            FabricEntityTypeBuilder
                    .createMob()
                    .spawnGroup(SpawnGroup.MONSTER)
                    .entityFactory(FrostedEyeEntity::new)
                    .spawnRestriction(SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FrostedEyeEntity::canSpawnAt)
                    .dimensions(EntityDimensions.fixed(2, 2))
                    .build());

    public static final EntityType<AbyssalRiftEntity> ABYSSAL_RIFT = register(
            "abyssal_rift",
            FabricEntityTypeBuilder
                    .create(SpawnGroup.MONSTER, AbyssalRiftEntity::new)
                    .trackRangeBlocks(1000)
                    .dimensions(EntityDimensions.fixed(5, 5))
                    .build());

    public static final EntityType<VoidMatrixBeamEntity> VOID_MATRIX_BEAM = register(
            "void_matrix_beam",
            FabricEntityTypeBuilder
                    .<VoidMatrixBeamEntity>create(SpawnGroup.MISC, VoidMatrixBeamEntity::new)
                    .dimensions(EntityDimensions.fixed(.25f, .25f))
                    .build());

    public static final EntityType<VoidWalkerEntity> VOID_WALKER = register(
            "void_walker",
            FabricEntityTypeBuilder
                    .<VoidWalkerEntity>create(SpawnGroup.MISC, VoidWalkerEntity::new)
                    .dimensions(EntityDimensions.fixed(1f, 1.8f))
                    .build());

    public static final EntityType<FrostedEndermanEntity> FROSTED_ENDERMAN = register(
            "frosted_enderman",
            FabricEntityTypeBuilder
                    .createMob()
                    .spawnGroup(SpawnGroup.MONSTER)
                    .entityFactory(FrostedEndermanEntity::new)
                    .spawnRestriction(SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark)
                    .dimensions(EntityDimensions.fixed(0.6F, 2.9F))
                    .build());

    public static final EntityType<EndSlimeEntity> END_SLIME = register(
            "end_slime",
            FabricEntityTypeBuilder
                    .createMob()
                    .spawnGroup(SpawnGroup.MONSTER)
                    .entityFactory(EndSlimeEntity::new)
                    .dimensions(EntityDimensions.changing(2.04F, 2.04F))
                    .build());

    public static final EntityType<VoidBeetleEntity> VOID_BEETLE = register(
            "void_beetle",
            FabricEntityTypeBuilder
                    .createMob()
                    .spawnGroup(SpawnGroup.MONSTER)
                    .entityFactory(VoidBeetleEntity::new)
                    .dimensions(EntityDimensions.fixed(0.75f, 0.75f))
                    .build());

    public static final EntityType<AbyssGlobeEntity> ABYSS_GLOBE = register(
            "abyss_globe",
            FabricEntityTypeBuilder
                    .create()
                    .spawnGroup(SpawnGroup.MISC)
                    .entityFactory(AbyssGlobeEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.5f))
                    .build());

    public static final EntityType<VoidFloaterEntity> VOID_FLOATER = register(
            "void_floater",
            FabricEntityTypeBuilder
                    .createMob()
                    .spawnGroup(SpawnGroup.MISC)
                    .entityFactory(VoidFloaterEntity::new)
                    .defaultAttributes(MobEntity::createMobAttributes)
                    .dimensions(EntityDimensions.fixed(1f, 1f))
                    .build());

    public static final EntityType<EmptyLeafMonsterEntity> LEAF_MONSTER = register(
            "leaf_monster",
            FabricEntityTypeBuilder
                    .createMob()
                    .spawnGroup(SpawnGroup.MISC)
                    .entityFactory(EmptyLeafMonsterEntity::new)
                    .defaultAttributes(MobEntity::createMobAttributes)
                    .dimensions(EntityDimensions.fixed(0.7f, 2f))
                    .build());

    public static final EntityType<LevitationProjectileEntity> LEVITATION_PROJECTILE = register(
            "levitation_projectile",
            FabricEntityTypeBuilder.create()
                    .spawnGroup(SpawnGroup.MISC)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.5f))
                    .entityFactory(LevitationProjectileEntity::new)
                    .build());

    public static final EntityType<SwirlGrenadeEntity> SWIRL_GRENADE = register(
            "swirl_grenade",
            FabricEntityTypeBuilder.create()
                    .spawnGroup(SpawnGroup.MISC)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.5f))
                    .entityFactory(SwirlGrenadeEntity::new)
                    .build());

    public static final EntityType<SlimefallEntity> SLIMEFALL = register(
            "slimefall",
            FabricEntityTypeBuilder.create()
                    .spawnGroup(SpawnGroup.MISC)
                    .dimensions(EntityDimensions.fixed(1f, 1f))
                    .entityFactory(SlimefallEntity::new)
                    .build());

    public static final EntityType<StarfallProjectileEntity> STARFALL_PROJECTILE = register(
            "starfall_projectile",
            FabricEntityTypeBuilder.create()
                    .spawnGroup(SpawnGroup.MISC)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.5f))
                    .entityFactory(StarfallProjectileEntity::new)
                    .build());

    public static final EntityType<EntwinedEntity> ENTWINED = register(
            "entwined",
            FabricEntityTypeBuilder.createMob()
                    .spawnGroup(SpawnGroup.MONSTER)
                    .dimensions(EntityDimensions.fixed(0.75f, 1.8f))
                    .entityFactory(EntwinedEntity::new)
                    .defaultAttributes(EntwinedEntity::createEntwinedAttributes)
                    .build());

    public static final EntityType<AbyssalKnightEntity> ABYSSAL_KNIGHT = register(
            "abyssal_knight",
            FabricEntityTypeBuilder.createMob()
                    .spawnGroup(SpawnGroup.MONSTER)
                    .dimensions(EntityDimensions.fixed(0.75f, 1.8f))
                    .entityFactory(AbyssalKnightEntity::new)
                    .defaultAttributes(AbyssalKnightEntity::createAbyssalKnightAttributes)
                    .build());

    public static final EntityType<StarWalkerEntity> STAR_WALKER = register(
            "star_walker",
            FabricEntityTypeBuilder.createMob()
                    .spawnGroup(SpawnGroup.MONSTER)
                    .dimensions(EntityDimensions.fixed(0.75f, 1.8f))
                    .entityFactory(StarWalkerEntity::new)
                    .defaultAttributes(StarWalkerEntity::createStarWalkerAttributes)
                    .build());

    public static final EntityType<HomingStarlitProjectileEntity> HOMING_STARLIT_PROJECTILE = register(
            "homing_starlit_projectile",
            FabricEntityTypeBuilder.create()
                    .spawnGroup(SpawnGroup.MONSTER)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.5f))
                    .entityFactory(HomingStarlitProjectileEntity::new)
                    .build());

    public static final EntityType<OriginNovaEntity> ORIGIN_NOVA = register(
            "origin_nova",
            FabricEntityTypeBuilder.createMob()
                    .defaultAttributes(MobEntity::createMobAttributes)
                    .spawnGroup(SpawnGroup.MONSTER)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.5f))
                    .entityFactory(OriginNovaEntity::new)
                    .trackRangeBlocks(256)
                    .build());

    public static final EntityType<NovaNodeEntity> NOVA_NODE = register(
            "nova_node",
            FabricEntityTypeBuilder.create()
                    .spawnGroup(SpawnGroup.MONSTER)
                    .dimensions(EntityDimensions.fixed(1.0f, 1.0f))
                    .entityFactory(NovaNodeEntity::new)
                    .trackRangeBlocks(256)
                    .build());

    public static final EntityType<NovaGhoulEntity> NOVA_GHOUL = register(
            "nova_ghoul",
            FabricEntityTypeBuilder.createMob()
                    .spawnGroup(SpawnGroup.MONSTER)
                    .defaultAttributes(NovaGhoulEntity::createNovaGhoulAttributes)
                    .dimensions(EntityDimensions.fixed(0.75f, 3f))
                    .entityFactory(NovaGhoulEntity::new)
                    .trackRangeBlocks(256)
                    .build());

    public static final EntityType<NovaStrikeEntity> NOVA_STRIKE = register(
            "nova_strike",
            FabricEntityTypeBuilder.create()
                    .spawnGroup(SpawnGroup.MONSTER)
                    .dimensions(EntityDimensions.fixed(0.75f, 0.75f))
                    .entityFactory(NovaStrikeEntity::new)
                    .trackRangeBlocks(128)
                    .build());

    public static final EntityType<NovaGroundBurstEntity> NOVA_GROUND_BURST = register(
            "nova_ground_burst",
            FabricEntityTypeBuilder.create()
                    .spawnGroup(SpawnGroup.MONSTER)
                    .dimensions(EntityDimensions.fixed(1.0f, 1.0f))
                    .entityFactory(NovaGroundBurstEntity::new)
                    .trackRangeBlocks(256)
                    .build());

    public static final EntityType<EnvoyEntity> ENVOY = register(
            "envoy",
            FabricEntityTypeBuilder.createMob()
                    .defaultAttributes(EnvoyEntity::createEnvoyAttributes)
                    .spawnGroup(SpawnGroup.MONSTER)
                    .dimensions(EntityDimensions.fixed(5.0f, 5.0f))
                    .entityFactory(EnvoyEntity::new)
                    .trackRangeBlocks(1000)
                    .build());


    public static final EntityType<EnvoySegmentEntity> ENVOY_SEGMENT = register(
            "envoy_segment",
            FabricEntityTypeBuilder.<EnvoySegmentEntity>create()
                    .spawnGroup(SpawnGroup.MONSTER)
                    .dimensions(EntityDimensions.fixed(5.0f, 5.0f))
                    .disableSaving()
                    .entityFactory((type, world) -> new EnvoySegmentEntity(null, world))
                    .trackRangeBlocks(1000)
                    .trackedUpdateRate(1)
                    .forceTrackedVelocityUpdates(true)
                    .build());

    public static final EntityType<MatriteTargetIndicatorEntity> MATRITE_TARGET_INDICATOR = register(
            "matrite_target_indicator",
            FabricEntityTypeBuilder.<MatriteTargetIndicatorEntity>create()
                    .spawnGroup(SpawnGroup.MISC)
                    .dimensions(EntityDimensions.fixed(0.0f, 0.0f))
                    .disableSaving()
                    .entityFactory(MatriteTargetIndicatorEntity::new)
                    .build());

    private static <T extends Entity> EntityType<T> register(String name, EntityType<T> entity) {
        return Registry.register(Registry.ENTITY_TYPE, IntoTheOmega.id(name), entity);
    }

    public static void init() {
        FabricDefaultAttributeRegistry.register(CHORUS_COW, CowEntity.createCowAttributes());
        FabricDefaultAttributeRegistry.register(VOID_MATRIX, VoidMatrixEntity.createVoidMatrixAttributes());
        FabricDefaultAttributeRegistry.register(ENIGMA_KING, EnigmaKingEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(OMEGA_SLIME_EMPEROR, OmegaSlimeEmperorEntity.createEmperorAttributes());
        FabricDefaultAttributeRegistry.register(OMEGA_SLIME, HostileEntity.createHostileAttributes());
        FabricDefaultAttributeRegistry.register(FROSTED_EYE, HostileEntity.createHostileAttributes());
        FabricDefaultAttributeRegistry.register(ABYSSAL_RIFT, HostileEntity.createHostileAttributes());
        FabricDefaultAttributeRegistry.register(VOID_WALKER, VoidWalkerEntity.createVoidWalkerAttributes());
        FabricDefaultAttributeRegistry.register(FROSTED_ENDERMAN, EndermanEntity.createEndermanAttributes());
        FabricDefaultAttributeRegistry.register(END_SLIME, HostileEntity.createHostileAttributes());
        FabricDefaultAttributeRegistry.register(VOID_BEETLE, VoidBeetleEntity.createBeetleAttributes());
    }

    private OmegaEntities() {
        // NO-OP
    }
}
