package draylar.intotheomega.client.entity.renderer;

import draylar.intotheomega.client.particle.OmegaParticle;
import draylar.intotheomega.entity.ice.AbyssGlobeEntity;
import draylar.intotheomega.registry.OmegaParticles;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class AbyssGlobeRenderer extends EntityRenderer<AbyssGlobeEntity> {

    public AbyssGlobeRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(AbyssGlobeEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);

        float lerpedAge = MathHelper.lerp(tickDelta, entity.age, entity.age + 1);

        for(int i = 0; i < 5; i++) {
            entity.world.addParticle(
                    ParticleTypes.END_ROD,
                    true,
                    entity.getX() + Math.sin(lerpedAge + i / 5f) * 15,
                    entity.getY() + 1,
                    entity.getZ() + Math.cos(lerpedAge + i / 5f) * 15,
                    0,
                    0,
                    0);
        }

        for(int z = 0; z < 10; z++) {
            float scale = z / 10f;

            for (int i = 0; i < 20; i++) {
                entity.world.addParticle(
                        OmegaParticles.SMALL_BLUE_OMEGA_BURST,
                        true,
                        entity.getX() + Math.sin(lerpedAge + i / 5f) * (15 - 15 * scale),
                        entity.getY() + 3 + Math.cos(lerpedAge * 20f) * 2 + z,
                        entity.getZ() + Math.cos(lerpedAge + i / 5f) * (15 - 15 * scale),
                        0,
                        0.1,
                        0);
            }
        }
    }

    @Override
    public Identifier getTexture(AbyssGlobeEntity entity) {
        return null;
    }
}
