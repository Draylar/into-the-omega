package draylar.intotheomega.client;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.api.StatueRegistry;
import draylar.intotheomega.entity.StatueBlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;

public class StatueBlockEntityRenderer extends BlockEntityRenderer<StatueBlockEntity> {

    public static final Identifier TEXTURE = IntoTheOmega.id("textures/yeet.png");

    public StatueBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(StatueBlockEntity entity, float tickDelta, MatrixStack stack, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        StatueRegistry.StatueData data = entity.getData();
        EntityType<?> type = data.getType();
        EntityModel<?> model = entity.createModel();
        float scale = 1 / Math.max(type.getDimensions().width, type.getDimensions().height);

        stack.push();

        stack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(180)); // undo upside-down
        stack.translate(0.5, -data.getVerticalOffset(), -0.5);
        stack.scale(scale, scale, scale);
        model.render(stack, vertexConsumers.getBuffer(RenderLayer.getEntitySolid(IntoTheOmega.id("textures/yeet.png"))), light, overlay, 1f, 1f, 1f, 1f);

        stack.pop();
    }
}
