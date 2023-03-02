package draylar.intotheomega.api;

import net.minecraft.block.SideShapeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PositionUtils {

    @Nullable
    public static BlockPos findClosestGroundPosition(World world, BlockPos base, int maximumUp, int maximumDown) {
        // Travel up
        if(world.getBlockState(base).isSideSolid(world, base, Direction.DOWN, SideShapeType.CENTER)) {
            base = base.up();
            int tries = 1;
            while(world.getBlockState(base).isSideSolid(world, base, Direction.DOWN, SideShapeType.CENTER)) {
                base = base.up();
                tries++;

                if(tries >= maximumUp) {
                    return null;
                }
            }

            return base;
        }

        // found air, travel down
        else {
            int tries = 0;
            while(!world.getBlockState(base.down()).isSideSolid(world, base, Direction.DOWN, SideShapeType.CENTER)) {
                base = base.down();
                tries++;

                if(tries >= maximumDown) {
                    return null;
                }
            }

            return base;
        }
    }
}
