package draylar.intotheomega.cca;

// TODO: re-implement
public class TelosComponent {

//    private static final String TELOS_KEY = "Telos";
//    private final PlayerEntity player;
//    private final List<TelosProvider> providers = new ArrayList<>();
//    private double perTick = 0.5;
//    private int maxTelos = 100;
//    private double telos = 0;
//
//    public TelosComponent(PlayerEntity player) {
//        this.player = player;
//    }
//
//    public void give(double telos) {
//        this.telos = Math.min(maxTelos, this.telos + telos);
//        OmegaComponents.TELOS.sync(player);
//    }
//
//    public void revoke(double telos) {
//        this.telos = Math.max(0, this.telos - telos);
//        OmegaComponents.TELOS.sync(player);
//    }
//
//    public double getTelos() {
//        return telos;
//    }
//
//    public int getMaxTelos() {
//        return maxTelos;
//    }
//
//    @Override
//    public void readFromNbt(NbtCompound tag) {
//        telos = tag.getDouble(TELOS_KEY);
//    }
//
//    @Override
//    public void writeToNbt(NbtCompound tag) {
//        tag.putDouble(TELOS_KEY, telos);
//    }
//
//
//    @Override
//    public void serverTick() {
//        give(perTick);
//    }
}
