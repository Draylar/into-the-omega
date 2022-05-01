package draylar.intotheomega.entity.ai;

import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.PathAwareEntity;

public class HumanoidAttackGoal extends MeleeAttackGoal {

    private final PathAwareEntity mob;
    private int ticks;

    public HumanoidAttackGoal(PathAwareEntity zombie, double speed, boolean pauseWhenMobIdle) {
        super(zombie, speed, pauseWhenMobIdle);
        this.mob = zombie;
    }

    @Override
    public void start() {
        super.start();
        ticks = 0;
    }

    @Override
    public void stop() {
        super.stop();
        mob.setAttacking(false);
    }

    @Override
    public void tick() {
        super.tick();
        ticks++;
        mob.setAttacking(ticks >= 5 && getCooldown() < getMaxCooldown() / 2);
    }
}
