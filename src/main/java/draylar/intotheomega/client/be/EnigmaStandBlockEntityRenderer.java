package draylar.intotheomega.client.be;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.entity.block.EnigmaStandBlockEntity;
import draylar.intotheomega.registry.OmegaItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;

public class EnigmaStandBlockEntityRenderer extends BlockEntityRenderer<EnigmaStandBlockEntity> {

    public EnigmaStandBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(EnigmaStandBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        float lerped = MathHelper.lerp(tickDelta, entity.getActivationTicks() - 1, entity.getActivationTicks());

        matrices.push();
        matrices.translate(0.5, .05f, 0.5);

        // BASE PEDESTAL -------------------------------------
        matrices.push();
        matrices.scale(1.25f, 0.2f, 1.25f);

        // remove after ticks start
        if(entity.getActivationTicks() > 0) {
            float v = 1 - (Math.min(20, lerped) / 20f);
            matrices.scale(v, v, v);
        }

        MinecraftClient.getInstance().getItemRenderer().renderItem(
                new ItemStack(Items.END_STONE_BRICKS),
                ModelTransformation.Mode.FIXED,
                light,
                overlay,
                matrices,
                vertexConsumers
        );

        matrices.pop();


        // OBSIDIAN BOTTOM -------------------------------------
        matrices.push();
        matrices.translate(0, .3f, 0);

        // remove after ticks start
        if(entity.getActivationTicks() > 20) {
            float v = 1 - (Math.min(20, lerped - 20) / 20f);
            matrices.scale(v, v, v);
        }

        MinecraftClient.getInstance().getItemRenderer().renderItem(
                new ItemStack(Registry.ITEM.get(IntoTheOmega.id("chiseled_obsidian"))),
                ModelTransformation.Mode.FIXED,
                light,
                overlay,
                matrices,
                vertexConsumers
        );

        matrices.pop();

        // SWORD BOTTOM -------------------------------------
        matrices.translate(0, 1.2f, 0);
        matrices.scale(1.25f, 1.25f, 1.25f);

        // float after ticks start
        if(entity.getActivationTicks() > 60) {
            float v = (Math.min(20, lerped - 60) / 20f);
            matrices.translate(0, -2 * v + 1, 0);
        } else if(entity.getActivationTicks() > 40) {
            float v = (Math.min(20, lerped - 40) / 20f);
            matrices.translate(0, v, 0);
        }

        matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(135));

        MinecraftClient.getInstance().getItemRenderer().renderItem(
                new ItemStack(OmegaItems.DARK_ENIGMA),
                ModelTransformation.Mode.FIXED,
                light,
                overlay,
                matrices,
                vertexConsumers
        );



        matrices.pop();
    }
}
