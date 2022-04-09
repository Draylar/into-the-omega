package draylar.intotheomega.client;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.registry.OmegaFluids;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

import java.util.function.Function;

@Environment(EnvType.CLIENT)
public class OmegaSlimeRenderingHandler {

    public static final Identifier STILL_TEXTURE = IntoTheOmega.id("block/omega_slime_still");
    public static final Identifier FLOWING_TEXTURE = IntoTheOmega.id("block/omega_slime_flow");

    public static void init() {
        Sprite[] sprites = new Sprite[2];

        // Append Omega Slime textures to the block atlas
        ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).register((atlas, registry) -> {
            registry.register(STILL_TEXTURE);
            registry.register(FLOWING_TEXTURE);
        });

        // Register a resource listener that loads the fluid sprites from the atlas
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
            @Override
            public Identifier getFabricId() {
                return IntoTheOmega.id("omega_slime_reload");
            }

            @Override
            public void reload(ResourceManager manager) {
                Function<Identifier, Sprite> spriteAtlas = MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE);
                sprites[0] = spriteAtlas.apply(STILL_TEXTURE);
                sprites[1] = spriteAtlas.apply(FLOWING_TEXTURE);
            }
        });

        // Create a render handler for the fluid and register it
        FluidRenderHandler fluidRenderHandler = (view, pos, state) -> sprites;
        FluidRenderHandlerRegistry.INSTANCE.register(OmegaFluids.OMEGA_SLIME_STILL, fluidRenderHandler);
        FluidRenderHandlerRegistry.INSTANCE.register(OmegaFluids.OMEGA_SLIME_FLOWING, fluidRenderHandler);
    }

    private OmegaSlimeRenderingHandler() {
        // NO-OP
    }
}
