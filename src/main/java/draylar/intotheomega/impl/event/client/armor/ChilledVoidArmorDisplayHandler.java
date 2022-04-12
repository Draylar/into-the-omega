package draylar.intotheomega.impl.event.client.armor;

import draylar.intotheomega.api.client.ArmorSetDisplayRegistry;
import draylar.intotheomega.registry.OmegaParticles;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;

public class ChilledVoidArmorDisplayHandler implements ArmorSetDisplayRegistry.SetDisplay {

    @Override
    public void display(AbstractClientPlayerEntity player, ClientWorld world, float delta) {
        double age = MathHelper.lerp(delta, player.age, player.age + 1) / 10f;
        double x = Math.sin(age) * 2;
        double z = Math.cos(age) * 2;
        double y = Math.sin(age / 10) + 1;

        double secondAge = MathHelper.lerp(delta, player.age, player.age + 1) / 10f + 135;
        double secondX = Math.sin(secondAge) * 2;
        double secondZ = Math.cos(secondAge) * 2;

        world.addParticle(OmegaParticles.ICE_FLAKE, player.getX() + x, player.getY() + y, player.getZ() + z, 0, 0, 0);
        world.addParticle(OmegaParticles.ICE_FLAKE, player.getX() + secondX, player.getY() + y, player.getZ() + secondZ, 0, 0, 0);
    }
}
