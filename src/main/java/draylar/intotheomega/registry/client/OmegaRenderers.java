package draylar.intotheomega.registry.client;

import draylar.intotheomega.client.be.*;
import draylar.intotheomega.client.entity.renderer.*;
import draylar.intotheomega.entity.VoidFloaterEntity;
import draylar.intotheomega.registry.OmegaBlockEntities;
import draylar.intotheomega.registry.OmegaEntities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EmptyEntityRenderer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;

@Environment(EnvType.CLIENT)
public class OmegaRenderers {

    public static void init() {
        EntityRendererRegistry.register(OmegaEntities.CHORUS_COW, ChorusCowEntityRenderer::new);
        EntityRendererRegistry.register(OmegaEntities.INANIS, InanisEntityRenderer::new);
        EntityRendererRegistry.register(OmegaEntities.DUAL_INANIS, DualInanisEntityRenderer::new);
        EntityRendererRegistry.register(OmegaEntities.VOID_MATRIX, VoidMatrixEntityRenderer::new);
        EntityRendererRegistry.register(OmegaEntities.ENIGMA_KING, EnigmaKingEntityRenderer::new);
        EntityRendererRegistry.register(OmegaEntities.MATRITE, MatriteEntityRenderer::new);
        EntityRendererRegistry.register(OmegaEntities.MATRIX_BOMB, MatrixBombEntityRenderer::new);
        EntityRendererRegistry.register(OmegaEntities.OBSIDIAN_THORN, ObsidianThornEntityRenderer::new);
        EntityRendererRegistry.register(OmegaEntities.OMEGA_SLIME_EMPEROR, OmegaSlimeEmperorRenderer::new);
        EntityRendererRegistry.register(OmegaEntities.OMEGA_SLIME_MOUNT, OmegaSlimeMountRenderer::new);
        EntityRendererRegistry.register(OmegaEntities.OMEGA_SLIME, OmegaSlimeRenderer::new);
        EntityRendererRegistry.register(OmegaEntities.FROSTED_EYE, FrostedEyeEntityRenderer::new);
        EntityRendererRegistry.register(OmegaEntities.VIOLET_UNION_BLADE, VioletUnionBladeRenderer::new);
        EntityRendererRegistry.register(OmegaEntities.ABYSSAL_RIFT, AbyssalRiftEntityRenderer::new);
        EntityRendererRegistry.register(OmegaEntities.VOID_MATRIX_BEAM, VoidMatrixBeamRenderer::new);
        EntityRendererRegistry.register(OmegaEntities.VOID_WALKER, VoidWalkerRenderer::new);
        EntityRendererRegistry.register(OmegaEntities.FROSTED_ENDERMAN, FrostedEndermanEntityRenderer::new);
        EntityRendererRegistry.register(OmegaEntities.END_SLIME, EndSlimeRenderer::new);
        EntityRendererRegistry.register(OmegaEntities.VOID_BEETLE, VoidBeetleRenderer::new);
        EntityRendererRegistry.register(OmegaEntities.ABYSS_GLOBE, AbyssGlobeRenderer::new);
        EntityRendererRegistry.register(OmegaEntities.VOID_FLOATER, EmptyEntityRenderer::new);
        EntityRendererRegistry.register(OmegaEntities.LEAF_MONSTER, LeafMonsterRenderer::new);
        EntityRendererRegistry.register(OmegaEntities.LEVITATION_PROJECTILE, EmptyEntityRenderer::new);
        EntityRendererRegistry.register(OmegaEntities.SWIRL_GRENADE, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(OmegaEntities.SLIMEFALL, SlimefallEntityRenderer::new);
        EntityRendererRegistry.register(OmegaEntities.STARFALL_PROJECTILE, StarfallEntityRenderer::new);
        EntityRendererRegistry.register(OmegaEntities.ENTWINED, EntwinedEntityRenderer::new);
        EntityRendererRegistry.register(OmegaEntities.ABYSSAL_KNIGHT, AbyssalKnightEntityRenderer::new);
        EntityRendererRegistry.register(OmegaEntities.HOMING_STARLIT_PROJECTILE, EmptyEntityRenderer::new);
        EntityRendererRegistry.register(OmegaEntities.STAR_WALKER, StarWalkerEntityRenderer::new);
        EntityRendererRegistry.register(OmegaEntities.ORIGIN_NOVA, EmptyEntityRenderer::new);

        BlockEntityRendererRegistry.INSTANCE.register(OmegaBlockEntities.VOID_MATRIX_SPAWN_BLOCK, VoidMatrixSpawnBlockEntityRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(OmegaBlockEntities.PHASE_PAD, PhasePadBlockEntityRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(OmegaBlockEntities.ENIGMA_STAND, EnigmaStandBlockEntityRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(OmegaBlockEntities.INVISIBLE_DUNGEON_BRICK, InvisibleDungeonBrickBlockEntityRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(OmegaBlockEntities.ABYSS_CHAIN, AbyssChainBlockEntityRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(OmegaBlockEntities.BEJEWELED_LOCK, BejeweledLockBlockEntityRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(OmegaBlockEntities.SWIRLED_MIXER, SwirledMixerRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(OmegaBlockEntities.GALAXY_FURNACE, GalaxyFurnaceRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(OmegaBlockEntities.STARLIGHT_CORNER, StarlightCornerRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(OmegaBlockEntities.OBSIDIAN_PEDESTAL, ObsidianPedestalRenderer::new);
    }

    private OmegaRenderers() {
        // NO-OP
    }
}
