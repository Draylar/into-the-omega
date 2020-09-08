package draylar.intotheomega.mixin;

import com.mojang.authlib.GameProfile;
import dev.emi.trinkets.api.PlayerTrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import draylar.intotheomega.impl.DoubleJumpProvider;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {

    @Shadow public Input input;

    private int availableJumps = 0;
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
            availableJumps = 0;
            canDoubleJump = false;

            // check inventory for jump-providing items
            Inventory inventory = TrinketsApi.getTrinketsInventory(this);
            for(int i = 0; i < inventory.size(); i++) {
                ItemStack stack = inventory.getStack(i);

                // Add available double-jumps from item
                if(stack.getItem() instanceof DoubleJumpProvider) {
                    availableJumps += ((DoubleJumpProvider) stack.getItem()).getDoubleJumps(stack);
                }
            }
        }

        // Track jump un-press
        else if(!input.jumping) {
            canDoubleJump = true;
        }

        // Only allow another jump when it has been unpressed since the player left the ground
        else if(canDoubleJump && availableJumps > 0) {
            jump();
            availableJumps--;
            canDoubleJump = false;
        }
    }
}
