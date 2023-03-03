package draylar.intotheomega.api;

import java.util.Random;

public class RandomUtils {

    public static float range(Random random, float variance) {
        return -variance + random.nextFloat(variance * 2);
    }

    public static float outerRange(Random random, float minimumVariance, float maximumVariance) {
        return random.nextBoolean() ? -1 : 1 * (random.nextFloat() * maximumVariance + minimumVariance);
    }
}
