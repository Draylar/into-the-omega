package draylar.intotheomega.registry;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import draylar.attributed.event.CriticalHitEvents;
import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.api.BewitchedHelper;
import draylar.intotheomega.api.EndBiomeSourceCache;
import draylar.intotheomega.api.event.*;
import draylar.intotheomega.api.item.*;
import draylar.intotheomega.item.api.SetArmorItem;
import draylar.intotheomega.item.dragon.InanisItem;
import draylar.intotheomega.item.ice.ChilledVoidArmorItem;
import draylar.intotheomega.item.matrix.MatrixCharmItem;
import draylar.intotheomega.item.matrix.MatrixLensItem;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.Optional;

public class OmegaEventHandlers {

    public static void init() {
        registerMatrixCharmDamageReduction();
        registerMatrixLensDamageBoost();
        registerIceIslandLocationUpdater();
        registerDungeonLockHandlers();
        registerBewitchedEndermanAggression();
        registerItemAttackHandler();
        registerChilledVoidBonuses();
        registerTrinketEventHandler();
        registerInanisLeapAttack();
        registerHeartOfIceCriticalHandlers();
        registerItemHandlers();
        ServerWorldEvents.LOAD.register(new EndBiomeSourceCache());
    }

    private static void registerItemHandlers() {
        CriticalHitEvents.CALCULATE_MODIFIER.register((player, target, stack, modifier) -> {
            if(player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof CriticalItem criticalModifier) {
                return criticalModifier.modifyCriticalDamage(player, target, stack, Hand.MAIN_HAND, modifier);
            }

            if(player.getStackInHand(Hand.OFF_HAND).getItem() instanceof CriticalItem criticalModifier) {
                return criticalModifier.modifyCriticalDamage(player, target, stack, Hand.OFF_HAND, modifier);
            }

            return modifier;
        });

        CriticalHitEvents.AFTER.register((player, target, stack) -> {
            if(player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof CriticalItem criticalModifier) {
                criticalModifier.afterCriticalHit(player, target, stack, Hand.MAIN_HAND);
            }

            if(player.getStackInHand(Hand.OFF_HAND).getItem() instanceof CriticalItem criticalModifier) {
                criticalModifier.afterCriticalHit(player, target, stack, Hand.OFF_HAND);
            }
        });

        BatchAttackEvents.AFTER.register((player, data) -> {
            for (ItemStack stack : IntoTheOmega.collectListeningItems(player)) {
                if(stack.getItem() instanceof BatchDamageHandler handler) {
                    handler.afterBatchDamage(player.world, player, stack, data);
                }
            }
        });

        BatchAttackEvents.BEFORE.register((player, data) -> {
            for (ItemStack stack : IntoTheOmega.collectListeningItems(player)) {
                if(stack.getItem() instanceof BatchDamageHandler handler) {
                    handler.beforeBatchDamage(player.world, player, stack, data);
                }
            }
        });
    }

