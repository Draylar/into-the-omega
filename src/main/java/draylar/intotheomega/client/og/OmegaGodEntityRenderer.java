package draylar.intotheomega.client.og;

import draylar.intotheomega.entity.og.OmegaGodEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class OmegaGodEntityRenderer extends MobEntityRenderer<OmegaGodEntity, OmegaGodEntityModel> {

    public OmegaGodEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new OmegaGodEntityModel(), 0);
    }

    @Override
    public void render(OmegaGodEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        matrixStack.scale(6, 6, 6);
        matrixStack.translate(0, .25, 0);

        MinecraftClient.getInstance().getItemRenderer().renderItem(
                new ItemStack(Items.DIAMOND_BLOCK),
                ModelTransformation.Mode.FIXED,
                LightmapTextureManager.pack(15, 15),
                5,
                matrixStack,
                vertexConsumerProvider
        );

        matrixStack.pop();
    }

    @Override
    public Identifier getTexture(OmegaGodEntity entity) {
        return null;
    }
}
