package draylar.intotheomega.mixin;

import draylar.intotheomega.api.block.EarlyRender;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Mixin(WorldRenderer.class)
public class WorldRendererEarlyBEMixin {

    @Shadow @Final private ObjectArrayList<WorldRenderer.ChunkInfo> chunkInfos;
    @Shadow @Final private BlockEntityRenderDispatcher blockEntityRenderDispatcher;
    @Shadow @Final private BufferBuilderStorage bufferBuilders;

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/world/ClientWorld;getEntities()Ljava/lang/Iterable;"))
    private void ito$renderEarlyBlockEntities(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f positionMatrix, CallbackInfo ci) {
        VertexConsumerProvider.Immediate immediate = bufferBuilders.getEntityVertexConsumers();
        Vec3d vec3d = camera.getPos();
        double camX = vec3d.getX();
        double camY = vec3d.getY();
        double camZ = vec3d.getZ();

        for (WorldRenderer.ChunkInfo chunkInfo : chunkInfos) {
            List<BlockEntity> toRender = chunkInfo.chunk.getData().getBlockEntities();
            for (BlockEntity blockEntity : toRender) {
                if(!(blockEntity instanceof EarlyRender)) {
                    return;
                }

                BlockPos pos = blockEntity.getPos();
                matrices.push();
                matrices.translate((double)pos.getX() - camX, (double)pos.getY() - camY, (double)pos.getZ() - camZ);
                blockEntityRenderDispatcher.render(blockEntity, tickDelta, matrices, immediate);
                matrices.pop();
            }
        }
    }

    /**
     * Prevent any non-no-cull {@link BlockEntity} from rendering if it is an {@link EarlyRender}.
     */
    @ModifyVariable(method = "render", at = @At(value = "INVOKE", target = "Ljava/util/List;isEmpty()Z"), index = 28)
    private List<BlockEntity> ito$removeEarlyBlockEntities(List<BlockEntity> bes) {
        List<BlockEntity> duped = new ArrayList<>();
        for (BlockEntity be : bes) {
            if(!(be instanceof EarlyRender)) {
                duped.add(be);
            }
        }

        return duped;
    }
}
