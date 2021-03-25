package draylar.intotheomega.api;

public class EndBiomeHelper {

    public static int distanceToZone(int x, int z) {
        int ax = (x / 2500) * 2500 + 1250;
        int az = (z / 2500) * 2500 + 1250;

        return (int) Math.sqrt(Math.pow(ax - x, 2) + Math.pow(az - z, 2));
    }
}
