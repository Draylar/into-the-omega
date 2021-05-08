package draylar.intotheomega.registry;

import dev.emi.trinkets.api.SlotGroups;
import dev.emi.trinkets.api.Slots;
import dev.emi.trinkets.api.TrinketsApi;
import draylar.attributed.event.CriticalHitEvents;
import draylar.intotheomega.api.AttackHandler;
import draylar.intotheomega.api.BewitchedHelper;
import draylar.intotheomega.api.TrinketEventHandler;
import draylar.intotheomega.api.event.ExplosionDamageEntityCallback;
import draylar.intotheomega.api.event.PlayerAttackCallback;
import draylar.intotheomega.api.event.PlayerDamageCallback;
import draylar.intotheomega.item.ChilledVoidArmorItem;
import draylar.intotheomega.item.MatrixCharmItem;
import draylar.intotheomega.item.MatrixLensItem;
import draylar.intotheomega.item.api.SetArmorItem;
import draylar.intotheomega.item.ice.HeartOfIceItem;
import draylar.intotheomega.network.ServerNetworking;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class OmegaEventHandlers {

    public static void init() {
        registerMatrixCharmDamageReduction();
        registerMatrixLensDamageBoost();
        registerIceIslandLocationUpdater();
        registerDungeonLockHandlers();
        registerBewitchedEndermanAggression();
        registerItemAttackHandler();
        registerChilledVoidBonuses();
        registerHeartOfIceHandlers();
        registerTrinketEventHandler();
    }

    private static void registerIceIslandLocationUpdater() {
        ServerTickEvents.START_WORLD_TICK.register(world -> {
            if(world.getRegistryKey().equals(World.END)) {
                world.getPlayers().forEach(player -> {
                    boolean inIceIsland = world.getStructureAccessor().getStructureAt(player.getBlockPos(), false, OmegaWorld.ICE_ISLAND) != StructureStart.DEFAULT;

                    // todo: what if a player is in the ice island and they teleport away?
                    // they will still see snow.
                    ServerNetworking.updateIceIsland(player, inIceIsland);
                });
            }
        });
    }

    private static void registerMatrixCharmDamageReduction() {
        PlayerDamageCallback.EVENT.register((player, source, amount) -> {
            if(source.isExplosive()) {
                ItemStack stack = TrinketsApi.getTrinketComponent(player).getStack(SlotGroups.CHEST, Slots.NECKLACE);
                Item item = stack.getItem();

                if(item instanceof MatrixCharmItem) {
                    float reduction = MatrixCharmItem.getDamageReductionPercentage(stack);
                    return TypedActionResult.pass(reduction * amount);
                }
            }

            return TypedActionResult.pass(amount);
        });
    }

    private static void registerMatrixLensDamageBoost() {
        ExplosionDamageEntityCallback.EVENT.register((entity, source, amount) -> {
            if(source.getAttacker() instanceof PlayerEntity) {
                ItemStack stack = TrinketsApi.getTrinketComponent((PlayerEntity) source.getAttacker()).getStack(SlotGroups.OFFHAND, Slots.RING);
                Item item = stack.getItem();

                if (item instanceof MatrixLensItem) {
                    float boost = MatrixLensItem.getExplosiveDamageBoost(stack);
                    return TypedActionResult.pass(boost * amount);
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
            Inventory inventory = TrinketsApi.getTrinketsInventory(player);
            if(entity instanceof LivingEntity) {
                for (int i = 0; i < inventory.size(); i++) {
                    ItemStack stack = inventory.getStack(i);
                    if (stack.getItem() instanceof TrinketEventHandler) {
                        ((TrinketEventHandler) stack.getItem()).onAttackEnemy(stack, player, (LivingEntity) entity);
                    }
                }
            }

            return ActionResult.PASS;
        });

        // Run Critical Hit bonus modifier checks on Trinkets
        CriticalHitEvents.CALCULATE_MODIFIER.register((player, target, stack, modifier) -> {
            double bonus = 0.0;

            Inventory inventory = TrinketsApi.getTrinketsInventory(player);
            if(target instanceof LivingEntity) {
                for (int i = 0; i < inventory.size(); i++) {
                    ItemStack trinketStack = inventory.getStack(i);
                    if (trinketStack.getItem() instanceof TrinketEventHandler) {
                        bonus += ((TrinketEventHandler) trinketStack.getItem()).getCriticalChanceBonusAgainst((LivingEntity) target);
                    }
                }
            }

            return modifier + bonus;
        });

        // Calculate damage bonus multiplier
        PlayerAttackCallback.EVENT.register((hit, player, amount) -> {
            float multiplier = 1.0f;

            Inventory inventory = TrinketsApi.getTrinketsInventory(player);
            if(hit instanceof LivingEntity) {
                for (int i = 0; i < inventory.size(); i++) {
                    ItemStack trinketStack = inventory.getStack(i);
                    if (trinketStack.getItem() instanceof TrinketEventHandler) {
                        multiplier *= ((TrinketEventHandler) trinketStack.getItem()).getDamageMultiplierAgainst((LivingEntity) hit);
                    }
                }
            }

            return amount * multiplier;
        });

        // Run raw damage bonus
        PlayerAttackCallback.EVENT.register((hit, player, amount) -> {
            float bonus = 0.0f;

            Inventory inventory = TrinketsApi.getTrinketsInventory(player);
            if(hit instanceof LivingEntity) {
                for (int i = 0; i < inventory.size(); i++) {
                    ItemStack trinketStack = inventory.getStack(i);
                    if (trinketStack.getItem() instanceof TrinketEventHandler) {
                        bonus += ((TrinketEventHandler) trinketStack.getItem()).getDamageBonusAgainst((LivingEntity) hit);
                    }
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
                    for(int i = 0; i < 25; i++) {
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

    private static void registerHeartOfIceHandlers() {
        // Players with the "Heart of Ice" cannot be Critical Hit.
        CriticalHitEvents.BEFORE.register((player, target, stack, chance) -> {
            if(TrinketsApi.getTrinketComponent(player).getStack(SlotGroups.CHEST, Slots.NECKLACE).getItem() instanceof HeartOfIceItem) {
                return TypedActionResult.fail(0.0);
            }

            return TypedActionResult.pass(chance);
        });

        // Players with the "Heart of Ice" who land a Critical Hit gain a short buff.
        CriticalHitEvents.AFTER.register((player, target, stack) -> {
            if(TrinketsApi.getTrinketComponent(player).getStack(SlotGroups.CHEST, Slots.NECKLACE).getItem() instanceof HeartOfIceItem) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 20 * 5, 0, true, false));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 20 * 5, 0, true, false));
            }
        });
    }

    private OmegaEventHandlers() {
        // NO-OP
    }
}
