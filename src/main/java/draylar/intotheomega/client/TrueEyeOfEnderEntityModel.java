// Made with Blockbench 3.6.6
// Exported for Minecraft version 1.12.2 or 1.15.2 (same format for both) for entity models animated with GeckoLib
// Paste this class into your mod and follow the documentation for GeckoLib to use animations. You can find the documentation here: https://github.com/bernie-g/geckolib
// Blockbench plugin created by Gecko
package draylar.intotheomega.client;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.entity.TrueEyeOfEnderEntity;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.animation.model.AnimatedEntityModel;
import software.bernie.geckolib.animation.render.AnimatedModelRenderer;

public class TrueEyeOfEnderEntityModel extends AnimatedEntityModel<TrueEyeOfEnderEntity> {

	private final AnimatedModelRenderer bone;
	private final AnimatedModelRenderer back;
	private final AnimatedModelRenderer bone6;
	private final AnimatedModelRenderer bone4;
	private final AnimatedModelRenderer bone2;
	private final AnimatedModelRenderer bone3;
	private final AnimatedModelRenderer bone5;

	public TrueEyeOfEnderEntityModel() {
		textureWidth = 64;
		textureHeight = 64;
		bone = new AnimatedModelRenderer(this);
		bone.setRotationPoint(0.0F, 18.5F, 0.0F);
		bone.setTextureOffset(0, 0).addBox(-5.5F, -5.5F, -5.5F, 11.0F, 11.0F, 11.0F, 0.0F, false);
		bone.setModelRendererName("bone");
		this.registerModelRenderer(bone);

		back = new AnimatedModelRenderer(this);
		back.setRotationPoint(5.0F, 0.0F, 0.0F);
		bone.addChild(back);

		back.setModelRendererName("back");
		this.registerModelRenderer(back);

		bone6 = new AnimatedModelRenderer(this);
		bone6.setRotationPoint(-0.75F, -0.5F, -0.25F);
		back.addChild(bone6);
		bone6.setTextureOffset(0, 22).addBox(-4.0F, -1.0F, 9.5F, 1.0F, 1.0F, 10.0F, 0.0F, false);
		bone6.setTextureOffset(0, 24).addBox(-3.0F, -1.0F, 5.5F, 1.0F, 1.0F, 8.0F, 0.0F, false);
		bone6.setTextureOffset(15, 30).addBox(-4.0F, -1.0F, 7.5F, 1.0F, 2.0F, 2.0F, 0.0F, false);
		bone6.setTextureOffset(14, 26).addBox(-5.0F, -1.0F, 3.5F, 2.0F, 2.0F, 4.0F, 0.0F, false);
		bone6.setTextureOffset(7, 30).addBox(-4.0F, -2.0F, 3.5F, 1.0F, 1.0F, 3.0F, 0.0F, false);
		bone6.setModelRendererName("bone6");
		this.registerModelRenderer(bone6);

		bone4 = new AnimatedModelRenderer(this);
		bone4.setRotationPoint(-0.75F, 3.5F, -2.25F);
		back.addChild(bone4);
		bone4.setTextureOffset(0, 26).addBox(-2.0F, 0.0F, 8.5F, 1.0F, 1.0F, 7.0F, 0.0F, false);
		bone4.setTextureOffset(24, 26).addBox(-3.0F, 0.0F, 5.5F, 1.0F, 1.0F, 4.0F, 0.0F, false);
		bone4.setTextureOffset(0, 28).addBox(-2.0F, -1.0F, 5.5F, 1.0F, 2.0F, 3.0F, 0.0F, false);
		bone4.setModelRendererName("bone4");
		this.registerModelRenderer(bone4);

		bone2 = new AnimatedModelRenderer(this);
		bone2.setRotationPoint(-0.75F, -3.5F, -3.25F);
		back.addChild(bone2);
		bone2.setTextureOffset(8, 28).addBox(-1.0F, -1.0F, 9.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		bone2.setTextureOffset(21, 30).addBox(-2.0F, -1.0F, 9.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		bone2.setTextureOffset(0, 0).addBox(-2.0F, -1.0F, 6.5F, 2.0F, 2.0F, 3.0F, 0.0F, false);
		bone2.setTextureOffset(18, 24).addBox(-2.0F, 0.0F, 10.5F, 1.0F, 1.0F, 7.0F, 0.0F, false);
		bone2.setModelRendererName("bone2");
		this.registerModelRenderer(bone2);

		bone3 = new AnimatedModelRenderer(this);
		bone3.setRotationPoint(-0.75F, -3.5F, 1.75F);
		back.addChild(bone3);
		bone3.setTextureOffset(0, 9).addBox(-7.0F, 0.0F, 4.5F, 1.0F, 1.0F, 4.0F, 0.0F, false);
		bone3.setTextureOffset(26, 28).addBox(-7.0F, -1.0F, 1.5F, 1.0F, 2.0F, 3.0F, 0.0F, false);
		bone3.setModelRendererName("bone3");
		this.registerModelRenderer(bone3);

		bone5 = new AnimatedModelRenderer(this);
		bone5.setRotationPoint(-0.75F, 2.5F, 1.75F);
		back.addChild(bone5);
		bone5.setTextureOffset(22, 22).addBox(-7.0F, 0.0F, 4.5F, 1.0F, 1.0F, 9.0F, 0.0F, false);
		bone5.setTextureOffset(0, 4).addBox(-7.0F, 0.0F, 1.5F, 2.0F, 1.0F, 3.0F, 0.0F, false);
		bone5.setTextureOffset(0, 7).addBox(-7.0F, -1.0F, 3.5F, 1.0F, 1.0F, 4.0F, 0.0F, false);
		bone5.setModelRendererName("bone5");
		this.registerModelRenderer(bone5);

		this.rootBones.add(bone);
	}

	@Override
	public void render(MatrixStack matrixStack, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		super.render(matrixStack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}

	@Override
	public void setAngles(TrueEyeOfEnderEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		super.setAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		System.out.println(headPitch);
		bone.pitch = headPitch;
	}

	@Override
	public Identifier getAnimationFileLocation() {
		return IntoTheOmega.id("animations/true_eoe.animation.json");
	}
}