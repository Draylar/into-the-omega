package draylar.intotheomega.entity;

import draylar.intotheomega.registry.OmegaParticles;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.world.World;

public class OmegaSlimeEntity extends SlimeEntity {

    public OmegaSlimeEntity(EntityType<? extends SlimeEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public ParticleEffect getParticles() {
        return OmegaParticles.OMEGA_SLIME;
    }
}
