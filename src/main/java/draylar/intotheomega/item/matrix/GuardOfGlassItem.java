package draylar.intotheomega.item.matrix;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import draylar.intotheomega.IntoTheOmega;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

public class GuardOfGlassItem extends TrinketItem {

    public static final int MAX_SHIELD_COUNT = 3;

    public GuardOfGlassItem(Settings settings) {
        super(settings);
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        super.tick(stack, slot, entity);

        // debug
        if(!entity.world.isClient && FabricLoader.getInstance().isDevelopmentEnvironment()) {
            if(entity instanceof PlayerEntity player) {
                player.sendMessage(new LiteralText(player.getPlayerData(IntoTheOmega.VOID_MATRIX_SHIELD_STATUS) + " stacks left"), true);
            }
        }

        if(!entity.world.isClient && entity.age % 20 == 0) {
            if(entity instanceof ServerPlayerEntity player) {
                ItemCooldownManager cooldown = player.getItemCooldownManager();
                if(!cooldown.isCoolingDown(this) && player.getPlayerData(IntoTheOmega.VOID_MATRIX_SHIELD_STATUS) < MAX_SHIELD_COUNT) {
                    player.incrementPlayerData(IntoTheOmega.VOID_MATRIX_SHIELD_STATUS);
                    player.sync(IntoTheOmega.VOID_MATRIX_SHIELD_STATUS);

                    // re-set cooldown & reduce item durability
                    // TODO: make this CD reset when the user takes damage
                    cooldown.set(this, 50);
                    stack.damage(1, entity.getRandom(), player);
                }
            }
        }
    }
}
