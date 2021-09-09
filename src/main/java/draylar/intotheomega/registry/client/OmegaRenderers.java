package draylar.intotheomega.registry.client;

import draylar.intotheomega.client.be.*;
import draylar.intotheomega.client.entity.renderer.*;
import draylar.intotheomega.registry.OmegaBlockEntities;
import draylar.intotheomega.registry.OmegaEntities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class OmegaRenderers {

    public static void init() {
        EntityRendererRegistry.INSTANCE.register(OmegaEntities.CHORUS_COW, (dispatcher, context) -> new ChorusCowEntityRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(OmegaEntities.INANIS, (dispatcher, context) -> new InanisEntityRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(OmegaEntities.DUAL_INANIS, (dispatcher, context) -> new DualInanisEntityRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(OmegaEntities.VOID_MATRIX, (dispatcher, context) -> new VoidMatrixEntityRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(OmegaEntities.ENIGMA_KING, (dispatcher, context) -> new EnigmaKingEntityRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(OmegaEntities.MATRITE, (dispatcher, context) -> new MatriteEntityRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(OmegaEntities.MATRIX_BOMB, (dispatcher, context) -> new MatrixBombEntityRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(OmegaEntities.OBSIDIAN_THORN, (dispatcher, context) -> new ObsidianThornEntityRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(OmegaEntities.OMEGA_SLIME_EMPEROR, (dispatcher, context) -> new OmegaSlimeEmperorRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(OmegaEntities.OMEGA_SLIME_MOUNT, (dispatcher, context) -> new OmegaSlimeMountRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(OmegaEntities.OMEGA_SLIME, (dispatcher, context) -> new OmegaSlimeRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(OmegaEntities.FROSTED_EYE, (dispatcher, context) -> new FrostedEyeEntityRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(OmegaEntities.VIOLET_UNION_BLADE, (dispatcher, context) -> new VioletUnionBladeRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(OmegaEntities.ABYSSAL_RIFT, (dispatcher, context) -> new AbyssalRiftEntityRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(OmegaEntities.VOID_MATRIX_BEAM, (dispatcher, context) -> new VoidMatrixBeamRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(OmegaEntities.VOID_WALKER, (dispatcher, context) -> new VoidWalkerRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(OmegaEntities.FROSTED_ENDERMAN, (dispatcher, context) -> new FrostedEndermanEntityRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(OmegaEntities.END_SLIME, (dispatcher, context) -> new EndSlimeRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(OmegaEntities.VOID_BEETLE, (dispatcher, context) -> new VoidBeetleRenderer(dispatcher));

        BlockEntityRendererRegistry.INSTANCE.register(OmegaBlockEntities.VOID_MATRIX_SPAWN_BLOCK, VoidMatrixSpawnBlockEntityRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(OmegaBlockEntities.PHASE_PAD, PhasePadBlockEntityRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(OmegaBlockEntities.ENIGMA_STAND, EnigmaStandBlockEntityRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(OmegaBlockEntities.INVISIBLE_DUNGEON_BRICK, InvisibleDungeonBrickBlockEntityRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(OmegaBlockEntities.ABYSS_CHAIN, AbyssChainBlockEntityRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(OmegaBlockEntities.BEJEWELED_LOCK, BejeweledLockBlockEntityRenderer::new);
    }

    private OmegaRenderers() {
        // NO-OP
    }
}
