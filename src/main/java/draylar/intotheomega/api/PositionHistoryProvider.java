package draylar.intotheomega.api;

import net.minecraft.util.math.Vec3d;

import java.util.List;

public interface PositionHistoryProvider {
    List<Vec3d> getHistory();
}
