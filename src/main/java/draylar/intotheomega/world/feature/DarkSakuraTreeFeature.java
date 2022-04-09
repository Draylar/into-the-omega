package draylar.intotheomega.world.feature;

import draylar.intotheomega.api.DevelopmentSpawnable;
import draylar.intotheomega.registry.OmegaBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.noise.SimplexNoiseSampler;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.random.SimpleRandom;

import java.util.Random;

public class DarkSakuraTreeFeature extends Feature<DefaultFeatureConfig> implements DevelopmentSpawnable {

    private static final SimplexNoiseSampler DARK_SAKURA_NOISE = new SimplexNoiseSampler(new SimpleRandom(new Random().nextLong(100000000)));

    public DarkSakuraTreeFeature() {
        super(DefaultFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getOrigin();
        Random random = context.getRandom();

        BlockPos topPosition = world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, pos);
        BlockState underneath = world.getBlockState(topPosition.down());

        if(underneath.getBlock().equals(Blocks.END_STONE)) {
            placeTree(world, random, pos);
            return true;
        }

        return false;
    }

    private void placeTree(StructureWorldAccess world, Random random, BlockPos origin) {
        origin = origin.down();

        int treeHeight = 8 + world.getRandom().nextInt(5);
        Vec3d vecOrigin = new Vec3d(origin.getX(), origin.getY(), origin.getZ());
        int randX = (random.nextInt(4) + 1) * (random.nextDouble() < 0.5 ? -1 : 1);
        int randZ = (random.nextInt(4) + 1) * (random.nextDouble() < 0.5 ? -1 : 1);
        Vec3d vecTarget = vecOrigin.add(randX, treeHeight, randZ);
        Vec3d towardsTarget = vecTarget.subtract(vecOrigin).normalize();
        BlockPos top = branch(world, vecOrigin, vecTarget);

        // Build up the base
        for(int y = 0; y < 3; y++) {
            int radius = 2 - y;
            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2));
                    if(distance <= radius + DARK_SAKURA_NOISE.sample(x, y + origin.getY(), z) * 5) {
                        BlockPos p = origin.add(x, y, z);
                        if(y == 0 || world.getBlockState(p.down()).getBlock().equals(Blocks.DARK_OAK_WOOD)) {
                            world.setBlockState(p, Blocks.DARK_OAK_WOOD.getDefaultState(), 3);
                        }
                    }
                }
            }
        }

        // Branches
        for(int i = 0 ; i < 2 + random.nextInt(2); i++) {
            Vec3d branchOrigin = new Vec3d(top.getX(), top.getY(), top.getZ());
            int branchLength = 3 + world.getRandom().nextInt(3);

            double xOffset = (world.getRandom().nextDouble() - 0.5) * 1.5;
            double yOffset = (world.getRandom().nextDouble() - 0.5) * 2;
            double zOffset = (world.getRandom().nextDouble() - 0.5) * 1.5;
            Vec3d per = towardsTarget.add(xOffset, yOffset, zOffset).multiply(0.5f);
            Vec3d branchEnd = branchOrigin.add(per.multiply(branchLength));
            for(int z = 0; z < branchLength * 2; z++) {
                branchOrigin = branchOrigin.add(per);
                world.setBlockState(new BlockPos(branchOrigin.getX(), branchOrigin.getY(), branchOrigin.getZ()), Blocks.DARK_OAK_WOOD.getDefaultState(), 3);
            }
        }

        // Leaves
        for (int x = -8; x <= 8; x++) {
            for (int z = -8; z <= 8; z++) {
                for (int y = -8; y <= 8; y++) {
                    BlockPos leafPos = top.add(x, y + 3, z);
                    double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
                    if(distance <= 5 - DARK_SAKURA_NOISE.sample(leafPos.getX() / 8f, leafPos.getY() / 8f, leafPos.getZ() / 8f) * 6) {
                        if(!world.getBlockState(leafPos).getBlock().equals(Blocks.DARK_OAK_WOOD)) {
                            world.setBlockState(leafPos, OmegaBlocks.DARK_SAKURA_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, true), 3);
                        }
                    }
                }
            }
        }

        // Coarse Dirt
        for(int x = -8; x <= 8; x++) {
            for (int z = -8; z <= 8; z++) {
                BlockPos root = origin.add(x, 0, z);
                BlockState atRoot = world.getBlockState(root);
                BlockState up =world.getBlockState(root.up());
                int tries = 0;
                boolean valid = true;

                // Find the top block, but only check a maximum of 3 up or down
                // Block is air - going down
                if(atRoot.isAir()) {
                    valid = false;
                    while(atRoot.isAir() && tries < 3 && !valid) {
                        tries++;
                        root = root.down();
                        atRoot = world.getBlockState(root);
                        if(!atRoot.isAir()) {
                            valid = true;
                        }
                    }
                }

                // Moving up!
                else if (!up.isAir()) {
                    valid = false;
                    while(atRoot.isAir() && tries < 3 && !valid) {
                        tries++;
                        root = root.up();
                        atRoot = world.getBlockState(root);
                        if(atRoot.isAir()) {
                            valid = true;
                        }
                    }
                }

                if(valid) {
                    double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2));
                    if(distance <= 8) {
                        if(distance <= 5 - DARK_SAKURA_NOISE.sample(root.getX(), root.getY(), root.getZ()) * 8) {
                            world.setBlockState(root, Blocks.COARSE_DIRT.getDefaultState(), 3);
                        }

                        if(random.nextDouble() <= 0.25) {
                            world.setBlockState(root.up(), OmegaBlocks.DARK_SAKURA_LEAF_PILE.getDefaultState(), 3);
                        }
                    }
                }
            }
        }
    }

    private BlockPos branch(StructureWorldAccess world, Vec3d from, Vec3d to) {
        BlockPos last = null;
        Vec3d vecOrigin = from;
        Vec3d perIncrement = to.subtract(vecOrigin).multiply(1 / vecOrigin.distanceTo(to));
        double distance = vecOrigin.distanceTo(to);

        // Build trunk
        for (int i = 0; i < distance; i++) {
            last = new BlockPos(vecOrigin);
            world.setBlockState(last, Blocks.DARK_OAK_WOOD.getDefaultState(), 3);
            world.setBlockState(new BlockPos(vecOrigin).down(), Blocks.DARK_OAK_WOOD.getDefaultState(), 3);
            vecOrigin = vecOrigin.add(perIncrement);
        }

        return last;
    }

    @Override
    public void spawn(StructureWorldAccess world, BlockPos pos) {
        placeTree(world, world.getRandom(), pos);
    }
}
