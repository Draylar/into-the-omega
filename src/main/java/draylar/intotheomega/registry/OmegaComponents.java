package draylar.intotheomega.registry;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.cca.TelosComponent;
import net.minecraft.entity.player.PlayerEntity;

public class OmegaComponents implements EntityComponentInitializer {

    public static final ComponentKey<TelosComponent> TELOS = ComponentRegistryV3.INSTANCE.getOrCreate(IntoTheOmega.id("telos"), TelosComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerFor(PlayerEntity.class, TELOS, TelosComponent::new);
    }
}
