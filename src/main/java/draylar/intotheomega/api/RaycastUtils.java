package draylar.intotheomega.api;

import net.minecraft.entity.Entity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.function.Predicate;

public class RaycastUtils {

    // todo: what does this do
    public static EntityHitResult raycast(Entity source, Vec3d from, Predicate<Entity> predicate, double distance) {
        Vec3d vec3d2 = source.getRotationVec(1.0F).multiply(distance);
        Vec3d to = from.add(vec3d2);
        World world = source.world;
        Entity hit = null;
        Vec3d hitPos = null;
        Box box = source.getBoundingBox().stretch(to).expand(1.0D);

        for (Entity potentialTarget : world.getOtherEntities(source, box, predicate)) {
            Box box2 = potentialTarget.getBoundingBox().expand(potentialTarget.getTargetingMargin());
            Optional<Vec3d> optional = box2.raycast(from, to);
            if (box2.contains(from)) {
                if (distance >= 0.0D) {
                    hit = potentialTarget;
                    hitPos = optional.orElse(from);
                    distance = 0.0D;
                }
            } else if (optional.isPresent()) {
                Vec3d vec3d4 = optional.get();
                double f = from.squaredDistanceTo(vec3d4);
                if (f < distance || distance == 0.0D) {
                    if (potentialTarget.getRootVehicle() == source.getRootVehicle()) {
                        if (distance == 0.0D) {
                            hit = potentialTarget;
                            hitPos = vec3d4;
                        }
                    } else {
                        hit = potentialTarget;
                        hitPos = vec3d4;
                        distance = f;
                    }
                }
            }
        }

        if (hit == null) {
            return null;
        }

        return new EntityHitResult(hit, hitPos);
    }
}
