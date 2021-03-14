package draylar.intotheomega.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.List;
import java.util.Map;

public interface SetBonusProvider {
    Map<EquipmentSlot, Item> requiredItems();

    @Environment(EnvType.CLIENT)
    void appendSetBonusTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context);

    void tickSetBonus(PlayerEntity player, ItemStack stack);
}
