package draylar.intotheomega.particle;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import draylar.intotheomega.api.Color;
import draylar.intotheomega.registry.client.OmegaShaders;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class OmegaParticle extends SpriteBillboardParticle {

    public static final ParticleTextureSheet PARTICLE_SHEET_TRANSLUCENT = new ParticleTextureSheet() {

        @Override
        public void begin(BufferBuilder bufferBuilder, TextureManager textureManager) {
            RenderSystem.depthMask(false);
            RenderSystem.setShader(OmegaShaders.TRANSLUCENT_PARTICLE);
            RenderSystem.setShaderTexture(0, SpriteAtlasTexture.PARTICLE_ATLAS_TEXTURE);
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
            bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR_LIGHT);
        }

        @Override
        public void draw(Tessellator tessellator) {
            tessellator.draw();
        }

        @Override
        public String toString() {
            return "PARTICLE_SHEET_TRANSLUCENT";
        }
    };

    protected OmegaParticle(ClientWorld clientWorld, double d, double e, double f) {
        super(clientWorld, d, e, f);
    }

    @Override
    public ParticleTextureSheet getType() {
        return PARTICLE_SHEET_TRANSLUCENT;
    }

    @Environment(EnvType.CLIENT)
    public static class OmegaParticleFactory implements ParticleFactory<DefaultParticleType> {

        private final SpriteProvider spriteProvider;
        private final Color color;

        public OmegaParticleFactory(SpriteProvider spriteProvider, Color color) {
            this.spriteProvider = spriteProvider;
            this.color = color;
        }

        @Environment(EnvType.CLIENT)
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            OmegaParticle particle = new OmegaParticle(clientWorld, d, e + 0.5D, f);
            particle.setSprite(this.spriteProvider);
            particle.setColor(color.r, color.g, color.b);
            return particle;
        }
    }
}
