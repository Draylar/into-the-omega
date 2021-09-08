package draylar.intotheomega.enchantment;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EndSlimeEntity extends SlimeEntity {

    private int teleportCooldown = 0;

    public EndSlimeEntity(EntityType<? extends SlimeEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        super.tick();

        if(!world.isClient) {
            teleportCooldown = Math.max(0, teleportCooldown - 1);

            if(!onGround && getVelocity().getY() <= 0) {

                // 10% chance to teleport
                if(world.random.nextDouble() <= 0.10 && teleportCooldown <= 0) {
                    if (teleportRandomly()) {
                        world.playSound(null, this.prevX, this.prevY, this.prevZ, SoundEvents.ENTITY_ENDERMAN_TELEPORT, this.getSoundCategory(), 1.0F, 1.0F);
                        playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
                        teleportCooldown = 20;
                    }
                }
            }
        }
    }

    // EndermanEntity#teleportRandomly
    public boolean teleportRandomly() {
        if (!this.world.isClient() && this.isAlive()) {
            double d = this.getX() + (this.random.nextDouble() - 0.5D) * 16.0D;
            double e = this.getY() + (double)(this.random.nextInt(8) - 4);
            double f = this.getZ() + (this.random.nextDouble() - 0.5D) * 16.0D;
            return this.teleportTo(d, e, f);
        } else {
            return false;
        }
    }

    // EndermanEntity#teleportTo
    private boolean teleportTo(double x, double y, double z) {
        BlockPos.Mutable mutable = new BlockPos.Mutable(x, y, z);

        // Locate the first block the Enderman can stand on.
        while(mutable.getY() > 0 && !this.world.getBlockState(mutable).getMaterial().blocksMovement()) {
            mutable.move(Direction.DOWN);
        }

        BlockState blockState = this.world.getBlockState(mutable);
        boolean bl = blockState.getMaterial().blocksMovement();
        boolean bl2 = blockState.getFluidState().isIn(FluidTags.WATER);
        if (bl && !bl2) {
            return this.teleport(x, y, z, true);
        } else {
            return false;
        }
    }
}
