package draylar.intotheomega.client.be;

import draylar.intotheomega.entity.block.VoidMatrixSpawnBlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;

public class VoidMatrixSpawnBlockEntityRenderer implements BlockEntityRenderer<VoidMatrixSpawnBlockEntity> {

    private final BlockEntityRendererFactory.Context context;

    public VoidMatrixSpawnBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        this.context = context;
    }

    @Override
    public void render(VoidMatrixSpawnBlockEntity spawnBlock, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j) {

    }
}
