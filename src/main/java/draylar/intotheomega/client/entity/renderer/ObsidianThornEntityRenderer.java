package draylar.intotheomega.client.entity.renderer;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.entity.ObsidianThornEntity;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;

public class ObsidianThornEntityRenderer extends EntityRenderer<ObsidianThornEntity> {

    public static final Identifier SIGIL_TEXTURE = IntoTheOmega.id("textures/obsidian_thorn_sigil.png");

    public ObsidianThornEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(ObsidianThornEntity entity) {
        return null;
    }

    @Override
    public void render(ObsidianThornEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayers.getBlockLayer(Blocks.OBSIDIAN.getDefaultState()));

        // obsidian spike
        matrices.push();
        if(entity.getProgress() > 30) { // entity.getProgress() > 30
            float scale = Math.min(1, MathHelper.lerp(tickDelta, entity.age - 1 - 30, entity.age - 30) / 10f);
            matrices.scale(scale, scale, scale);

            // first layer
            entity.getRenderBlocks().forEach(pos -> {
                matrices.push();
                matrices.translate(pos.getX(), pos.getY(), pos.getZ());
                render(buffer, matrices, entity.world);
                matrices.pop();
            });
        }
        matrices.pop();

        // sigil
        matrices.push();
        MinecraftClient.getInstance().getTextureManager().bindTexture(SIGIL_TEXTURE);
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90));
        matrices.scale(0.1f, 0.1f, 0.1f);
        matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.lerp(tickDelta, entity.age - 1, entity.age)));
        float scale = Math.min(1.5f, MathHelper.lerp(tickDelta, entity.age - 1, entity.age) / 30f);
        matrices.scale(scale, scale, scale);
        matrices.translate(-32, -32, 0);
        DrawableHelper.drawTexture(matrices, 0, 0, 0, 0, 0, 64, 64, 64, 64);
        matrices.pop();
    }

    public void render(VertexConsumer buffer, MatrixStack matrices, World world) {
        MinecraftClient.getInstance().getBlockRenderManager().renderBlock(
                Blocks.OBSIDIAN.getDefaultState(),
                BlockPos.ORIGIN,
                world,
                matrices,
                buffer,
                false,
                world.random);
    }
}
