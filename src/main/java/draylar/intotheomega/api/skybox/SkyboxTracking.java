package draylar.intotheomega.api.skybox;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public interface SkyboxTracking {

    @Environment(EnvType.CLIENT)
    void startTracking();

    @Environment(EnvType.CLIENT)
    void stopTracking();
}
