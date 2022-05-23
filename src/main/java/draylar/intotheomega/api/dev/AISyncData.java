package draylar.intotheomega.api.dev;

import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.ai.pathing.Path;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AISyncData {

    public static final Map<Integer, List<PrioritizedGoal>> DEVELOPMENT_AI_SYNC = new HashMap<>();
    public static final Map<Integer, Path> DEVELOPMENT_PATH_SYNC = new HashMap<>();
}
