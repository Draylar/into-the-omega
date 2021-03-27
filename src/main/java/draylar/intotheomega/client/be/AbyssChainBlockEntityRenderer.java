package draylar.intotheomega.client.be;

import draylar.intotheomega.entity.block.AbyssChainBlockEntity;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;

public class AbyssChainBlockEntityRenderer extends BlockEntityRenderer<AbyssChainBlockEntity> {

    public AbyssChainBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(AbyssChainBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        matrices.translate(0, -4, 0);
        matrices.scale(5, 5, 5);
        matrices.translate(-.4, 0, -.4);

        for(int i = 0; i < 25; i++) {
            matrices.translate(0, 1, 0);
            MinecraftClient.getInstance().getBlockRenderManager().renderBlock(
                    Blocks.CHAIN.getDefaultState(),
                    entity.getPos(),
                    entity.getWorld(),
                    matrices,
                    vertexConsumers.getBuffer(RenderLayer.getSolid()),
                    false,
                    entity.getWorld().random
            );
        }

        matrices.pop();
    }

    @Override
    public boolean rendersOutsideBoundingBox(AbyssChainBlockEntity blockEntity) {
        return true;
    }
}
