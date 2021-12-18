package draylar.intotheomega.client.be;

import draylar.intotheomega.entity.block.PhasePadBlockEntity;
import draylar.intotheomega.registry.OmegaBlocks;
import draylar.intotheomega.registry.client.OmegaRenderLayers;
import draylar.intotheomega.registry.client.OmegaRendering;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;

public class PhasePadBlockEntityRenderer extends BlockEntityRenderer<PhasePadBlockEntity> {

    public PhasePadBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(PhasePadBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();

        VertexConsumer buffer;

        if(entity.isHighlight()) {
            matrices.translate(0.5, 0.5, 0.5);
            matrices.scale(1.2f, 1.2f, 1.2f);
            matrices.translate(-0.5, -0.5, -0.5);
        }

        if(entity.isActivated()) {
            buffer = vertexConsumers.getBuffer(OmegaRenderLayers.SOLID);

            MinecraftClient.getInstance().getBlockRenderManager().renderBlock(
                    OmegaBlocks.PHASE_PAD.getDefaultState(),
                    BlockPos.ORIGIN,
                    entity.getWorld(),
                    matrices,
                    buffer,
                    false,
                    entity.getWorld().random
            );
        }

        matrices.pop();
    }
}
