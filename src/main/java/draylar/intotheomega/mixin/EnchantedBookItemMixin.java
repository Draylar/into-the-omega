package draylar.intotheomega.mixin;

import draylar.intotheomega.IntoTheOmega;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mixin(EnchantedBookItem.class)
public abstract class EnchantedBookItemMixin {

    @Shadow
    public static ItemStack forEnchantment(EnchantmentLevelEntry info) {
        return null;
    }

    @Inject(
            method = "appendStacks",
            at = @At("RETURN")
    )
    private void removeOmegaBooks(ItemGroup group, DefaultedList<ItemStack> stacks, CallbackInfo ci) {
        List<ItemStack> added = new ArrayList<>();

        if(group != ItemGroup.SEARCH && group != IntoTheOmega.GROUP) {
            stacks.forEach(stack -> {
                Map<Enchantment, Integer> enchantments = EnchantmentHelper.get(stack);

                for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
                    Enchantment enchantment = entry.getKey();

                    // if namespace is 'intotheomega', remove
                    if (omega_getID(enchantment).getNamespace().equals(IntoTheOmega.MODID)) {
                        added.add(stack);
                        break;
                    }
                }
            });

            stacks.removeAll(added);
        }
    }

    @Inject(
            method = "appendStacks",
            at = @At("HEAD")
    )
    private void addStacksToGroup(ItemGroup group, DefaultedList<ItemStack> stacks, CallbackInfo ci) {
        if (group.equals(IntoTheOmega.GROUP)) {
            Registry.ENCHANTMENT.forEach(enchantment -> {
                if(omega_getID(enchantment).getNamespace().equals(IntoTheOmega.MODID)) {
                    stacks.add(forEnchantment(new EnchantmentLevelEntry(enchantment, enchantment.getMaxLevel())));
                }
            });
        }
    }

    @Unique
    public Identifier omega_getID(Enchantment enchantment) {
        return Registry.ENCHANTMENT.getId(enchantment);
    }
}
