package draylar.intotheomega.registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.LiteralText;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class OmegaKeys {

    public static void init() {
        if(FabricLoader.getInstance().isDevelopmentEnvironment()) {
            ClientTickEvents.START_CLIENT_TICK.register(client -> {
                if(client == null) return;

                if(InputUtil.isKeyPressed(client.getWindow().getHandle(), GLFW.GLFW_KEY_X) && InputUtil.isKeyPressed(client.getWindow().getHandle(), GLFW.GLFW_KEY_LEFT_CONTROL)) {
                    client.submit(() -> {
                        long start = System.currentTimeMillis();
                        MinecraftClient.getInstance().gameRenderer.loadShaders(MinecraftClient.getInstance().getResourceManager());
                        if(client.player != null) {
                            client.player.sendMessage(new LiteralText("Reloaded all shaders in %dms".formatted(System.currentTimeMillis() - start)), true);
                        }
                    });
                }
            });
        }
    }

    private OmegaKeys() {
        // NO-OP
    }
}
