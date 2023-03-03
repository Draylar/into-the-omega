package draylar.intotheomega.mixin;

import com.google.common.collect.ImmutableList;
import draylar.intotheomega.api.event.ParticleEvents;
import draylar.intotheomega.client.particle.*;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.world.ClientWorld;
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

    @Shadow @Final @Mutable private static List<ParticleTextureSheet> PARTICLE_TEXTURE_SHEETS;

    @Inject(
            method = "<init>",
            at = @At("RETURN")
    )
    private void onInit(ClientWorld world, TextureManager textureManager, CallbackInfo ci) {
        ParticleEvents.REGISTER_FACTORIES.invoker().onRegister((ParticleManager) (Object) this);
    }

    static {
        PARTICLE_TEXTURE_SHEETS = new ImmutableList.Builder<ParticleTextureSheet>()
                .addAll(PARTICLE_TEXTURE_SHEETS)
                .add(OmegaParticle.PARTICLE_SHEET_TRANSLUCENT)
                .add(OmegaParticleSheets.PROGRESS_TRANSLUCENT_CIRCULAR)
                .add(OmegaParticleSheets.PROGRESS_TRANSLUCENT_LENGTH_EXPAND)
                .add(OmegaParticleSheets.TRANSLUCENT_ADDITION)
                .build();
    }
}