    private static void registerHeartOfIceCriticalHandlers() {
        // A player with a "Heart of Ice" equipped is immune to critical hits.
        CriticalHitEvents.BEFORE.register((player, target, stack, chance) -> {
            Optional<TrinketComponent> trinkets = TrinketsApi.getTrinketComponent(player);
            if(trinkets.isPresent()) {
                if(!trinkets.get().getEquipped(OmegaItems.HEART_OF_ICE).isEmpty()) {
                    return TypedActionResult.fail(chance);
                }
            }

            return TypedActionResult.pass(chance);
        });

        // When a player with a "Heart of Ice" equipped lands a critical hit, they are granted speed & strength.
        CriticalHitEvents.AFTER.register((player, target, stack) -> {
            Optional<TrinketComponent> trinkets = TrinketsApi.getTrinketComponent(player);
            if(trinkets.isPresent()) {
                if(!trinkets.get().getEquipped(OmegaItems.HEART_OF_ICE).isEmpty()) {
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 20 * 5, 0, true, false));
                    player.addStatusEffect(new StatusEffectInstance(OmegaStatusEffects.PURE_STRENGTH, 20 * 5, 0, true, false));
                }
            }
        });
    }

    private static void registerInanisLeapAttack() {
        EntityJumpCallback.EVENT.register((entity, velocity) -> {
            if(entity instanceof PlayerEntity && entity.getMainHandStack().getItem() instanceof InanisItem) {
                if(entity.isUsingItem()) {
                    entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 20 * 3, 0, true, false));
                    return TypedActionResult.success(velocity * 3);
                }
            }

            return TypedActionResult.pass(velocity);
        });
    }

    private static void registerIceIslandLocationUpdater() {
        // TODO: BRUH
        ServerTickEvents.START_WORLD_TICK.register(world -> {
            if(world.getRegistryKey().equals(World.END)) {
                world.getPlayers().forEach(player -> {
//                    boolean inIceIsland = world.getStructureAccessor().getStructureAt(player.getBlockPos(), OmegaWorld.ICE_ISLAND) != StructureStart.DEFAULT;

                    // todo: what if a player is in the ice island and they teleport away?
                    // they will still see snow.
//                    ServerNetworking.updateIceIsland(player, inIceIsland);
                });
            }
        });
    }

    private static void registerMatrixCharmDamageReduction() {
        PlayerDamageCallback.EVENT.register((player, source, amount) -> {
            if(source.isExplosive()) {
                Optional<TrinketComponent> trinkets = TrinketsApi.getTrinketComponent(player);
                if(trinkets.isPresent()) {
                    var equipped = trinkets.get().getEquipped(OmegaItems.MATRIX_CHARM);
                    if(!equipped.isEmpty()) {
                        float reduction = MatrixCharmItem.getDamageReductionPercentage(equipped.get(0).getRight());
                        return TypedActionResult.pass(reduction * amount);
                    }
                }
            }

            return TypedActionResult.pass(amount);
        });
    }

    private static void registerMatrixLensDamageBoost() {
        ExplosionDamageEntityCallback.EVENT.register((entity, source, amount) -> {
            if(source.getAttacker() instanceof PlayerEntity player) {
                Optional<TrinketComponent> trinkets = TrinketsApi.getTrinketComponent(player);
                if(trinkets.isPresent()) {
                    var equipped = trinkets.get().getEquipped(OmegaItems.MATRIX_LENS);
                    if(!equipped.isEmpty()) {
                        float boost = MatrixLensItem.getExplosiveDamageBoost(equipped.get(0).getRight());
                        return TypedActionResult.pass(boost * amount);
                    }
                }
            }

            return TypedActionResult.pass(amount);
        });
    }

    private static void registerDungeonLockHandlers() {
        AttackBlockCallback.EVENT.register((player, world, hand, blockPos, direction) -> {
            if(player.hasStatusEffect(OmegaStatusEffects.DUNGEON_LOCK)) {
                return ActionResult.FAIL;
            }

            return ActionResult.PASS;
        });

        PlayerBlockBreakEvents.BEFORE.register((world, player, blockPos, blockState, blockEntity) -> !player.hasStatusEffect(OmegaStatusEffects.DUNGEON_LOCK));
    }

    private static void registerBewitchedEndermanAggression() {
        AttackEntityCallback.EVENT.register((player, world, hand, entity, entityHitResult) -> {
            if(entity instanceof LivingEntity) {
                world.getEntitiesByClass(EndermanEntity.class, new Box(player.getBlockPos().add(-128, -64, -128), player.getBlockPos().add(128, 64, 128)), enderman -> BewitchedHelper.isBewitchedTo(enderman, player)).forEach(enderman -> {
                    enderman.setTarget((LivingEntity) entity);
                    enderman.setAngryAt(entity.getUuid());
                    enderman.setAngerTime(20 * 10);
                });
            }

            return ActionResult.PASS;
        });
    }

    private static void registerItemAttackHandler() {
        AttackEntityCallback.EVENT.register((player, world, hand, entity, entityHitResult) -> {
            // If the player is holding an AttackHandler item, trigger the "onAttack" method now.
            if(entity instanceof LivingEntity && player.getStackInHand(hand).getItem() instanceof AttackHandler) {
                ((AttackHandler) player.getStackInHand(hand).getItem()).onAttack(world, player, player.getStackInHand(hand), (LivingEntity) entity);
            }

            return ActionResult.PASS;
        });
    }

    private static void registerTrinketEventHandler() {
        // If the player has a TrinketEventHandler Trinket equipped, trigger the "onAttackEnemy" method.
        AttackEntityCallback.EVENT.register((player, world, hand, entity, entityHitResult) -> {
            TrinketsApi.getTrinketComponent(player).ifPresent(trinkets -> {
                trinkets.getEquipped(stack -> stack.getItem() instanceof TrinketEventHandler).forEach(trinketEntry -> {
                    ((TrinketEventHandler) trinketEntry.getRight().getItem()).onAttackEnemy(trinketEntry.getRight(), player, (LivingEntity) entity);
                });
            });

            return ActionResult.PASS;
        });

        // Run Critical Hit bonus modifier checks on Trinkets
        CriticalHitEvents.CALCULATE_MODIFIER.register((player, target, stack, modifier) -> {
            double bonus = 0.0;

            Optional<TrinketComponent> trinkets = TrinketsApi.getTrinketComponent(player);
            if(trinkets.isPresent()) {
                for (Pair<SlotReference, ItemStack> eventHandlerEntry : trinkets.get().getEquipped(s -> s.getItem() instanceof TrinketEventHandler)) {
                    bonus += ((TrinketEventHandler) eventHandlerEntry.getRight().getItem()).getCriticalChanceBonusAgainst((LivingEntity) target);
                }
            }

            return modifier + bonus;
        });

        // TrinketEventHandler trinkets can supply a multiplicative damage boost to the player through the getDamageBonusAgainst method.
        // Calculate the modifier of all trinkets and apply the damage boost when the player hits an enemy.
        PlayerAttackCallback.EVENT.register((hit, player, amount) -> {
            float multiplier = 1.0f;

            Optional<TrinketComponent> trinkets = TrinketsApi.getTrinketComponent(player);
            if(trinkets.isPresent()) {
                for (Pair<SlotReference, ItemStack> eventHandlerEntry : trinkets.get().getEquipped(stack -> stack.getItem() instanceof TrinketEventHandler)) {
                    multiplier *= ((TrinketEventHandler) eventHandlerEntry.getRight().getItem()).getDamageMultiplierAgainst((LivingEntity) hit);
                }
            }

            return amount * multiplier;
        });

        // TrinketEventHandler trinkets can supply a raw damage bonus to the player through the getDamageBonusAgainst method.
        // Collect all trinkets & sum their damage bonus when the player hits an enemy.
        PlayerAttackCallback.EVENT.register((hit, player, amount) -> {
            float bonus = 0.0f;

            Optional<TrinketComponent> trinkets = TrinketsApi.getTrinketComponent(player);
            if(trinkets.isPresent()) {
                for (Pair<SlotReference, ItemStack> eventHandlerEntry : trinkets.get().getEquipped(stack -> stack.getItem() instanceof TrinketEventHandler)) {
                    bonus += ((TrinketEventHandler) eventHandlerEntry.getRight().getItem()).getDamageBonusAgainst((LivingEntity) hit);
                }
            }

            return amount + bonus;
        });
    }

    private static void registerChilledVoidBonuses() {
        // Apply +15% bonus Critical Rate for the Chilled Void set
        CriticalHitEvents.BEFORE.register((player, target, stack, chance) -> {
            ItemStack equippedStack = player.getEquippedStack(EquipmentSlot.HEAD);
            if(equippedStack.getItem() instanceof ChilledVoidArmorItem) {
                SetArmorItem set = (SetArmorItem) equippedStack.getItem();

                // Only apply the bonus if the player has the full set equipped.
                if(set.hasFullSet(player)) {
                    double bonus = .15;

                    // If the target has the Abyssal Frostbite status, apply an additional .10 bonus.
                    if(target instanceof LivingEntity) {
                        if(((LivingEntity) target).hasStatusEffect(OmegaStatusEffects.ABYSSAL_FROSTBITE)) {
                            bonus += 0.10;
                        }
                    }

                    return TypedActionResult.pass(chance + bonus);
                }
            }

            return TypedActionResult.pass(chance);
        });

        // The Chilled Void set has a base +10% bonus on Critical Hits.
        CriticalHitEvents.CALCULATE_MODIFIER.register((player, target, stack, modifier) -> {
            ItemStack equippedStack = player.getEquippedStack(EquipmentSlot.HEAD);
            if(equippedStack.getItem() instanceof ChilledVoidArmorItem) {
                SetArmorItem set = (SetArmorItem) equippedStack.getItem();

                // Only apply the bonus if the player has the full set equipped.
                if(set.hasFullSet(player)) {
                    double bonus = .10;

                    // If the target has the Abyssal Frostbite status, apply an additional .05 bonus.
                    if(target instanceof LivingEntity) {
                        if(((LivingEntity) target).hasStatusEffect(OmegaStatusEffects.ABYSSAL_FROSTBITE)) {
                            bonus += 0.05;
                        }
                    }

                    return modifier + bonus;
                }
            }

            return modifier;
        });

        // When a player with the Chilled Void set lands a Critical Hit, display a particle shower.
        CriticalHitEvents.AFTER.register((player, target, stack) -> {
            ItemStack equippedStack = player.getEquippedStack(EquipmentSlot.HEAD);
            if(equippedStack.getItem() instanceof ChilledVoidArmorItem) {
                SetArmorItem set = (SetArmorItem) equippedStack.getItem();

                // Only show the effect if the player has the set.
                if(set.hasFullSet(player)) {
                    for (int i = 0; i < 25; i++) {
                        player.world.addParticle(
                                OmegaParticles.ICE_FLAKE,
                                target.getParticleX(0.5D),
                                target.getRandomBodyY() - 0.25D,
                                target.getParticleZ(0.5D),
                                (player.world.random.nextDouble() - 0.5D) * 2.0D,
                                -player.world.random.nextDouble(),
                                (player.world.random.nextDouble() - 0.5D) * 2.0D
                        );
                    }
                }
            }
        });
    }

    private OmegaEventHandlers() {
        // NO-OP
    }
}
