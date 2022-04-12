package draylar.intotheomega.impl.event.client.color;

import draylar.intotheomega.client.PhasePadUtils;
import draylar.intotheomega.entity.block.PhasePadBlockEntity;
import draylar.intotheomega.registry.OmegaBlocks;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class PhasePadTickHandler implements ClientTickEvents.StartTick {

    @Override
    public void onStartTick(MinecraftClient client) {
        if(client.world == null) {
            return;
        }

        if(!client.world.getBlockState(client.player.getBlockPos().down()).getBlock().equals(OmegaBlocks.PHASE_PAD)) {
            PhasePadUtils.reset();
        } else {
            PhasePadUtils.stepOn(client.world, client.player.getBlockPos().down());

            Vec3d originPos = client.cameraEntity.getPos().multiply(1, 0, 1).add(0, client.cameraEntity.getEyeY(), 0);
            Vec3d rotationVector = client.player.getRotationVector();

            for(int i = 0; i < 32; i++) {
                Vec3d offsetPos = originPos.add(rotationVector.multiply(i));
                BlockPos offsetBlockPos = new BlockPos(offsetPos);

                if(client.world.getBlockState(offsetBlockPos).getBlock().equals(OmegaBlocks.PHASE_PAD)) {
                    BlockEntity entity = client.world.getBlockEntity(offsetBlockPos);

                    if(entity != null) {
                        ((PhasePadBlockEntity) entity).highlight();
                        PhasePadUtils.setHighlighting(offsetBlockPos);
                    }
                }
            }
        }
    }
}
