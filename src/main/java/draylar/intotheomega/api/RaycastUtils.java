package draylar.intotheomega.api;

import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.function.Predicate;

public class RaycastUtils {

    public static EntityHitResult raycast(Entity entity, Vec3d startPosition, Predicate<Entity> predicate, double distance) {
        Vec3d line = entity.getRotationVec(1.0F).multiply(distance);
        Vec3d endPosition = startPosition.add(line);
        Box box = entity.getBoundingBox().stretch(line).expand(1.0, 1.0, 1.0);
        return ProjectileUtil.raycast(entity, startPosition, endPosition, box, predicate, distance);
    }
}
