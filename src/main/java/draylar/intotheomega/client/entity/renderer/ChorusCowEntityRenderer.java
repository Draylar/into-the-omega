package draylar.intotheomega.client.entity.renderer;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.client.entity.model.ChorusCowEntityModel;
import draylar.intotheomega.entity.ChorusCowEntity;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChorusPlantBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3f;

public class ChorusCowEntityRenderer extends MobEntityRenderer<ChorusCowEntity, ChorusCowEntityModel> {

    private static final Identifier TEXTURE = IntoTheOmega.id("textures/entity/chorus_cow.png");

    public ChorusCowEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new ChorusCowEntityModel(context.getPart(EntityModelLayers.COW)), 0.7F);
    }

    @Override
    public void render(ChorusCowEntity cow, float f, float g, MatrixStack stack, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(cow, f, g, stack, vertexConsumerProvider, i);
        stack.push();

        if(!cow.isSheared()) {
            MinecraftClient client = MinecraftClient.getInstance();

            stack.multiply(Vec3f.NEGATIVE_Y.getDegreesQuaternion(cow.bodyYaw));
            stack.scale(.4f, .4f, .4f);

            stack.translate(-.5, 3.25, -.5);
            client.getBlockRenderManager().renderBlock(
                    Blocks.CHORUS_PLANT.getDefaultState().with(ChorusPlantBlock.UP, true).with(ChorusPlantBlock.DOWN, true),
                    BlockPos.ORIGIN,
                    cow.world,
                    stack,
                    vertexConsumerProvider.getBuffer(RenderLayers.getBlockLayer(Blocks.CHORUS_PLANT.getDefaultState())),
                    false,
                    cow.world.random
            );

            stack.translate(0, 1, 0);
            client.getBlockRenderManager().renderBlock(
                    Blocks.CHORUS_PLANT.getDefaultState().with(ChorusPlantBlock.UP, true).with(ChorusPlantBlock.DOWN, true).with(ChorusPlantBlock.EAST, true),
                    BlockPos.ORIGIN,
                    cow.world,
                    stack,
                    vertexConsumerProvider.getBuffer(RenderLayers.getBlockLayer(Blocks.CHORUS_PLANT.getDefaultState())),
                    false,
                    cow.world.random
            );

            stack.translate(1, 0, 0);
            client.getBlockRenderManager().renderBlock(
                    Blocks.CHORUS_PLANT.getDefaultState().with(ChorusPlantBlock.WEST, true).with(ChorusPlantBlock.UP, true),
                    BlockPos.ORIGIN,
                    cow.world,
                    stack,
                    vertexConsumerProvider.getBuffer(RenderLayers.getBlockLayer(Blocks.CHORUS_PLANT.getDefaultState())),
                    false,
                    cow.world.random
            );

            stack.translate(0, 1, 0);
            client.getBlockRenderManager().renderBlock(
                    Blocks.CHORUS_FLOWER.getDefaultState(),
                    BlockPos.ORIGIN,
                    cow.world,
                    stack,
                    vertexConsumerProvider.getBuffer(RenderLayers.getBlockLayer(Blocks.CHORUS_FLOWER.getDefaultState())),
                    false,
                    cow.world.random
            );

            stack.pop();

            stack.push();
            stack.multiply(Vec3f.NEGATIVE_Y.getDegreesQuaternion(cow.bodyYaw));
            stack.scale(.4f, .4f, .4f);

            stack.translate(-1.9, 2.25, -1.1);
            client.getBlockRenderManager().renderBlock(
                    Blocks.CHORUS_PLANT.getDefaultState().with(ChorusPlantBlock.EAST, true).with(ChorusPlantBlock.UP, true),
                    BlockPos.ORIGIN,
                    cow.world,
                    stack,
                    vertexConsumerProvider.getBuffer(RenderLayers.getBlockLayer(Blocks.CHORUS_PLANT.getDefaultState())),
                    false,
                    cow.world.random
            );

            stack.translate(0, 1, 0);
            client.getBlockRenderManager().renderBlock(
                    Blocks.CHORUS_PLANT.getDefaultState().with(ChorusPlantBlock.UP, true).with(ChorusPlantBlock.DOWN, true),
                    BlockPos.ORIGIN,
                    cow.world,
                    stack,
                    vertexConsumerProvider.getBuffer(RenderLayers.getBlockLayer(Blocks.CHORUS_PLANT.getDefaultState())),
                    false,
                    cow.world.random
            );

            stack.pop();


            stack.push();
            stack.multiply(Vec3f.NEGATIVE_Y.getDegreesQuaternion(cow.bodyYaw));
            stack.scale(.4f, .4f, .4f);

            stack.translate(.9, 2.25, 0);
            client.getBlockRenderManager().renderBlock(
                    Blocks.CHORUS_PLANT.getDefaultState().with(ChorusPlantBlock.WEST, true).with(ChorusPlantBlock.SOUTH, true),
                    BlockPos.ORIGIN,
                    cow.world,
                    stack,
                    vertexConsumerProvider.getBuffer(RenderLayers.getBlockLayer(Blocks.CHORUS_PLANT.getDefaultState())),
                    false,
                    cow.world.random
            );

            stack.translate(0, 0, 1);
            client.getBlockRenderManager().renderBlock(
                    Blocks.CHORUS_PLANT.getDefaultState().with(ChorusPlantBlock.NORTH, true),
                    BlockPos.ORIGIN,
                    cow.world,
                    stack,
                    vertexConsumerProvider.getBuffer(RenderLayers.getBlockLayer(Blocks.CHORUS_PLANT.getDefaultState())),
                    false,
                    cow.world.random
            );
        }

        stack.pop();
    }

    @Override
    public Identifier getTexture(ChorusCowEntity cowEntity) {
        return TEXTURE;
    }
}
