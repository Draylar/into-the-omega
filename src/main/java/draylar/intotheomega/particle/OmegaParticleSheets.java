package draylar.intotheomega.particle;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import draylar.intotheomega.api.client.OmegaVertexFormats;
import draylar.intotheomega.api.shader.OmegaCoreShader;
import draylar.intotheomega.registry.client.OmegaShaders;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.texture.TextureManager;

public class OmegaParticleSheets {

    private static class ShaderTemplateSheet implements ParticleTextureSheet {

        private final OmegaCoreShader shader;
        private final VertexFormat format;

        public ShaderTemplateSheet(OmegaCoreShader shader, VertexFormat format) {
            this.shader = shader;
            this.format = format;
        }

        @Override
        public void begin(BufferBuilder builder, TextureManager textureManager) {
            RenderSystem.depthMask(false);
            textureManager.bindTexture(SpriteAtlasTexture.PARTICLE_ATLAS_TEXTURE);
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);

            // Shader & Buffer Drawing
            RenderSystem.setShader(shader);
            builder.begin(VertexFormat.DrawMode.QUADS, format);
        }

        @Override
        public void draw(Tessellator tessellator) {
            tessellator.draw();
        }
    }

    public static final ParticleTextureSheet TRANSLUCENT_ADDITION = new ParticleTextureSheet() {

        @Override
        public void begin(BufferBuilder bufferBuilder, TextureManager textureManager) {
            RenderSystem.depthMask(false);
            RenderSystem.setShader(OmegaShaders.TRANSLUCENT_PARTICLE);
            RenderSystem.setShaderTexture(0, SpriteAtlasTexture.PARTICLE_ATLAS_TEXTURE);
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(
                    GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE,
                    GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ONE
            );

            bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR_LIGHT);
        }

        @Override
        public void draw(Tessellator tessellator) {
            tessellator.draw();
        }

        @Override
        public String toString() {
            return "TRANSLUCENT_ADDITION";
        }
    };

    public static final ParticleTextureSheet BLOCK = new ParticleTextureSheet(){

        @Override
        public void begin(BufferBuilder builder, TextureManager textureManager) {
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.depthMask(true);
            RenderSystem.setShaderTexture(0, SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE);
            builder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR_LIGHT);
        }

        @Override
        public void draw(Tessellator tessellator) {
            tessellator.draw();
        }

        public String toString() {
            return "BLOCK_SHEET";
        }
    };

    public static final ParticleTextureSheet PROGRESS_TRANSLUCENT_CIRCULAR = new ShaderTemplateSheet(OmegaShaders.CIRCLE_INDICATOR_PARTICLE, OmegaVertexFormats.POSITION_TEXTURE_COLOR_LIGHT_PROGRESS);
    public static final ParticleTextureSheet PROGRESS_TRANSLUCENT_LENGTH_EXPAND = new ShaderTemplateSheet(OmegaShaders.LENGTH_EXPAND_INDICATOR_PARTICLE, OmegaVertexFormats.POSITION_TEXTURE_COLOR_LIGHT_PROGRESS);
}
