package draylar.intotheomega.entity.block;

import draylar.intotheomega.mixin.StatusEffectInstanceAccessor;
import draylar.intotheomega.registry.OmegaBlockEntities;
import draylar.intotheomega.registry.OmegaParticles;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.Tickable;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public class SwirledMixerBlockEntity extends BlockEntity implements BlockEntityClientSerializable, Tickable {

    private final DefaultedList<ItemStack> potions = DefaultedList.ofSize(3, ItemStack.EMPTY);
    private ItemStack catalyst = ItemStack.EMPTY;
    private boolean processing = false;
    private int processingTicks = 0;

    public SwirledMixerBlockEntity() {
        super(OmegaBlockEntities.SWIRLED_MIXER);
    }

    @Override
    public void tick() {
        if(world != null && !world.isClient) {

            // tick processing
            if(processing) {
                processingTicks++;
                if(processingTicks >= 20 * 5) {
                    processing = false;
                    processingTicks = 0;
                    ItemStack output = getOutput();

                    // clear both inventories
                    potions.clear();
                    catalyst = output;
                    sync();
                } else {
                    // SFX
                    ((ServerWorld) world).spawnParticles(OmegaParticles.OMEGA_SLIME, pos.getX() + 0.5f, pos.getY() + 1, pos.getZ() + 0.5f, 5, 0, 0, 0, 0);
                }
            }
        }
    }

    @Nullable
    public ItemStack getOutput() {
        // If the catalyst is slime, potions are being combined.
        if(catalyst.getItem().equals(Items.SLIME_BALL)) {

            // We can only process the input if:
            //    1. There is at least 1 potion
            //    2. All potions can be upgraded (ie. not already upgraded)

            // First, check for at least 1 potion:
            if(potions.stream().allMatch(ItemStack::isEmpty)) {
                return null;
            }

            // Second, ensure all potions can be limit-broken.
            for (ItemStack stack : potions) {
                Potion potion = PotionUtil.getPotion(stack);
                for (StatusEffectInstance effect : potion.getEffects()) {
                    int level = effect.getAmplifier();

                    // We assume anything >=3 has already been upgraded.
                    if(level >= 3) {
                        return null;
                    }
                }
            }

            // Requirements match. Return the final potion.
            Set<StatusEffectInstance> effects = new HashSet<>();
            for (ItemStack stack : potions) {
                Potion potion = PotionUtil.getPotion(stack);
                for (StatusEffectInstance effect : potion.getEffects()) {
                    ((StatusEffectInstanceAccessor) effect).setAmplifier(effect.getAmplifier() + 1);
                    effects.add(effect);
                }
            }

            ItemStack resultStack = new ItemStack(Items.POTION);
            PotionUtil.setCustomPotionEffects(resultStack, effects);
            return resultStack;
        }

        // If the catalyst is Omega Crystal, potions are being upgraded.
        // TODO: implement this!
        return null;
    }

    public void interact(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);

        // If we are currently processing, do not allow interactions.
        if(processing) {
            return;
        }

        // If the player is sneaking & there is a valid catalyst / potion combination, start the transformation.
        if(player.isSneaking()) {
            if(getOutput() != null) {
                processing = true;
                return;
            }
        }

        // If the player does not have an item in the given hand, extract from the mixer, starting with potions.
        if(stack.isEmpty()) {
            if(!catalyst.isEmpty()) {
                // Swap with the catalyst spot
                player.setStackInHand(hand, catalyst.copy());
                catalyst = ItemStack.EMPTY;
                sync();
            } else {
                // Take a potion
                for(int i = 0; i < potions.size(); i++) {
                    ItemStack potion = potions.get(i);
                    if(!potion.isEmpty()) {
                        player.setStackInHand(hand, potion.copy());
                        potions.set(i, ItemStack.EMPTY);
                        sync();
                        break;
                    }
                }
            }
        }

        // If the player has a potion in their hand, insert it.
        else if (stack.getItem() instanceof PotionItem) {
            for (int i = 0; i < potions.size(); i++) {
                ItemStack current = potions.get(i);
                if(current.isEmpty()) {
                    potions.set(i, stack.split(1));
                    sync();
                    break;
                }
            }
        }

        // Catalyst insertion
        else if (stack.getItem().equals(Items.SLIME_BALL)) {
            if(catalyst.isEmpty()) {
                catalyst = stack.split(1);
                sync();
            }
        }
    }

    public DefaultedList<ItemStack> getPotions() {
        return potions;
    }

    public ItemStack getCatalyst() {
        return catalyst;
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        Inventories.toTag(tag, potions);
        tag.put("Catalyst", catalyst.toTag(new CompoundTag()));
        return super.toTag(tag);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        potions.clear();
        Inventories.fromTag(tag, potions);
        catalyst = ItemStack.fromTag(tag.getCompound("Catalyst"));
        super.fromTag(state, tag);
    }

    @Override
    public void fromClientTag(CompoundTag tag) {
        fromTag(getCachedState(), tag);
    }

    @Override
    public CompoundTag toClientTag(CompoundTag tag) {
        return toTag(tag);
    }
}
