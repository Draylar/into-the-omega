package draylar.intotheomega.api.entity;

import net.minecraft.entity.ai.goal.Goal;

public interface AIDebug {

    boolean debugGoal(Goal goal);

    void consumeGoalDebug();

    void markDebugGoal(Class<? extends Goal> goalClass);
}
