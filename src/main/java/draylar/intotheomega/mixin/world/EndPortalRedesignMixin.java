package draylar.intotheomega.mixin.world;

import com.mojang.serialization.Codec;
import draylar.intotheomega.registry.OmegaBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.EndPortalFeature;
import net.minecraft.world.gen.feature.Feature;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import java.util.stream.Collectors;

@Mixin(EndPortalFeature.class)
public abstract class EndPortalRedesignMixin extends Feature<DefaultFeatureConfig> {

    @Shadow @Final private boolean open;

    public EndPortalRedesignMixin(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    /**
     * @author Draylar
     */
    @Overwrite
    public boolean generate(StructureWorldAccess structureWorldAccess, ChunkGenerator chunkGenerator, Random random, BlockPos portalOrigin, DefaultFeatureConfig defaultFeatureConfig) {
        Iterator<BlockPos> boxPositions = BlockPos.iterate(new BlockPos(portalOrigin.getX() - 4, portalOrigin.getY() - 1, portalOrigin.getZ() - 4), new BlockPos(portalOrigin.getX() + 4, portalOrigin.getY() + 32, portalOrigin.getZ() + 4)).iterator();

        while(true) {
            BlockPos next;
            boolean firstLayer;

            // Place the center pillar & torches surrounding it.
            do {
                if (!boxPositions.hasNext()) {

                    // Place the center pillar.
                    setBlockState(structureWorldAccess, portalOrigin.up(0), OmegaBlocks.OBSIDIAN_PILLAR.getDefaultState());
                    setBlockState(structureWorldAccess, portalOrigin.up(1), OmegaBlocks.OBSIDIAN_PILLAR.getDefaultState());
                    setBlockState(structureWorldAccess, portalOrigin.up(2), OmegaBlocks.OBSIDIAN_PILLAR.getDefaultState());
                    setBlockState(structureWorldAccess, portalOrigin.up(3), OmegaBlocks.CHISELED_OBSIDIAN.getDefaultState());

                    // Place torches around the 3rd block on the pillar from the bottom
                    BlockPos torchOrigin = portalOrigin.up(2);
                    for (Direction direction : Direction.Type.HORIZONTAL) {
                        setBlockState(structureWorldAccess, torchOrigin.offset(direction), Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.FACING, direction));
                    }

                    return true;
                }

                next = boxPositions.next();
                firstLayer = next.isWithinDistance(portalOrigin, 2.5D);
            } while(!firstLayer && !next.isWithinDistance(portalOrigin, 3.5D));

            // Fill in the area underneath the portal (inside the End Island).
            if (next.getY() < portalOrigin.getY()) {
                // First layer (underneath the End Portal blocks, or what you see when the portal is unfilled. Otherwise, everything underneath that.
                if (firstLayer) {
                    setBlockState(structureWorldAccess, next, Blocks.OBSIDIAN.getDefaultState());
                } else if (next.getY() < portalOrigin.getY()) {
                    setBlockState(structureWorldAccess, next, Blocks.END_STONE.getDefaultState());
                }
            }

            // Place air ABOVE the portal.
            else if (next.getY() > portalOrigin.getY()) {
//                setBlockState(structureWorldAccess, next, Blocks.AIR.getDefaultState());
            }

            // This forms the ring outside the portal.
            else if (!firstLayer) {
                setBlockState(structureWorldAccess, next, Blocks.OBSIDIAN.getDefaultState());

                int xOffset = portalOrigin.getX() - next.getX();
                int zOffset = portalOrigin.getZ() - next.getZ();

                // If the x or z position is 1 away from the axis's 0, place Chiseled Obsidian one block up.
                if(Math.abs(xOffset) == 1 || Math.abs(zOffset) == 1) {
                    setBlockState(structureWorldAccess, next.up(), OmegaBlocks.CHISELED_OBSIDIAN.getDefaultState());
                }

                // If the x or z position is 0, place Obsidian Pillar
                if(Math.abs(xOffset) == 0 || Math.abs(zOffset) == 0) {
                    setBlockState(structureWorldAccess, next.up(), OmegaBlocks.OBSIDIAN_PILLAR.getDefaultState());

                    // Calculate facing direction for stairs
                    Direction direction;
                    if(zOffset > 0 && xOffset == 0) {
                        direction = Direction.SOUTH;
                    } else if (xOffset < 0 && zOffset == 0) {
                        direction = Direction.WEST;
                    } else if (zOffset < 0 && xOffset == 0) {
                        direction = Direction.NORTH;
                    } else {
                        direction = Direction.EAST;
                    }

                    // Place stairs on top of the Pillar
                    setBlockState(structureWorldAccess, next.up(2), OmegaBlocks.OBSIDIAN_STAIRS.getDefaultState().with(StairsBlock.FACING, direction));
                    setBlockState(structureWorldAccess, next.up(1).offset(direction.getOpposite()), OmegaBlocks.OBSIDIAN_STAIRS.getDefaultState().with(StairsBlock.FACING, direction));
                }

                // If the x or z position is 2, place Crying Obsidian :'(
                if(Math.abs(xOffset) == 2 || Math.abs(zOffset) == 2) {
                    setBlockState(structureWorldAccess, next.up(), Blocks.CRYING_OBSIDIAN.getDefaultState());

                    for(Direction direction : Arrays.stream(Direction.values()).filter(it -> it.getId() >= 2).collect(Collectors.toList())) {
                        BlockPos o = next.up().offset(direction);
                        if(!o.isWithinDistance(portalOrigin, 2.5D)) {
                            setBlockState(structureWorldAccess, o, OmegaBlocks.OBSIDIAN_SLAB.getDefaultState());
                        }
                    };
                }
            }

            // Depending on whether the portal is open, either fill the inside with End Portals or Air.
            else if (open) {
                setBlockState(structureWorldAccess, new BlockPos(next), Blocks.END_PORTAL.getDefaultState());
            } else {
                setBlockState(structureWorldAccess, new BlockPos(next), Blocks.AIR.getDefaultState());
            }
        }
    }
}
