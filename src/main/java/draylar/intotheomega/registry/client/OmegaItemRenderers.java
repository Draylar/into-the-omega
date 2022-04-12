package draylar.intotheomega.registry.client;

import draylar.intotheomega.client.item.MatriteOrbitalItemRenderer;
import draylar.intotheomega.client.item.NebulaGearItemRenderer;
import draylar.intotheomega.registry.OmegaItems;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;

public class OmegaItemRenderers {

    public static void initialize() {
        BuiltinItemRendererRegistry.INSTANCE.register(OmegaItems.MATRITE_ORBITAL, new MatriteOrbitalItemRenderer());
        BuiltinItemRendererRegistry.INSTANCE.register(OmegaItems.NEBULA_GEAR, new NebulaGearItemRenderer());
    }
}
