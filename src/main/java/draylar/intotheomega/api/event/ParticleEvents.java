package draylar.intotheomega.api.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.particle.ParticleManager;

@Environment(EnvType.CLIENT)
public class ParticleEvents {

    public static final Event<RegistryHandler> REGISTER_FACTORIES = EventFactory.createArrayBacked(RegistryHandler.class,
            (listeners) -> manager -> {
                for (RegistryHandler callback : listeners) {
                    callback.onRegister(manager);
                }
            }
    );

    public interface RegistryHandler {
        void onRegister(ParticleManager manager);
    }
}
