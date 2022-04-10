package draylar.intotheomega.item;

import draylar.intotheomega.registry.OmegaBlocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.SimplexNoiseSampler;
import net.minecraft.world.World;
import net.minecraft.world.gen.random.SimpleRandom;

public class SlimeStructureGenerator extends Item {

    public SlimeStructureGenerator(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        SimplexNoiseSampler noise = new SimplexNoiseSampler(new SimpleRandom(world.random.nextInt()));

        if(!world.isClient) {
            HitResult raycast = user.raycast(512, 0, false);
            BlockPos pos = new BlockPos(raycast.getPos());

            for (int y = 0; y < 200; y += 2) {
                float radius = 20 + (1.0f - y / 200f) * 30;
                int x = (int) (Math.sin(y) * radius);
                int z = (int) (Math.cos(y) * radius);

                BlockPos origin = pos.add(x, y, z);
                for (BlockPos outer : BlockPos.iterateOutwards(origin, 15, 15, 15)) {
                    if(Math.sqrt(outer.getSquaredDistance(origin)) <= 10 + noise.sample(outer.getX() / 15f, outer.getY() / 15f, outer.getZ() / 15f) * 5) {
                        world.setBlockState(outer, OmegaBlocks.CONGEALED_SLIME.getDefaultState());
                    }
                }
            }

            for (int y = 0; y < 200; y += 2) {
                float radius = 20 + (1.0f - y / 200f) * 30;
                int x = (int) (Math.cos(y) * radius);
                int z = (int) (Math.sin(y) * radius);

                BlockPos origin = pos.add(x, y, z);
                for (BlockPos outer : BlockPos.iterateOutwards(origin, 15, 15, 15)) {
                    if(Math.sqrt(outer.getSquaredDistance(origin)) <= 10 + noise.sample(outer.getX() / 15f, outer.getY() / 15f, outer.getZ() / 15f) * 5) {
                        world.setBlockState(outer, OmegaBlocks.CONGEALED_OMEGA_SLIME.getDefaultState());
                    }
                }
            }
        }

        return super.use(world, user, hand);
    }
}
