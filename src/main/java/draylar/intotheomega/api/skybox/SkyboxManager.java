package draylar.intotheomega.api.skybox;

import java.util.HashMap;
import java.util.Map;

public class SkyboxManager {

    public static final int WORLD_LAYER = 1500;
    public static final int LOCAL_LAYER = 1400;
    public static final int BOSS_LAYER = 1300;

    private static final Map<Integer, SkyboxLayer> ACTIVE_LAYERS = new HashMap<>();

    public void setLayer(int layer, SkyboxLayer skybox) {
        ACTIVE_LAYERS.put(layer, skybox);
    }

    public void discardLayer(int layer, SkyboxLayer skybox) {
        if(ACTIVE_LAYERS.get(layer) == skybox) {
            ACTIVE_LAYERS.remove(layer);
        }
    }
}
