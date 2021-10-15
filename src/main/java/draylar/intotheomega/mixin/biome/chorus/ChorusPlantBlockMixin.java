package draylar.intotheomega.mixin.biome.chorus;

import draylar.intotheomega.registry.OmegaTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChorusPlantBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Iterator;

@Mixin(ChorusPlantBlock.class)
public class ChorusPlantBlockMixin {

    /**
     * @author Draylar
     * // TODO: un-overwrite this!
     */
    @Overwrite
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState underneathState = world.getBlockState(pos.down());
        boolean validAir = !world.getBlockState(pos.up()).isAir() && !underneathState.isAir();
        Iterator<Direction> directions = Direction.Type.HORIZONTAL.iterator();

        Block underneathBlock;
        do {
            BlockPos blockPos;
            Block block;

            do {
                if (!directions.hasNext()) {
                    return OmegaTags.CHORUS_GROUND.contains(underneathState.getBlock());
                }

                Direction direction = directions.next();
                blockPos = pos.offset(direction);
                block = world.getBlockState(blockPos).getBlock();
            } while(block != Blocks.CHORUS_PLANT);

            // If the Chorus Plant has an air block underneath, the placement state is invalid.
            if (validAir) {
                return false;
            }

            underneathBlock = world.getBlockState(blockPos.down()).getBlock();
        } while(!OmegaTags.CHORUS_GROUND.contains(underneathBlock));

        return true;
    }
}
