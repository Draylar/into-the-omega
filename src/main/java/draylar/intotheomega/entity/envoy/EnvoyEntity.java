package draylar.intotheomega.entity.envoy;

import draylar.intotheomega.api.EntityTrackingHandler;
import draylar.intotheomega.api.PositionHistoryProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.network.packet.s2c.play.MobSpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerEntityManager;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class EnvoyEntity extends PathAwareEntity implements EntityTrackingHandler<EnvoyEntity>, PositionHistoryProvider {

    private final List<EnvoySegmentEntity> children = new ArrayList<>();
    private final List<Vec3d> positionHistory = new ArrayList<>();

    public EnvoyEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        noClip = true;
        ignoreCameraFrustum = true;

        for (int i = 0; i < 50; i++) {
            EnvoySegmentEntity envoy = new EnvoySegmentEntity(this, world);
            envoy.setLinkIndex(i);
            children.add(envoy);
        }

        // Tell children about their next friend
        for (int i = 0; i < children.size(); i++) {
            if(i < children.size() - 1) {
                children.get(i).setNext(children.get(i + 1));
            }
        }
    }

    @Override
    public boolean shouldRender(double distance) {
        return true;
    }

    public static DefaultAttributeContainer.Builder createEnvoyAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 100_000.0)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, Integer.MAX_VALUE)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 100)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 256)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 1.0f);
    }

    @Override
    public List<Vec3d> getHistory() {
        return positionHistory;
    }

    @Override
    public void tick() {
        Vec3d previousPosition = getPos();

        super.tick();

        ProjectileUtil.setRotationFromVelocity(this, 1.0f);
        setPitch(MathHelper.wrapDegrees(180 - getPitch()));

        if(world.isClient) {
            Vec3d base = getPos();
            Vec3d rotation = getRotationVector();

            for (int i = 0; i < 25; i++) {
                base = base.add(rotation);
                world.addParticle(ParticleTypes.FLAME, true, base.getX(), base.getY(), base.getZ(), 0, 0, 0);
            }
        }

        if(!world.isClient) {
            positionHistory.add(0, getPos());

            for (int i = 0; i < children.size(); i++) {
                EnvoySegmentEntity child = children.get(i);
                Entity parent = i == 0 ? this : children.get(i - 1);
                List<Vec3d> parentHistory = ((PositionHistoryProvider) parent).getHistory();

                int j;
                for (j = 0; j < parentHistory.size(); j++) {
                    Vec3d history = parentHistory.get(j);
                    if(Math.sqrt(history.squaredDistanceTo(parent.getX(), parent.getY(), parent.getZ())) >= 12) {
                        child.updatePosition(history.getX(), history.getY(), history.getZ());

                        Vec3d towardsParent = parent.getPos().subtract(child.getPos()).normalize();
                        child.setPitch((float)(MathHelper.atan2(towardsParent.horizontalLength(), towardsParent.getY()) * 57.2957763671875) - 90.0f);
                        break;
                    }
                }

                for (int m = Math.min(parentHistory.size(), j + 1); m < parentHistory.size(); m++) {
                    parentHistory.remove(parentHistory.get(m));
                }
            }
        }

        if(!world.isClient) {
            setVelocity(0, 0, 0.1);
            setVelocity(getVelocity().add(0, Math.sin(age / 100f) * 1, 0));
            setVelocity(getVelocity().normalize().multiply(5));
            velocityModified = true;
        }
    }

    @Override
    public void startTracking(ServerEntityManager<EnvoyEntity> manager) {
        for (int i = 0; i < children.size(); i++) {
            EnvoySegmentEntity segment = children.get(i);
            segment.updatePosition(getX(), getY(), getZ() - 5 * i);
            world.spawnEntity(segment);
        }
    }

    public List<EnvoySegmentEntity> getChildren() {
        return children;
    }

    @Override
    public void readFromPacket(MobSpawnS2CPacket packet) {
        super.readFromPacket(packet);

        for (EnvoySegmentEntity segment : children) {
            segment.setId(packet.getId());
        }
    }

    @Override
    public boolean cannotDespawn() {
        return true;
    }
}
