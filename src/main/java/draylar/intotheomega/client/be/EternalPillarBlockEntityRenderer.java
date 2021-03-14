package draylar.intotheomega.client.be;

import draylar.intotheomega.entity.block.EternalPillarBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class EternalPillarBlockEntityRenderer extends BlockEntityRenderer<EternalPillarBlockEntity> {

    public static final ItemStack OBSIDIAN = new ItemStack(Items.OBSIDIAN);

    public EternalPillarBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(EternalPillarBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        for(int x = -20; x <= 20; x++) {
            for(int z = -20; z <= 20; z++) {
                for(int y = 0; y < 50; y++) {
                    matrices.push();
                    matrices.translate(x, y, z);
                    MinecraftClient.getInstance().getItemRenderer().renderItem(
                            OBSIDIAN,
                            ModelTransformation.Mode.FIXED,
                            light,
                            overlay,
                            matrices,
                            vertexConsumers
                    );
                    matrices.pop();
                }
            }
        }
    }
}
