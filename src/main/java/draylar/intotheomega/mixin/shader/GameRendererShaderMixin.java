package draylar.intotheomega.mixin.shader;

import draylar.intotheomega.api.shader.OmegaCoreShader;
import draylar.intotheomega.registry.client.OmegaShaders;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Shader;
import net.minecraft.resource.ResourceManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.util.Map;

@Mixin(GameRenderer.class)
public class GameRendererShaderMixin {

    @Shadow @Final private Map<String, Shader> shaders;

    @Inject(method = "loadShaders", at = @At(value = "RETURN"))
    private void loadOmegaShaders(ResourceManager manager, CallbackInfo ci) {
        for (OmegaCoreShader coreShader : OmegaCoreShader.getCoreShaders()) {
            try {
                coreShader.setup(manager);
                shaders.put(coreShader.getShader().getName(), coreShader.getShader());
            } catch (IOException exception) {
                throw new RuntimeException("could not reload ITO shaders", exception);
            }
        }
    }
}
