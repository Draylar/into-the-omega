package draylar.intotheomega.client.be;

import draylar.intotheomega.api.TextureConstants;
import draylar.intotheomega.api.client.ShapeRendering;
import draylar.intotheomega.api.client.VertexWrapper;
import draylar.intotheomega.entity.block.MatrixDungeonFloorBlockEntity;
import draylar.intotheomega.mixin.access.RenderLayerAccessor;
import draylar.intotheomega.registry.client.OmegaShaders;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.function.BiFunction;

public class MatrixDungeonFloorRenderer implements BlockEntityRenderer<MatrixDungeonFloorBlockEntity> {

    private static final BiFunction<Identifier, Boolean, RenderLayer> MATRIX_DUNGEON_FLOOR = Util.memoize((texture, affectsOutline) -> {
        RenderLayer.MultiPhaseParameters multiPhaseParameters = RenderLayer.MultiPhaseParameters.builder()
                .shader(new RenderPhase.Shader(OmegaShaders.MATRIX_DUNGEON_FLOOR::getShader))
                .texture(new RenderPhase.Texture(texture, false, false))
                .transparency(RenderPhase.TRANSLUCENT_TRANSPARENCY)
                .cull(RenderPhase.DISABLE_CULLING)
                .lightmap(RenderPhase.ENABLE_LIGHTMAP)
                .overlay(RenderPhase.ENABLE_OVERLAY_COLOR)
                .build(affectsOutline);

        return RenderLayerAccessor.of("matrix_dungeon_floor", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS, 256, true, true, multiPhaseParameters);
    });

    public MatrixDungeonFloorRenderer(BlockEntityRendererFactory.Context context) {

    }

    @Override
    public void render(MatrixDungeonFloorBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        VertexWrapper buffer = VertexWrapper.wrap(vertexConsumers.getBuffer(MATRIX_DUNGEON_FLOOR.apply(new Identifier("intotheomega", "textures/block/matrix_bricks.png"), false)), VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL);

        matrices.push();
        matrices.translate(0.5f, 0.001f, 0.5f);
        matrices.scale(75f, 1f, 75f);
        ShapeRendering.quad(LightmapTextureManager.MAX_LIGHT_COORDINATE, 1.0f, 1.0f, 1.0f, matrices.peek().getNormalMatrix(), matrices.peek().getPositionMatrix(), buffer);
        matrices.pop();
    }

    @Override
    public boolean rendersOutsideBoundingBox(MatrixDungeonFloorBlockEntity blockEntity) {
        return true;
    }

    @Override
    public int getRenderDistance() {
        return 256;
    }
}
