package draylar.intotheomega.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import draylar.intotheomega.api.RandomUtils;
import draylar.intotheomega.impl.item.SweepingParticleAttackHandler;
import draylar.intotheomega.registry.OmegaDamageSources;
import draylar.intotheomega.registry.OmegaParticles;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class QuasarFluxItem extends Item implements SweepingParticleAttackHandler {

    private static final ImmutableMultimap<EntityAttribute, EntityAttributeModifier> MODIFIERS = ImmutableMultimap
            .<EntityAttribute, EntityAttributeModifier>builder()
            .put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Quasar Flux damage modifier", 24, EntityAttributeModifier.Operation.ADDITION))
            .put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Quasar Flux speed modifier", -1.0f, EntityAttributeModifier.Operation.ADDITION))
            .build();

    public QuasarFluxItem(Settings settings) {
        super(settings);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BLOCK;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 20 * 5;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.setCurrentHand(hand);
        return TypedActionResult.pass(user.getStackInHand(hand));
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        super.usageTick(world, user, stack, remainingUseTicks);

        if(!world.isClient) {
            Vec3d slicePosition = user.getPos().add(RandomUtils.range(world.random, 5.0f), world.random.nextFloat(5), RandomUtils.range(world.random, 5.0f));
            ((ServerWorld) world).spawnParticles(getAttackParticleType(), slicePosition.getX(), slicePosition.getY(), slicePosition.getZ(), 0, 0, 0.0, 0, 0.0);
            double variance = 2;
            for (Entity target : world.getOtherEntities(user, new Box(slicePosition.getX() - variance, slicePosition.getY() - variance, slicePosition.getZ() - variance, slicePosition.getX() + variance, slicePosition.getY() + variance, slicePosition.getZ() + variance), it -> it instanceof Monster)) {
                if(target instanceof LivingEntity living) {
                    living.damage(OmegaDamageSources.quasarSlash(user), 5.0f);
                }
            }
        }
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        super.onStoppedUsing(stack, world, user, remainingUseTicks);

        if(!world.isClient) {
            if(user instanceof PlayerEntity player) {
                if(!player.isCreative()) {
                    player.getItemCooldownManager().set(this, 20 * 10);
                }
            }
        }
    }

    @Override
    public void onAttack(World world, PlayerEntity holder, ItemStack stack, LivingEntity target) {
        if (holder.world instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(getAttackParticleType(), target.getX(), target.getBodyY(0.5), target.getZ(), 0, 0, 0.0, 0, 0.0);
        }
    }

    @Override
    public DefaultParticleType getAttackParticleType() {
        return OmegaParticles.QUASAR_SLASH;
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        if(slot == EquipmentSlot.MAINHAND) {
            return MODIFIERS;
        }

        return super.getAttributeModifiers(slot);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        tooltip.add(LiteralText.EMPTY);
        tooltip.add(new LiteralText("Right-click to summon a §dnova of slashes§r").formatted(Formatting.GRAY));
        tooltip.add(new LiteralText("around you, heavily damaging").formatted(Formatting.GRAY));
        tooltip.add(new LiteralText("all foes in your path.").formatted(Formatting.GRAY));
    }
}
