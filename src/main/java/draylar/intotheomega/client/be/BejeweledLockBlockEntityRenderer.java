package draylar.intotheomega.client.be;

import draylar.intotheomega.entity.dungeon.BejeweledLockBlockEntity;
import draylar.intotheomega.registry.OmegaBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.stream.Collectors;

public class BejeweledLockBlockEntityRenderer implements BlockEntityRenderer<BejeweledLockBlockEntity> {

    public BejeweledLockBlockEntityRenderer(BlockEntityRendererFactory.Context context) {

    }

    @Override
    public void render(BejeweledLockBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        MinecraftClient client = MinecraftClient.getInstance();
        World world = entity.getWorld();

        matrices.push();
        matrices.translate(0.5, 0.5, 0.5);
        int unlockTicks = entity.getUnlockTicks();
        float lerpedUnlockTicks = MathHelper.lerp(tickDelta, entity.getUnlockTicks() - 1, unlockTicks);

        if(unlockTicks >= 1) {
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-lerpedUnlockTicks));
        }

        matrices.translate(-0.5, -0.5, -0.5);

        // base obsidian
        client.getBlockRenderManager().renderBlock(
                OmegaBlocks.OBSIDIAN_PILLAR.getDefaultState(),
                entity.getPos(),
                world,
                matrices,
                vertexConsumers.getBuffer(RenderLayers.getEntityBlockLayer(OmegaBlocks.OBSIDIAN_PILLAR.getDefaultState(), false)),
                false,
                world.random);

        // first layer
        matrices.push();
        matrices.translate(0, 1, 0);
        VertexConsumer vc = vertexConsumers.getBuffer(RenderLayers.getEntityBlockLayer(OmegaBlocks.BEJEWELED_OBSIDIAN.getDefaultState(), false));

        client.getBlockRenderManager().renderBlock(
                OmegaBlocks.BEJEWELED_OBSIDIAN.getDefaultState(),
                entity.getPos(),
                world,
                matrices,
                vc,
                false,
                world.random);
        matrices.pop();

        // second layer
        matrices.push();
        matrices.translate(0, 2, 0);
        client.getBlockRenderManager().renderBlock(
                OmegaBlocks.BEJEWELED_OBSIDIAN.getDefaultState(),
                entity.getPos(),
                world,
                matrices,
                vc,
                false,
                world.random);
        matrices.pop();

        // When the Bejeweled Lock is rotating, render the bottom platform.
        if(unlockTicks >= 1) {
            matrices.push();
            float scale = 1 - lerpedUnlockTicks / 360f;
            matrices.translate(0, -0.02, 0);

            matrices.translate(0.5, 0.5, 0.5);
            matrices.scale(scale, (float) .99, scale);
            matrices.translate(-0.5, -0.5, -0.5);

            for (Direction direction : Arrays.stream(Direction.values()).filter(direction -> !direction.getAxis().equals(Direction.Axis.Y)).collect(Collectors.toList())) {
                // directly out
                matrices.push();
                matrices.translate(direction.getOffsetX(), -1, direction.getOffsetZ());
                render(world, Blocks.END_STONE.getDefaultState(), entity.getPos(), matrices, vc);
                matrices.pop();

                // corner
                Direction next = direction.rotateYClockwise();
                matrices.push();
                matrices.translate(direction.getOffsetX() + next.getOffsetX(), -1, direction.getOffsetZ() + next.getOffsetZ());
                render(world, Blocks.END_STONE.getDefaultState(), entity.getPos(), matrices, vc);
                matrices.pop();
            }
            matrices.pop();
        }

        matrices.pop();
    }

    private void render(World world, BlockState state, BlockPos pos, MatrixStack matrices, VertexConsumer vc) {
        MinecraftClient.getInstance().getBlockRenderManager().renderBlock(
                state,
                pos,
                world,
                matrices,
                vc,
                false,
                world.random);
    }
}
