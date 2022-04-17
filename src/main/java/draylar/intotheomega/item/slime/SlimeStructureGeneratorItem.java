package draylar.intotheomega.item.slime;

import draylar.intotheomega.world.SlimeStructureGenerator;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SlimeStructureGeneratorItem extends Item {

    public SlimeStructureGeneratorItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient) {
            HitResult raycast = user.raycast(512, 0, false);
            BlockPos pos = new BlockPos(raycast.getPos());
            new SlimeStructureGenerator(null, null, null).spawn(world, pos);
        }

        return super.use(world, user, hand);
    }
}
