package draylar.intotheomega;

import dev.emi.trinkets.api.SlotGroups;
import dev.emi.trinkets.api.Slots;
import dev.emi.trinkets.api.TrinketSlots;
import draylar.intotheomega.mixin.SimpleRegistryAccessor;
import draylar.intotheomega.registry.*;
import draylar.intotheomega.ui.ConquestForgeScreenHandler;
import draylar.intotheomega.world.OmegaCrystalOreFeature;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class IntoTheOmega implements ModInitializer {

    public static final String MODID = "intotheomega";
    public static final ItemGroup GROUP = FabricItemGroupBuilder.build(id("group"), () -> new ItemStack(OmegaItems.OMEGA_CRYSTAL));
    public static final String OMEGA = "Ω";
    public static final ScreenHandlerType<ConquestForgeScreenHandler> CF_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(id("conquest_forge"), ConquestForgeScreenHandler::new);
    public static final EntityGroup END_CREATURE = new EntityGroup();

    public static final Feature<DefaultFeatureConfig> OMEGA_ORE_FEATURE = Registry.register(Registry.FEATURE, id("ore"), new OmegaCrystalOreFeature(DefaultFeatureConfig.CODEC));

    @Override
    public void onInitialize() {
        OmegaEnchantments.init();
        OmegaBlocks.init();
        OmegaItems.init();
        OmegaEntities.init();
        OmegaWorld.init();
        OmegaTags.init();
        OmegaParticles.init();

        TrinketSlots.addSlot(SlotGroups.HAND, Slots.RING, new Identifier("trinkets", "textures/item/empty_trinket_slot_ring.png"));
    }

    public static Identifier id(String name) {
        return new Identifier("intotheomega", name);
    }

    public static Enchantment getOmegaVariant(Enchantment enchantment) {
        Identifier testID = Registry.ENCHANTMENT.getId(enchantment);

        if(testID != null) {
            Identifier potentialOmegaID = IntoTheOmega.id(String.format("omega_%s", testID.getPath()));
            if(((SimpleRegistryAccessor) Registry.ENCHANTMENT).getIdToEntry().containsKey(potentialOmegaID)) {
                return Registry.ENCHANTMENT.get(potentialOmegaID);
            }
        }

        return null;
    }
}
