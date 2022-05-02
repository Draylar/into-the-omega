package draylar.intotheomega.registry;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.block.ActivatedStarlightCornerBlock;
import draylar.intotheomega.block.MirrorBlock;
import draylar.intotheomega.entity.WatchingEyeBlockEntity;
import draylar.intotheomega.entity.block.*;
import draylar.intotheomega.entity.dungeon.BejeweledLockBlockEntity;
import draylar.intotheomega.entity.dungeon.InvisibleDungeonBrickBlockEntity;
import draylar.intotheomega.entity.dungeon.SlimeObeliskBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

public class OmegaBlockEntities {

    public static final BlockEntityType<WatchingEyeBlockEntity> WATCHING_EYE = register(
            "watching_eye",
            FabricBlockEntityTypeBuilder.create(WatchingEyeBlockEntity::new, OmegaBlocks.WATCHING_EYE).build(null));

    public static final BlockEntityType<EnigmaStandBlockEntity> ENIGMA_STAND = register(
            "enigma_stand",
            FabricBlockEntityTypeBuilder.create(EnigmaStandBlockEntity::new, OmegaBlocks.ENIGMA_STAND).build(null));

    public static final BlockEntityType<VoidMatrixSpawnBlockEntity> VOID_MATRIX_SPAWN_BLOCK = register(
            "void_matrix_spawn_block",
            FabricBlockEntityTypeBuilder.create(VoidMatrixSpawnBlockEntity::new, OmegaBlocks.VOID_MATRIX_SPAWN_BLOCK).build(null));

    public static final BlockEntityType<PhasePadBlockEntity> PHASE_PAD = register(
            "phase_pad",
            FabricBlockEntityTypeBuilder.create(PhasePadBlockEntity::new, OmegaBlocks.PHASE_PAD).build(null));

    public static final BlockEntityType<InvisibleDungeonBrickBlockEntity> INVISIBLE_DUNGEON_BRICK = register(
            "invisible_dungeon_brick",
            FabricBlockEntityTypeBuilder.create(InvisibleDungeonBrickBlockEntity::new, OmegaBlocks.INVISIBLE_DUNGEON_BRICK).build(null));

    public static final BlockEntityType<AbyssChainBlockEntity> ABYSS_CHAIN = register(
            "abyss_chain",
            FabricBlockEntityTypeBuilder.create(AbyssChainBlockEntity::new, OmegaBlocks.ABYSS_CHAIN).build(null));

    public static final BlockEntityType<BejeweledLockBlockEntity> BEJEWELED_LOCK = register(
            "bejeweled_lock",
            FabricBlockEntityTypeBuilder.create(BejeweledLockBlockEntity::new, OmegaBlocks.BEJEWELED_LOCK).build(null));

    public static final BlockEntityType<SlimeObeliskBlockEntity> SLIME_OBELISK = register(
            "slime_obelisk",
            FabricBlockEntityTypeBuilder.create(SlimeObeliskBlockEntity::new, OmegaBlocks.SLIME_OBELISK).build(null));

    public static final BlockEntityType<SwirledMixerBlockEntity> SWIRLED_MIXER = register(
            "swirled_mixer",
            FabricBlockEntityTypeBuilder.create(SwirledMixerBlockEntity::new, OmegaBlocks.SWIRLED_MIXER).build(null));

    public static final BlockEntityType<GalaxyFurnaceBlockEntity> GALAXY_FURNACE = register(
            "galaxy_furnace",
            FabricBlockEntityTypeBuilder.create(GalaxyFurnaceBlockEntity::new, OmegaBlocks.GALAXY_FURNACE).build(null));

    public static final BlockEntityType<MirrorBlock.MirrorBlockEntity> MIRROR_BLOCK = register(
            "mirror_block",
            FabricBlockEntityTypeBuilder.create(MirrorBlock.MirrorBlockEntity::new, OmegaBlocks.MIRROR_BLOCK).build(null));

    public static final BlockEntityType<ActivatedStarlightCornerBlock.Entity> STARLIGHT_CORNER = register(
            "starlight_corner",
            FabricBlockEntityTypeBuilder.create(ActivatedStarlightCornerBlock.Entity::new, OmegaBlocks.ACTIVATED_STARLIGHT_CORNER).build(null));


    private static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType<T> entity) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, IntoTheOmega.id(name), entity);
    }

    private OmegaBlockEntities() {
        // NO-OP
    }
}
