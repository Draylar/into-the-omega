package draylar.intotheomega.client.particle;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import draylar.intotheomega.api.client.OmegaVertexFormats;
import draylar.intotheomega.registry.client.OmegaShaders;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.texture.TextureManager;

public class OmegaParticleSheets {

    public static final ParticleTextureSheet PROGRESS_TRANSLUCENT = new ParticleTextureSheet() {

        @Override
        public void begin(BufferBuilder bufferBuilder, TextureManager textureManager) {
            RenderSystem.depthMask(false);
            textureManager.bindTexture(SpriteAtlasTexture.PARTICLE_ATLAS_TEXTURE);
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);

            // Shader & Buffer Drawing
            RenderSystem.setShader(OmegaShaders.INDICATOR_PARTICLE);
            bufferBuilder.begin(VertexFormat.DrawMode.QUADS, OmegaVertexFormats.POSITION_TEXTURE_COLOR_LIGHT_PROGRESS);
        }

        @Override
        public void draw(Tessellator tessellator) {
            tessellator.draw();
        }

        @Override
        public String toString() {
            return "PROGRESS_TRANSLUCENT";
        }
    };
}
