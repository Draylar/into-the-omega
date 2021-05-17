package draylar.intotheomega.api.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public interface StanceAccessor {
    Stance getStance();
    void setStance(Stance stance);
}
