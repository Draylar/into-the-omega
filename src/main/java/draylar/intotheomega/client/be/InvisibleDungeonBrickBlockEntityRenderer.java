package draylar.intotheomega.client.be;

import draylar.intotheomega.entity.dungeon.InvisibleDungeonBrickBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

// https://github.com/OpenMods/OpenBlocks/blob/09a3675d1ddd96389913d45ee25c777dcbca5114/src/main/java/openblocks/client/renderer/tileentity/TileEntitySkyRenderer.java
// License => MIT
public class InvisibleDungeonBrickBlockEntityRenderer extends BlockEntityRenderer<InvisibleDungeonBrickBlockEntity> {

    public InvisibleDungeonBrickBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(InvisibleDungeonBrickBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {



    }
}
