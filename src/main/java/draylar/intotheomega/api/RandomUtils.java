package draylar.intotheomega.api;

import java.util.Random;

public class RandomUtils {

    public static float range(Random random, float variance) {
        return -variance + random.nextFloat(variance * 2);
    }
}
