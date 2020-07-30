package draylar.intotheomega;

import draylar.intotheomega.ui.ConquestForgeScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

@Environment(EnvType.CLIENT)
public class IntoTheOmegaClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(IntoTheOmega.CF_SCREEN_HANDLER, ConquestForgeScreen::new);
    }
}
