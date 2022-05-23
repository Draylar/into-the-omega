package draylar.intotheomega.item.api;

import draylar.intotheomega.api.SafeClientPlayer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.List;

public abstract class SetArmorItem extends ArmorItem {

    private final String name;
    private final int lines;

    public SetArmorItem(ArmorMaterial material, EquipmentSlot slot, Settings settings, String name, int lines) {
        super(material, slot, settings);
        this.name = name;
        this.lines = lines;
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        if(world != null && world.isClient) {
            PlayerEntity player = SafeClientPlayer.get();
            if(player == null) {
                return;
            }

            if(!hasFullSet(player)) {
                return;
            }

            tooltip.add(new LiteralText(" "));
            tooltip.add(new TranslatableText("intotheomega.set_bonus").formatted(Formatting.LIGHT_PURPLE));
            for (int i = 0; i < lines; i++) {
                tooltip.add(new LiteralText(" ").append(new TranslatableText(String.format("intotheomega.%s.%s", name, i)).formatted(Formatting.GRAY)));
            }
        }
    }

    public boolean hasFullSet(LivingEntity entity) {
        for (ItemStack armor : entity.getArmorItems()) {
            if (!(this.getClass().isInstance(armor.getItem()))) {
                return false;
            }
        }

        return true;
    }

    public static boolean hasFullSet(Item setItem, LivingEntity player) {
        for (ItemStack armor : player.getArmorItems()) {
            if (!(setItem.getClass().isInstance(armor.getItem()))) {
                return false;
            }
        }

        return true;
    }

    public List<StatusEffectInstance> getEffects() {
        return Collections.emptyList();
    }
}
