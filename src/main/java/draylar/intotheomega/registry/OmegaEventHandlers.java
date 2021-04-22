package draylar.intotheomega.registry;

import dev.emi.trinkets.api.SlotGroups;
import dev.emi.trinkets.api.Slots;
import dev.emi.trinkets.api.TrinketsApi;
import draylar.intotheomega.api.BewitchedHelper;
import draylar.intotheomega.api.event.ExplosionDamageEntityCallback;
import draylar.intotheomega.api.event.PlayerDamageCallback;
import draylar.intotheomega.item.MatrixCharmItem;
import draylar.intotheomega.item.MatrixLensItem;
import draylar.intotheomega.network.ServerNetworking;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
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

    private OmegaEventHandlers() {
        // NO-OP
    }
}
