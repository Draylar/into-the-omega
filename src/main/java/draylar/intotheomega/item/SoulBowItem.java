package draylar.intotheomega.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import draylar.intotheomega.registry.OmegaEntityAttributes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;

import java.util.UUID;

public class SoulBowItem extends TrinketItem {

    public SoulBowItem(Settings settings) {
        super(settings);
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
        return ImmutableMultimap.<EntityAttribute, EntityAttributeModifier>builder()
                .put(OmegaEntityAttributes.RANGED_DAMAGE, new EntityAttributeModifier(uuid, "Soul bow boost", 0.25f, EntityAttributeModifier.Operation.MULTIPLY_TOTAL))
                .build();
    }
}
