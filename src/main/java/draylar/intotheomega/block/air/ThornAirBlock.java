package draylar.intotheomega.block.air;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.AirBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ThornAirBlock extends AirBlock {

    public ThornAirBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {


        super.onEntityCollision(state, world, pos, entity);
    }
}
