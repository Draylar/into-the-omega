package draylar.intotheomega.mixin;

import com.google.common.collect.ImmutableList;
import draylar.intotheomega.api.Color;
import draylar.intotheomega.client.particle.OmegaParticle;
import draylar.intotheomega.client.particle.OmegaSlimeParticle;
import draylar.intotheomega.client.particle.SmallOmegaBurstParticle;
import draylar.intotheomega.registry.OmegaParticles;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ParticleManager.class)
public abstract class ParticleManagerMixin {

    @Shadow protected abstract <T extends ParticleEffect> void registerFactory(ParticleType<T> particleType, ParticleManager.SpriteAwareFactory<T> spriteAwareFactory);

    @Shadow @Final @Mutable private static List<ParticleTextureSheet> PARTICLE_TEXTURE_SHEETS;

    @Inject(
            method = "<init>",
            at = @At("RETURN")
    )
    private void onInit(ClientWorld world, TextureManager textureManager, CallbackInfo ci) {
        this.registerFactory(OmegaParticles.OMEGA_PARTICLE, provider -> new OmegaParticle.OmegaParticleFactory(provider, new Color(0.9F, 0.1F, 0.9F)));
        this.registerFactory(OmegaParticles.DARK, provider -> new OmegaParticle.OmegaParticleFactory(provider, new Color(0.34F, 0.03F, 0.47F)));
        this.registerFactory(OmegaParticles.OMEGA_SLIME, provider -> new OmegaSlimeParticle());
        this.registerFactory(OmegaParticles.SMALL_OMEGA_BURST, SmallOmegaBurstParticle.Factory::new);
    }

    static {
        PARTICLE_TEXTURE_SHEETS = new ImmutableList.Builder<ParticleTextureSheet>().addAll(PARTICLE_TEXTURE_SHEETS).add(OmegaParticle.PARTICLE_SHEET_TRANSLUCENT).build();
    }
}
