package draylar.intotheomega.mixin;

import com.mojang.authlib.GameProfile;
import dev.emi.trinkets.api.TrinketsApi;
import draylar.intotheomega.impl.DoubleJumpTrinket;
import draylar.intotheomega.registry.client.OmegaClientPackets;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {

    @Shadow public Input input;

    @Shadow @Final protected MinecraftClient client;
    private final List<Pair<ItemStack, Integer>> doubleJumpProviders = new ArrayList<>();
    private boolean canDoubleJump = false;

    private ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Inject(
            method = "tickMovement",
            at = @At("HEAD")
    )
    private void doubleJump(CallbackInfo ci) {
        // Reset available jumps if the user is on the ground or climbing
        if(isOnGround() || isClimbing()) {
            doubleJumpProviders.clear();
            canDoubleJump = false;

            // check inventory for jump-providing items
            TrinketsApi.getTrinketComponent(client.player).ifPresent(component -> {
                component.getEquipped(stack -> stack.getItem() instanceof DoubleJumpTrinket).forEach(pair -> {
                    ItemStack stack = pair.getRight();
                    if(stack.getItem() instanceof DoubleJumpTrinket doubleJumpTrinket) {
                        for(int i = 0; i < doubleJumpTrinket.getDoubleJumps(stack); i++) {
                            doubleJumpProviders.add(new Pair<>(stack, pair.getLeft().index()));
                        }
                    }
                });
            });
        }

        // Track jump un-press
        else if(!input.jumping) {
            canDoubleJump = true;
        }

        // Only allow another jump when it has been unpressed since the player left the ground
        else if(canDoubleJump && !doubleJumpProviders.isEmpty()) {
            jump();
            Pair<ItemStack, Integer> stack = doubleJumpProviders.remove(0);
            canDoubleJump = false;

            // Notify server for jump item method
            OmegaClientPackets.doubleJump(stack.getRight());
            if(stack.getLeft().getItem() instanceof DoubleJumpTrinket doubleJumpTrinket) {
                doubleJumpTrinket.onDoubleJump(this.world, this, stack.getLeft());
            }
        }
    }
}
