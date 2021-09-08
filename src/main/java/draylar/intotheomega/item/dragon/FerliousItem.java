package draylar.intotheomega.item.dragon;

import draylar.intotheomega.item.CustomBowItem;
import draylar.intotheomega.material.FerliousToolMaterial;
import net.minecraft.particle.ParticleTypes;

public class FerliousItem extends CustomBowItem {

    public FerliousItem(Settings settings) {
        super(FerliousToolMaterial.INSTANCE, settings, 10, ParticleTypes.DRAGON_BREATH);
        setStaticVelocity(true);
    }
}
