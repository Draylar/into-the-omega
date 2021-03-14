package draylar.intotheomega.item;

import draylar.intotheomega.material.FerliousToolMaterial;
import net.minecraft.particle.ParticleTypes;

public class FerliousItem extends CustomBowItem {

    public FerliousItem(Settings settings) {
        super(FerliousToolMaterial.INSTANCE, settings, 15, ParticleTypes.DRAGON_BREATH);
        setStaticVelocity(true);
    }
}
