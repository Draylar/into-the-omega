package draylar.intotheomega.entity.ai;

import net.minecraft.entity.ai.control.LookControl;
import net.minecraft.entity.mob.MobEntity;

public class TrueEyeLookControl extends LookControl {

    public TrueEyeLookControl(MobEntity entity) {
        super(entity);
    }

    @Override
    public boolean shouldStayHorizontal() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
    }
}
