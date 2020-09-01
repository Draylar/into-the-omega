package draylar.intotheomega.item;

import draylar.intotheomega.registry.OmegaParticles;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class OmegaSwordItem extends SwordItem {

    public OmegaSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient) {
            ServerWorld serverWorld = (ServerWorld) world;
            HitResult res = user.rayTrace(50, 0, false);

            double mult = .25;

            Vec3d origin = user.getPos();
            Vec3d target = res.getPos();
            double diff = Math.sqrt(origin.squaredDistanceTo(target)); // distance in blocks from origin to target
            Vec3d per = target.subtract(origin).multiply(1 / diff).multiply(mult); // position to increment per each block
            Vec3d cur = new Vec3d(origin.getX(), origin.getY(), origin.getZ()); // current position

            for(double i = 0; i < diff; i += mult) {
                serverWorld.spawnParticles(OmegaParticles.OMEGA_PARTICLE, cur.getX(), cur.getY(), cur.getZ(), 1, 0, 0, 0, 0);
                cur = cur.add(per);
            }

            if(res instanceof EntityHitResult) {
                ((EntityHitResult) res).getEntity().damage(DamageSource.MAGIC, 5);
            }

            return TypedActionResult.success(user.getStackInHand(hand));
        }

        return super.use(world, user, hand);
    }
}
