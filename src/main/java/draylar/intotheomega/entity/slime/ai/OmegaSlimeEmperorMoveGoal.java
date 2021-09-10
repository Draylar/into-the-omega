package draylar.intotheomega.entity.slime.ai;

import draylar.intotheomega.entity.slime.OmegaSlimeEmperorEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.SlimeEntity;

import java.util.EnumSet;

public class OmegaSlimeEmperorMoveGoal extends Goal {

    private final OmegaSlimeEmperorEntity slime;

    public OmegaSlimeEmperorMoveGoal(OmegaSlimeEmperorEntity slime) {
        this.slime = slime;
        this.setControls(EnumSet.of(Goal.Control.JUMP, Goal.Control.MOVE));
    }

    @Override
    public boolean canStart() {
        return !this.slime.hasVehicle();
    }

    @Override
    public void tick() {
        ((SlimeEntity.SlimeMoveControl) this.slime.getMoveControl()).move(1.0D);
    }
}