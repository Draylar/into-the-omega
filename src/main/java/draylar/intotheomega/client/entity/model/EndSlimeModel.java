package draylar.intotheomega.client.entity.model;

import draylar.intotheomega.enchantment.EndSlimeEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class EndSlimeModel extends EntityModel<EndSlimeEntity> {

	private final ModelPart bb_main;

	public EndSlimeModel() {
		textureWidth = 128;
		textureHeight = 128;

		bb_main = new ModelPart(this);
		bb_main.setPivot(0.0F, 24.0F, 0.0F);
		bb_main.setTextureOffset(0, 0).addCuboid(-12.0F, -24.0F, -12.0F, 24.0F, 24.0F, 24.0F, 0.0F, false);
		bb_main.setTextureOffset(0, 48).addCuboid(-8.0F, -20.0F, -8.0F, 16.0F, 16.0F, 16.0F, 0.0F, false);
		bb_main.setTextureOffset(0, 12).addCuboid(-9.0F, -19.0F, 3.0F, 2.0F, 6.0F, 6.0F, 0.0F, false);
		bb_main.setTextureOffset(0, 0).addCuboid(-9.0F, -19.0F, -9.0F, 2.0F, 6.0F, 6.0F, 0.0F, false);
		bb_main.setTextureOffset(10, 0).addCuboid(-9.0F, -9.0F, -5.0F, 2.0F, 3.0F, 3.0F, 0.0F, false);
	}

	@Override
	public void setAngles(EndSlimeEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		bb_main.render(matrices, vertices, light, overlay);
	}
}