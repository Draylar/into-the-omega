package draylar.intotheomega.entity.envoy;

import draylar.intotheomega.api.PositionHistoryProvider;
import draylar.intotheomega.registry.OmegaEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.ArrayList;
import java.util.List;

public class EnvoySegmentEntity extends Entity implements PositionHistoryProvider, IAnimatable {

    private static final TrackedData<Integer> ENVOY_LINK_INDEX = DataTracker.registerData(EnvoySegmentEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private final AnimationFactory factory = new AnimationFactory(this);
    private final List<Vec3d> positionHistory = new ArrayList<>();
    private final EnvoyEntity parent;
    private EnvoySegmentEntity next;

    public EnvoySegmentEntity(EnvoyEntity parent, World world) {
        super(OmegaEntities.ENVOY_SEGMENT, world);
        this.parent = parent;
    }

    @Override
    public void initDataTracker() {
        dataTracker.startTracking(ENVOY_LINK_INDEX, 0);
    }

    public void setLinkIndex(int index) {
        dataTracker.set(ENVOY_LINK_INDEX, index);
    }

    public int getLinkIndex() {
        return dataTracker.get(ENVOY_LINK_INDEX);
    }

    @Override
    public boolean shouldRender(double distance) {
        return true;
    }

    @Override
    public void tick() {
//        super.tick();

        if(next != null) {

        }

        // todo: clear if too big to prevent memory leak
        if(!world.isClient) {
            positionHistory.add(0, getPos());
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if(isInvulnerableTo(source)) {
            return false;
        }

        return super.damage(source, amount);
//        return parent.damagePart(this, source, amount);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {

    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {

    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    public void setNext(EnvoySegmentEntity nextSegment) {
        next = nextSegment;
    }

    @Override
    public List<Vec3d> getHistory() {
        return positionHistory;
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    public void registerControllers(AnimationData animationData) {

    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
