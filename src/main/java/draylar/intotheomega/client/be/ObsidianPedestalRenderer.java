package draylar.intotheomega.client.be;

import draylar.intotheomega.entity.block.ObsidianPedestalBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;

public class ObsidianPedestalRenderer implements BlockEntityRenderer<ObsidianPedestalBlockEntity> {

    private final TextRenderer textRenderer;

    public ObsidianPedestalRenderer(BlockEntityRendererFactory.Context context) {
        textRenderer = context.getTextRenderer();
    }

    @Override
    public void render(ObsidianPedestalBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if(MinecraftClient.getInstance().player.getPos().distanceTo(new Vec3d(entity.getPos().getX(), entity.getPos().getY(), entity.getPos().getZ())) <= 16) {
            matrices.push();
            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180));
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180));
            matrices.translate(-0.5, -2, 1);
            matrices.scale(0.02f, 0.02f, 0.02f);
            DrawableHelper.drawCenteredText(matrices, textRenderer, new LiteralText("The table reads...").formatted(Formatting.LIGHT_PURPLE), 0, -128, 0xffffffff);
            DrawableHelper.drawCenteredText(matrices, textRenderer, new LiteralText("Venture into the spiral to reach twilight.").formatted(Formatting.GRAY), 0, -80, 0xffffffff);
            DrawableHelper.drawCenteredText(matrices, textRenderer, new LiteralText("Touch the alter to battle with starlight.").formatted(Formatting.GRAY), 0, -64, 0xffffffff);
            DrawableHelper.drawCenteredText(matrices, textRenderer, new LiteralText("Conquest to discover the truth.").formatted(Formatting.GRAY), 0, -48, 0xffffffff);
            matrices.pop();
        }
    }
}
