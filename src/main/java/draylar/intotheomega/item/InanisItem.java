package draylar.intotheomega.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import draylar.intotheomega.entity.InanisEntity;
import draylar.intotheomega.registry.OmegaEntities;
import draylar.intotheomega.registry.OmegaItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

import java.util.List;

public class InanisItem extends Item {

    private final float attackDamage = 8;
    private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;
    private final String description;

    public InanisItem(Settings settings, String description) {
        super(settings);
        this.description = description;

        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", this.attackDamage, EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", -2.4f, EntityAttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    // when holding, lightning particle sparks come out back
    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        super.usageTick(world, user, stack, remainingUseTicks);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        int ticksUsed = getMaxUseTime(stack) - remainingUseTicks;

        // Inanis has to be used for at least 1 second
        if(ticksUsed > 20) {
            InanisEntity inanis = new InanisEntity(OmegaEntities.INANIS, world, user, stack);
            inanis.setProperties(user, user.pitch, user.yaw, 0.0F, 4F, 0.0F); // last param = variance/divergence, modifierZ = speed
            world.spawnEntity(inanis);
            world.playSound(null, user.getBlockPos(), SoundEvents.ITEM_TRIDENT_THROW, SoundCategory.PLAYERS, 1, 0);

            if (user instanceof PlayerEntity) {
                ((PlayerEntity) user).getItemCooldownManager().set(OmegaItems.INANIS, 20 * 3);
            }
        }
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.setCurrentHand(hand);
        return TypedActionResult.consume(itemStack);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        tooltip.add(new TranslatableText(String.format("intotheomega.%s.1", description)).formatted(Formatting.GRAY));
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(slot);
    }
}
