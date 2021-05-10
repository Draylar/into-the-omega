package draylar.intotheomega.item.slime;

import draylar.intotheomega.registry.OmegaStatusEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;

public class OmegaSlimeSlayerItem extends SwordItem {

    public OmegaSlimeSlayerItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.addStatusEffect(new StatusEffectInstance(OmegaStatusEffects.SWIRLED, 20 * 5, 0));
        return super.postHit(stack, target, attacker);
    }
}
