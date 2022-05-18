package draylar.intotheomega.item;

import it.unimi.dsi.fastutil.Hash;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.StructureBlockBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Map;

public class FloodingStructureVoidItem extends Item {

    public FloodingStructureVoidItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos origin = context.getBlockPos().toImmutable();

        if(!world.isClient) {
            @Nullable StructureBlockBlockEntity closest = findClosestStructureBlock(context, world, origin);
            if(closest != null) {
                BlockPos offset = closest.getOffset();
                Vec3i size = closest.getSize();
                BlockPos boxOrigin = closest.getPos().add(offset);
                BlockPos boxEnd = boxOrigin.add(size);
                BlockBox box = new BlockBox(boxOrigin.getX(), boxOrigin.getY(), boxOrigin.getZ(), boxEnd.getX() - 1, boxEnd.getY() - 1, boxEnd.getZ() - 1);
                if(box.contains(origin)) {
                    HashSet<BlockPos> foundPositions = new HashSet<>();
                    HashSet<BlockPos> toSearch = new HashSet<>();
                    HashSet<BlockPos> checked = new HashSet<>();

                    toSearch.add(origin.toImmutable());

                    while(!toSearch.isEmpty()) {
                        processPositions(world, box, foundPositions, toSearch, checked);
                    }

                    for (BlockPos pos : foundPositions) {
                        if(world.isAir(pos)) {
                            world.setBlockState(pos, Blocks.STRUCTURE_VOID.getDefaultState());
                        }
                    }
                }
            }
        }

        return super.useOnBlock(context);
    }

    private void processPositions(World world, BlockBox box, HashSet<BlockPos> foundPositions, HashSet<BlockPos> toSearch, HashSet<BlockPos> checked) {
        HashSet<BlockPos> newSearch = new HashSet<>();
        for (BlockPos search : toSearch) {
            for (Direction value : Direction.values()) {
                BlockPos offset = search.offset(value);

                if(!checked.contains(offset)) {
                    if(box.contains(offset)) {
                        if(world.isAir(offset)) {
                            foundPositions.add(offset);
                            newSearch.add(offset);
                        }
                    }

                    checked.add(offset);
                }
            }
        }

        toSearch.clear();
        toSearch.addAll(newSearch);
        newSearch.clear();
    }


    @Nullable
    private StructureBlockBlockEntity findClosestStructureBlock(ItemUsageContext context, World world, BlockPos origin) {
        StructureBlockBlockEntity structureBlock = null;
        double distance = -1;

        for (int x = -3; x <= 3; x++) {
            for (int z = -3; z <= 3; z++) {
                ChunkPos pos = new ChunkPos(context.getBlockPos());
                WorldChunk chunk = world.getChunk(pos.x + x, pos.z + z);
                for (Map.Entry<BlockPos, BlockEntity> entry : chunk.getBlockEntities().entrySet()) {
                    BlockPos blockEntityPos = entry.getKey();
                    BlockEntity blockEntity = entry.getValue();

                    if(blockEntity instanceof StructureBlockBlockEntity found) {
                        double newDistance = Math.sqrt(origin.getSquaredDistance(blockEntityPos));

                        if(distance == -1) {
                            structureBlock = found;
                            distance = newDistance;
                        }

                        else if (newDistance < distance) {
                            distance = newDistance;
                            structureBlock = found;
                        }
                    }
                }
            }
        }

        return structureBlock;
    }
}
