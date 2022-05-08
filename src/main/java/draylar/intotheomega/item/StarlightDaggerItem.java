package draylar.intotheomega.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class StarlightDaggerItem extends Item {

    private static final ImmutableMultimap<EntityAttribute, EntityAttributeModifier> MODIFIERS = ImmutableMultimap
            .<EntityAttribute, EntityAttributeModifier>builder()
            .put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "damage", 15.0f, EntityAttributeModifier.Operation.ADDITION))
            .put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "swing", 0.5f, EntityAttributeModifier.Operation.MULTIPLY_BASE))
            .put(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "speed", 0.1f, EntityAttributeModifier.Operation.MULTIPLY_BASE))
            .build();

    public StarlightDaggerItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        tooltip.add(LiteralText.EMPTY);
        tooltip.add(new LiteralText("A dagger bathed in the light of the stars, ").formatted(Formatting.GRAY));
        tooltip.add(new LiteralText("   searching for illuminated destiny...").formatted(Formatting.GRAY));
        tooltip.add(LiteralText.EMPTY);
        tooltip.add(new LiteralText("Dual Starlight [SHIFT]").formatted(Formatting.LIGHT_PURPLE));

        if(Screen.hasShiftDown()) {
            tooltip.add(new LiteralText(" When wielding 2 Starlight Daggers,").formatted(Formatting.GRAY));
            tooltip.add(new LiteralText(" you gain a bonus damage effect.").formatted(Formatting.GRAY));
        }
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        if(slot == EquipmentSlot.MAINHAND) {
            return MODIFIERS;
        }

        return super.getAttributeModifiers(slot);
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return state.isOf(Blocks.COBWEB);
    }
}
