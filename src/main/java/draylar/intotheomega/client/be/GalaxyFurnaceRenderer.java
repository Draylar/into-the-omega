package draylar.intotheomega.client.be;

import draylar.intotheomega.entity.block.GalaxyFurnaceBlockEntity;
import draylar.intotheomega.registry.OmegaBlocks;
import draylar.intotheomega.registry.client.OmegaRenderLayers;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GalaxyFurnaceRenderer implements BlockEntityRenderer<GalaxyFurnaceBlockEntity> {

    private List<Star> points = null;
    private final List<Vec3d> colors = Arrays.asList(new Vec3d(1.0f, 0.3f, 0.5f), new Vec3d(66 / 255f, 245 / 255f, 203 / 255f));

    public GalaxyFurnaceRenderer(BlockEntityRendererFactory.Context context) {

    }

    @Override
    public void render(GalaxyFurnaceBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        // populate points
        if(points == null) {
            points = new ArrayList<>();

            for (int i = 0; i < 64; i++) {
                // Choose a random position within the sphere
                Random random = entity.getWorld().random;
                Vec3d point = new Vec3d(random.nextDouble(), random.nextDouble(), random.nextDouble());
                Vec3d scale = new Vec3d(0.1f, 0.1f, 0.1f);
                points.add(new Star(point, scale, colors.get(random.nextInt(colors.size()))));
            }
        }

        VertexConsumer stars = vertexConsumers.getBuffer(OmegaRenderLayers.SOLID);
        VertexConsumer stars2 = vertexConsumers.getBuffer(OmegaRenderLayers.SOLID);

        VertexConsumer cube = vertexConsumers.getBuffer(OmegaRenderLayers.getGalaxy(new Identifier("intotheomega", "textures/entity/galaxy_furnace_white.png")));

        MinecraftClient.getInstance().getBlockRenderManager().renderBlock(
                OmegaBlocks.PHASE_PAD.getDefaultState(),
                BlockPos.ORIGIN,
                entity.getWorld(),
                matrices,
                stars,
                false,
                entity.getWorld().random
        );

        matrices.push();

        float alpha = 0.5f;


        // CUBE

        // Opaque
        Matrix4f model;

        // blue
        matrices.push();
        matrices.translate(-0.75f, 0.0f, -0.75f);
        matrices.scale(2.5f, 2.5f, 2.5f);
        model = matrices.peek().getPositionMatrix();
        drawCube(cube, model, alpha, overlay, 0.1f, 0.0f, 0.2f);

        matrices.scale(1.1f, 1.1f, 1.1f);
        drawCube(cube, model, 0.1f, overlay, 0.1f, 0.0f, 0.2f);

        matrices.pop();

        VertexConsumer solid = vertexConsumers.getBuffer(RenderLayer.getSolid());

        // pink
        alpha = 0.5f;

        for (Star star : points) {
            Vec3d pos = star.pos;
            Vec3d color = star.color;
            Vec3d scale = star.scale;

            matrices.push();
            matrices.translate(-0.75f + pos.getX() * 2, 0.0f + pos.getY() * 2.25, -0.75f + pos.getZ() * 2);
            matrices.scale((float) scale.getX(), (float) scale.getY(), (float) scale.getZ());
            model = matrices.peek().getPositionMatrix();
            drawCube(solid, model, alpha, overlay, (float) color.getX(), (float) color.getY(), (float) color.getZ());
            matrices.pop();
        }

        matrices.pop();
    }

    public void drawCube(VertexConsumer solid, Matrix4f model, float alpha, int overlay, float red, float green, float blue) {

        // POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL
        solid.vertex(model, 0.0f, 0.0f, 0.0f).color(red, green, blue, alpha).texture(0, 0).overlay(overlay).light(LightmapTextureManager.pack(15, 15)).normal(1, 0, 0).next();
        solid.vertex(model, 0.0f, 1.0f, 0.0f).color(red, green, blue, alpha).texture(0, 0).overlay(overlay).light(LightmapTextureManager.pack(15, 15)).normal(1, 0, 0).next();
        solid.vertex(model, 1.0f, 1.0f, 0.0f).color(red, green, blue, alpha).texture(0, 0).overlay(overlay).light(LightmapTextureManager.pack(15, 15)).normal(1, 0, 0).next();
        solid.vertex(model, 1.0f, 0.0f, 0.0f).color(red, green, blue, alpha).texture(0, 0).overlay(overlay).light(LightmapTextureManager.pack(15, 15)).normal(1, 0, 0).next();

        // West-facing
        solid.vertex(model, 0.0f, 0.0f, 0.0f).color(red, green, blue, alpha).texture(0, 0).overlay(overlay).light(LightmapTextureManager.pack(15, 15)).normal(0, 0, 1).next();
        solid.vertex(model, 0.0f, 0.0f, 1.0f).color(red, green, blue, alpha).texture(0, 0).overlay(overlay).light(LightmapTextureManager.pack(15, 15)).normal(0, 0, 1).next();
        solid.vertex(model, 0.0f, 1.0f, 1.0f).color(red, green, blue, alpha).texture(0, 0).overlay(overlay).light(LightmapTextureManager.pack(15, 15)).normal(0, 0, 1).next();
        solid.vertex(model, 0.0f, 1.0f, 0.0f).color(red, green, blue, alpha).texture(0, 0).overlay(overlay).light(LightmapTextureManager.pack(15, 15)).normal(0, 0, 1).next();

        // East-facing
        solid.vertex(model, 1.0f, 0.0f, 0.0f).color(red, green, blue, alpha).texture(0, 0).overlay(overlay).light(LightmapTextureManager.pack(15, 15)).normal(1, 0, 0).next();
        solid.vertex(model, 1.0f, 1.0f, 0.0f).color(red, green, blue, alpha).texture(0, 0).overlay(overlay).light(LightmapTextureManager.pack(15, 15)).normal(1, 0, 0).next();
        solid.vertex(model, 1.0f, 1.0f, 1.0f).color(red, green, blue, alpha).texture(0, 0).overlay(overlay).light(LightmapTextureManager.pack(15, 15)).normal(1, 0, 0).next();
        solid.vertex(model, 1.0f, 0.0f, 1.0f).color(red, green, blue, alpha).texture(0, 0).overlay(overlay).light(LightmapTextureManager.pack(15, 15)).normal(1, 0, 0).next();

        // South-facing
        solid.vertex(model, 0.0f, 0.0f, 1.0f).color(red, green, blue, alpha).texture(0, 0).overlay(overlay).light(LightmapTextureManager.pack(15, 15)).normal(0, 0, 0).next();
        solid.vertex(model, 1.0f, 0.0f, 1.0f).color(red, green, blue, alpha).texture(0, 0).overlay(overlay).light(LightmapTextureManager.pack(15, 15)).normal(0, 0, 0).next();
        solid.vertex(model, 1.0f, 1.0f, 1.0f).color(red, green, blue, alpha).texture(0, 0).overlay(overlay).light(LightmapTextureManager.pack(15, 15)).normal(0, 0, 0).next();
        solid.vertex(model, 0.0f, 1.0f, 1.0f).color(red, green, blue, alpha).texture(0, 0).overlay(overlay).light(LightmapTextureManager.pack(15, 15)).normal(0, 0, 0).next();
//
//        if(alpha == 1.0f) {
        solid.vertex(model, 0.0f, 0.0f, 0.0f).color(red, green, blue, alpha).texture(0, 0).overlay(overlay).light(LightmapTextureManager.pack(15, 15)).normal(0, 0, 0).next();
        solid.vertex(model, 0.0f, 0.0f, 1.0f).color(red, green, blue, alpha).texture(0, 0).overlay(overlay).light(LightmapTextureManager.pack(15, 15)).normal(0, 0, 0).next();
        solid.vertex(model, 1.0f, 0.0f, 1.0f).color(red, green, blue, alpha).texture(0, 0).overlay(overlay).light(LightmapTextureManager.pack(15, 15)).normal(0, 0, 0).next();
        solid.vertex(model, 1.0f, 0.0f, 0.0f).color(red, green, blue, alpha).texture(0, 0).overlay(overlay).light(LightmapTextureManager.pack(15, 15)).normal(0, 0, 0).next();
//        }

        solid.vertex(model, 0.0f, 1.0f, 0.0f).color(red, green, blue, alpha).texture(0, 0).overlay(overlay).light(LightmapTextureManager.pack(15, 15)).normal(0, 0, 0).next();
        solid.vertex(model, 0.0f, 1.0f, 1.0f).color(red, green, blue, alpha).texture(0, 0).overlay(overlay).light(LightmapTextureManager.pack(15, 15)).normal(0, 0, 0).next();
        solid.vertex(model, 1.0f, 1.0f, 1.0f).color(red, green, blue, alpha).texture(0, 0).overlay(overlay).light(LightmapTextureManager.pack(15, 15)).normal(0, 0, 0).next();
        solid.vertex(model, 1.0f, 1.0f, 0.0f).color(red, green, blue, alpha).texture(0, 0).overlay(overlay).light(LightmapTextureManager.pack(15, 15)).normal(0, 0, 0).next();
    }

    private static class Star {
        private Vec3d pos;
        private Vec3d scale;
        private Vec3d color;

        public Star(Vec3d pos, Vec3d scale, Vec3d color) {
            this.pos = pos;
            this.scale = scale;
            this.color = color;
        }
    }
}
