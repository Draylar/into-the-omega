package draylar.intotheomega.registry;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.entity.*;
import draylar.intotheomega.entity.block.*;
import draylar.intotheomega.entity.dungeon.BejeweledLockBlockEntity;
import draylar.intotheomega.entity.dungeon.InvisibleDungeonBrickBlockEntity;
import draylar.intotheomega.entity.enigma.EnigmaKingEntity;
import draylar.intotheomega.entity.matrite.MatriteEntity;
import draylar.intotheomega.entity.slime.OmegaSlimeEmperorEntity;
import draylar.intotheomega.entity.void_matrix.VoidMatrixBeamEntity;
import draylar.intotheomega.entity.void_matrix.VoidMatrixEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.util.registry.Registry;

public class OmegaEntities {

    public static final EntityType<ChorusCowEntity> CHORUS_COW = register(
            "chorus_cow",
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ChorusCowEntity::new).dimensions(EntityDimensions.fixed(0.9F, 1.4F)).build());

    public static final BlockEntityType<WatchingEyeBlockEntity> WATCHING_EYE = register(
            "watching_eye",
            BlockEntityType.Builder.create(WatchingEyeBlockEntity::new, OmegaBlocks.WATCHING_EYE).build(null));

    public static final BlockEntityType<EnigmaStandBlockEntity> ENIGMA_STAND = register(
            "enigma_stand",
            BlockEntityType.Builder.create(EnigmaStandBlockEntity::new, OmegaBlocks.ENIGMA_STAND).build(null));

    public static final EntityType<InanisEntity> INANIS = register(
            "inanis",
            FabricEntityTypeBuilder.<InanisEntity>create(SpawnGroup.MISC, InanisEntity::new).dimensions(EntityDimensions.fixed(.5f, .5f)).build());

    public static final EntityType<DualInanisEntity> DUAL_INANIS = register(
            "dual_inanis",
            FabricEntityTypeBuilder.<DualInanisEntity>create(SpawnGroup.MISC, DualInanisEntity::new).dimensions(EntityDimensions.fixed(.5f, .5f)).build());

    public static final EntityType<VioletUnionBladeEntity> VIOLET_UNION_BLADE = register(
            "violet_union_blade",
            FabricEntityTypeBuilder.<VioletUnionBladeEntity>create(SpawnGroup.MISC, VioletUnionBladeEntity::new).dimensions(EntityDimensions.fixed(.5f, .5f)).build());

    public static final EntityType<VoidMatrixEntity> VOID_MATRIX = register(
            "void_matrix",
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, VoidMatrixEntity::new).forceTrackedVelocityUpdates(true).dimensions(EntityDimensions.fixed(4, 4)).build());

    public static final EntityType<EnigmaKingEntity> ENIGMA_KING = register(
            "enigma_king",
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, EnigmaKingEntity::new).dimensions(EntityDimensions.fixed(1, 2.2f)).build());

    public static final EntityType<OmegaSlimeEmperorEntity> OMEGA_SLIME_EMPEROR = register(
            "omega_slime_emperor",
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, OmegaSlimeEmperorEntity::new).dimensions(EntityDimensions.fixed(6, 6)).build());

    public static final EntityType<MatriteEntity> MATRITE = register(
            "matrite",
            FabricEntityTypeBuilder.<MatriteEntity>create(SpawnGroup.MONSTER, MatriteEntity::new).dimensions(EntityDimensions.fixed(.5f, .5f)).build());

    public static final EntityType<OmegaSlimeEntity> OMEGA_SLIME = register(
            "omega_slime",
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, OmegaSlimeEntity::new).dimensions(EntityDimensions.fixed(1f, 1f)).build());

    public static final EntityType<MatrixBombEntity> MATRIX_BOMB = register(
            "matrix_bomb",
            FabricEntityTypeBuilder.<MatrixBombEntity>create(SpawnGroup.MISC, MatrixBombEntity::new).dimensions(EntityDimensions.fixed(.25f, .25f)).build());

    public static final EntityType<ObsidianThornEntity> OBSIDIAN_THORN = register(
            "obsidian_thorn",
            FabricEntityTypeBuilder.<ObsidianThornEntity>create(SpawnGroup.MISC, ObsidianThornEntity::new).dimensions(EntityDimensions.changing(.25f, .25f)).build());

    public static final BlockEntityType<EternalPillarBlockEntity> ETERNAL_PILLAR = register(
            "eternal_pillar",
            BlockEntityType.Builder.create(EternalPillarBlockEntity::new, OmegaBlocks.ETERNAL_PILLAR).build(null));

    public static final BlockEntityType<VoidMatrixSpawnBlockEntity> VOID_MATRIX_SPAWN_BLOCK = register(
            "void_matrix_spawn_block",
            BlockEntityType.Builder.create(VoidMatrixSpawnBlockEntity::new, OmegaBlocks.VOID_MATRIX_SPAWN_BLOCK).build(null));

    public static final BlockEntityType<PhasePadBlockEntity> PHASE_PAD = register(
            "phase_pad",
            BlockEntityType.Builder.create(PhasePadBlockEntity::new, OmegaBlocks.PHASE_PAD).build(null));

    public static final BlockEntityType<InvisibleDungeonBrickBlockEntity> INVISIBLE_DUNGEON_BRICK = register(
            "invisible_dungeon_brick",
            BlockEntityType.Builder.create(InvisibleDungeonBrickBlockEntity::new, OmegaBlocks.INVISIBLE_DUNGEON_BRICK).build(null));

    public static final EntityType<OmegaSlimeMountEntity> OMEGA_SLIME_MOUNT = register(
            "omega_slime_mount",
            FabricEntityTypeBuilder.<OmegaSlimeMountEntity>create(SpawnGroup.MISC, OmegaSlimeMountEntity::new).trackRangeBlocks(128).trackedUpdateRate(4).dimensions(EntityDimensions.fixed(.75f, .75f)).build());

    public static final EntityType<FrostedEyeEntity> FROSTED_EYE = register(
            "frosted_eye",
            FabricEntityTypeBuilder.<FrostedEyeEntity>create(SpawnGroup.MONSTER, FrostedEyeEntity::new).dimensions(EntityDimensions.fixed(1, 1)).build());

    public static final EntityType<AbyssalRiftEntity> ABYSSAL_RIFT = register(
            "abyssal_rift",
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, AbyssalRiftEntity::new).trackRangeBlocks(1000).dimensions(EntityDimensions.fixed(5, 5)).build());

    public static final BlockEntityType<AbyssChainBlockEntity> ABYSS_CHAIN = register(
            "abyss_chain",
            BlockEntityType.Builder.create(AbyssChainBlockEntity::new, OmegaBlocks.ABYSS_CHAIN).build(null));

    public static final EntityType<VoidMatrixBeamEntity> VOID_MATRIX_BEAM = register(
            "void_matrix_beam",
            FabricEntityTypeBuilder.<VoidMatrixBeamEntity>create(SpawnGroup.MISC, VoidMatrixBeamEntity::new).dimensions(EntityDimensions.fixed(.25f, .25f)).build());

    public static final BlockEntityType<BejeweledLockBlockEntity> BEJEWELED_LOCK = register(
            "bejeweled_lock",
            BlockEntityType.Builder.create(BejeweledLockBlockEntity::new, OmegaBlocks.BEJEWELED_LOCK).build(null));

    public static final EntityType<VoidWalkerEntity> VOID_WALKER = register(
            "void_walker",
            FabricEntityTypeBuilder.<VoidWalkerEntity>create(SpawnGroup.MISC, VoidWalkerEntity::new).dimensions(EntityDimensions.fixed(1f, 1.8f)).build());

    private static <T extends Entity> EntityType<T> register(String name, EntityType<T> entity) {
        return Registry.register(Registry.ENTITY_TYPE, IntoTheOmega.id(name), entity);
    }

    private static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType<T> entity) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, IntoTheOmega.id(name), entity);
    }

    public static void init() {
//        FabricDefaultAttributeRegistry.register(TRUE_EYE_OF_ENDER, MobEntity.createMobAttributes());
//        FabricDefaultAttributeRegistry.register(OMEGA_GOD, MobEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(CHORUS_COW, CowEntity.createCowAttributes());
        FabricDefaultAttributeRegistry.register(VOID_MATRIX, VoidMatrixEntity.createVoidMatrixAttributes());
        FabricDefaultAttributeRegistry.register(ENIGMA_KING, EnigmaKingEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(OMEGA_SLIME_EMPEROR, MobEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(OMEGA_SLIME, HostileEntity.createHostileAttributes());
        FabricDefaultAttributeRegistry.register(FROSTED_EYE, HostileEntity.createHostileAttributes());
        FabricDefaultAttributeRegistry.register(ABYSSAL_RIFT, HostileEntity.createHostileAttributes());
        FabricDefaultAttributeRegistry.register(VOID_WALKER, VoidWalkerEntity.createVoidWalkerAttributes());
    }

    private OmegaEntities() {
        // NO-OP
    }
}
