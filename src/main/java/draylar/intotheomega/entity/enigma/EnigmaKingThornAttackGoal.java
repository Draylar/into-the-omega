package draylar.intotheomega.entity.enigma;

import draylar.intotheomega.entity.ObsidianThornEntity;
import draylar.intotheomega.registry.OmegaEntities;
import net.minecraft.entity.ai.goal.Goal;

public class EnigmaKingThornAttackGoal extends Goal {

    private final EnigmaKingEntity king;

    public EnigmaKingThornAttackGoal(EnigmaKingEntity king) {
        this.king = king;
    }

    @Override
    public boolean canStart() {
        return king.world.random.nextInt(200) == 0;
    }

    @Override
    public void start() {
        for (int i = 0; i < 10; i++) {
            ObsidianThornEntity thorn = new ObsidianThornEntity(OmegaEntities.OBSIDIAN_THORN, king.world);
            thorn.setPos(king.getOrigin().getX() + 20 - king.world.random.nextInt(40), king.getOrigin().getY(), king.getOrigin().getZ() + 20 - king.world.random.nextInt(40));
            king.world.spawnEntity(thorn);
        }
    }

    @Override
    public boolean shouldContinue() {
        return false;
    }
}
