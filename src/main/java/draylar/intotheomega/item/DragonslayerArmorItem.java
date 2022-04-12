package draylar.intotheomega.item;

import draylar.intotheomega.api.item.SetBonusProvider;
import draylar.intotheomega.material.DragonslayerArmorMaterial;
import draylar.intotheomega.registry.OmegaItems;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DragonslayerArmorItem extends ArmorItem implements SetBonusProvider {

    private static final HashMap<EquipmentSlot, Item> REQUIRED_FOR_SET = new HashMap<>();

    public DragonslayerArmorItem(EquipmentSlot slot, Settings settings) {
        super(DragonslayerArmorMaterial.INSTANCE, slot, settings);

        REQUIRED_FOR_SET.put(EquipmentSlot.HEAD, OmegaItems.DRAGONSLAYER_HELMET);
        REQUIRED_FOR_SET.put(EquipmentSlot.CHEST, OmegaItems.DRAGONSLAYER_CHESTPLATE);
        REQUIRED_FOR_SET.put(EquipmentSlot.LEGS, OmegaItems.DRAGONSLAYER_LEGGINGS);
        REQUIRED_FOR_SET.put(EquipmentSlot.FEET, OmegaItems.DRAGONSLAYER_BOOTS);
    }

    @Override
    public Map<EquipmentSlot, Item> requiredItems() {
        return REQUIRED_FOR_SET;
    }

    @Override
    public void appendSetBonusTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new LiteralText("+15% damage against dragons").formatted(Formatting.BLUE));
    }

    @Override
    public void tickSetBonus(PlayerEntity player, ItemStack stack) {

    }
}
