package draylar.intotheomega.mixin;

import draylar.intotheomega.client.EndSkybox;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Environment(EnvType.CLIENT)
@Mixin(WorldRenderer.class)
public class EndSkyboxMixin {

    /**
     * @author Draylar
     * @reason End Skybox Mixin
     */
//    @Overwrite
//    private void renderEndSky(MatrixStack matrices) {
//        EndSkybox.renderEndSky(matrices);
//    }
}
