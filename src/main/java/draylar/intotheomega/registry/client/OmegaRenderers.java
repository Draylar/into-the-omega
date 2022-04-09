package draylar.intotheomega.registry.client;

import draylar.intotheomega.client.be.*;
import draylar.intotheomega.client.entity.renderer.*;
import draylar.intotheomega.entity.VoidFloaterEntity;
import draylar.intotheomega.registry.OmegaBlockEntities;
import draylar.intotheomega.registry.OmegaEntities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EmptyEntityRenderer;

@Environment(EnvType.CLIENT)
public class OmegaRenderers {

    public static void init() {
        EntityRendererRegistry.INSTANCE.register(OmegaEntities.CHORUS_COW, ChorusCowEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(OmegaEntities.INANIS, InanisEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(OmegaEntities.DUAL_INANIS, DualInanisEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(OmegaEntities.VOID_MATRIX, VoidMatrixEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(OmegaEntities.ENIGMA_KING, EnigmaKingEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(OmegaEntities.MATRITE, MatriteEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(OmegaEntities.MATRIX_BOMB, MatrixBombEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(OmegaEntities.OBSIDIAN_THORN, ObsidianThornEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(OmegaEntities.OMEGA_SLIME_EMPEROR, OmegaSlimeEmperorRenderer::new);
        EntityRendererRegistry.INSTANCE.register(OmegaEntities.OMEGA_SLIME_MOUNT, OmegaSlimeMountRenderer::new);
        EntityRendererRegistry.INSTANCE.register(OmegaEntities.OMEGA_SLIME, OmegaSlimeRenderer::new);
        EntityRendererRegistry.INSTANCE.register(OmegaEntities.FROSTED_EYE, FrostedEyeEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(OmegaEntities.VIOLET_UNION_BLADE, VioletUnionBladeRenderer::new);
        EntityRendererRegistry.INSTANCE.register(OmegaEntities.ABYSSAL_RIFT, AbyssalRiftEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(OmegaEntities.VOID_MATRIX_BEAM, VoidMatrixBeamRenderer::new);
        EntityRendererRegistry.INSTANCE.register(OmegaEntities.VOID_WALKER, VoidWalkerRenderer::new);
        EntityRendererRegistry.INSTANCE.register(OmegaEntities.FROSTED_ENDERMAN, FrostedEndermanEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(OmegaEntities.END_SLIME, EndSlimeRenderer::new);
        EntityRendererRegistry.INSTANCE.register(OmegaEntities.VOID_BEETLE, VoidBeetleRenderer::new);
        EntityRendererRegistry.INSTANCE.register(OmegaEntities.ABYSS_GLOBE, AbyssGlobeRenderer::new);
        EntityRendererRegistry.INSTANCE.register(OmegaEntities.VOID_FLOATER, EmptyEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(OmegaEntities.LEAF_MONSTER, LeafMonsterRenderer::new);

        BlockEntityRendererRegistry.INSTANCE.register(OmegaBlockEntities.VOID_MATRIX_SPAWN_BLOCK, VoidMatrixSpawnBlockEntityRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(OmegaBlockEntities.PHASE_PAD, PhasePadBlockEntityRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(OmegaBlockEntities.ENIGMA_STAND, EnigmaStandBlockEntityRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(OmegaBlockEntities.INVISIBLE_DUNGEON_BRICK, InvisibleDungeonBrickBlockEntityRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(OmegaBlockEntities.ABYSS_CHAIN, AbyssChainBlockEntityRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(OmegaBlockEntities.BEJEWELED_LOCK, BejeweledLockBlockEntityRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(OmegaBlockEntities.SWIRLED_MIXER, SwirledMixerRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(OmegaBlockEntities.GALAXY_FURNACE, GalaxyFurnaceRenderer::new);
    }

    private OmegaRenderers() {
        // NO-OP
    }
}
