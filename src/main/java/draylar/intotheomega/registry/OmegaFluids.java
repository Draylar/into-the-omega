package draylar.intotheomega.registry;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.fluid.OmegaSlimeFluid;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.registry.Registry;

public class OmegaFluids {

    public static final FlowableFluid OMEGA_SLIME_STILL = (FlowableFluid) register("omega_slime", new OmegaSlimeFluid.Still());
    public static final FlowableFluid OMEGA_SLIME_FLOWING = (FlowableFluid) register("flowing_omega_slime", new OmegaSlimeFluid.Flowing());

    private OmegaFluids() {
        // NO-OP
    }

    private static Fluid register(String name, Fluid fluid) {
        return Registry.register(Registry.FLUID, IntoTheOmega.id(name), fluid);
    }

    public static void init() {
        // NO-OP
    }
}
