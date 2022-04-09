package draylar.intotheomega.ui;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.impl.OmegaManipulator;
import draylar.intotheomega.registry.OmegaItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;

public class ConquestForgeScreenHandler extends ScreenHandler {

    public final static Map<Integer, Integer> crystalCosts = new HashMap<>();

    static {
        crystalCosts.put(0, 4); // max level standard enchanting books -> level 1 omega book
        crystalCosts.put(1, 8); // 2x level 1 omega book
        crystalCosts.put(2, 16);
        crystalCosts.put(3, 32);
        crystalCosts.put(4, 48);
        crystalCosts.put(5, 64);
    }

    private int crystalCost = 0;
    public final Inventory output = new CraftingResultInventory();
    public final Inventory input = new SimpleInventory(3) {
        @Override
        public void markDirty() {
            super.markDirty();
            ConquestForgeScreenHandler.this.onContentChanged(this);
        }
    };

    public ConquestForgeScreenHandler(int syncId, PlayerInventory inventory) {
        super(IntoTheOmega.CF_SCREEN_HANDLER, syncId);

        // book inputs
        this.addSlot(new Slot(this.input, 0, 33, 97) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.getItem() instanceof EnchantedBookItem || stack.hasEnchantments();
            }
        });
        this.addSlot(new Slot(this.input, 1, 141, 97) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.getItem() instanceof EnchantedBookItem || stack.hasEnchantments();
            }
        });

        // omega crystal input
        this.addSlot(new Slot(this.input, 2, 87, 43) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.getItem().equals(OmegaItems.OMEGA_CRYSTAL);
            }
        });

        // result slot
        this.addSlot(new Slot(this.output, 2, 87, 97) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }

            @Override
            public ItemStack takeStack(int amount) {
                input.getStack(0).decrement(1);
                input.getStack(1).decrement(1);
                input.getStack(2).decrement(crystalCost); // amount of omega crystals to take

//                player.playSound(SoundEvents.BLOCK_ANVIL_USE, 1, .5f);
                
                return super.takeStack(amount);
            }
        });

        // main inventory
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 15 + j * 18, 147 + i * 18));
            }
        }

        // hotbar
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(inventory, i, 15 + i * 18, 205));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        super.onContentChanged(inventory);

        if(inventory == this.input) {
            updateOutput();
        }
    }

    private void updateOutput() {
        ItemStack inputLeft = input.getStack(0);
        ItemStack inputRight = input.getStack(1);
        ItemStack inputOmega = input.getStack(2);

        Item inputLeftItem = inputLeft.getItem();
        Item inputRightItem = inputRight.getItem();
        Item inputOmegaItem = inputOmega.getItem();

        // both items are enchanting books, combine first enchantment that can be combined
        for (Map.Entry<Enchantment, Integer> e : EnchantmentHelper.get(inputLeft).entrySet()) {
            Enchantment leftEnchantment = e.getKey();
            Integer leftLevel = e.getValue();

            for (Map.Entry<Enchantment, Integer> entry : EnchantmentHelper.get(inputRight).entrySet()) {
                Enchantment rightEnchantment = entry.getKey();
                Integer rightLevel = entry.getValue();

                // enchantments & name match
                if(leftEnchantment.equals(rightEnchantment) && leftLevel.equals(rightLevel)) {
                    int level = leftLevel;
                    int maxLevel = leftEnchantment.getMaxLevel();
                    boolean isOmega = ((OmegaManipulator) leftEnchantment).isOmega();

                    // Attempts to combine 2 max-level non-omega enchanting books into a tier 1 omega book.
                    if(!isOmega) {
                        // ensure both inputs are enchanting books
                        if(inputLeftItem instanceof EnchantedBookItem && inputRightItem instanceof EnchantedBookItem) {
                            // ensure the level we're operating on for both books is the max level of the given enchantment
                            if(level == maxLevel) {
                                Enchantment omegaVariant = IntoTheOmega.getOmegaVariant(leftEnchantment);

                                // if an omega enchantment was found for these books, populate output
                                if(omegaVariant != null) {
                                    crystalCost = crystalCosts.get(1);
                                    if(inputOmega.getCount() >= crystalCosts.get(1)) {
                                        ItemStack newStack = new ItemStack(Items.ENCHANTED_BOOK);
                                        EnchantedBookItem.addEnchantment(newStack, new EnchantmentLevelEntry(omegaVariant, 1));
                                        output.setStack(0, newStack);
                                        return;
                                    }
                                }
                            }
                        }
                    }

                    // Omega enchantment combination.
                    // Combination can occur between 2 books, or 1 book and 1 tool.
                    else {
                        // ensure at least 1 input is an enchanting book
                        if(inputLeftItem instanceof EnchantedBookItem || inputRightItem instanceof EnchantedBookItem) {
                            // make sure we're not over-leveling the tool/book
                            if(level < maxLevel) {
                                ItemStack newStack = new ItemStack(Items.ENCHANTED_BOOK);

                                // set output to correct item type
                                if(!(inputLeftItem instanceof EnchantedBookItem)) {
                                    newStack = inputLeft.copy();
                                } else if(!(inputRightItem instanceof EnchantedBookItem)) {
                                    newStack = inputRight.copy();
                                }

                                int nextLevel = level + 1;

                                if(newStack.getItem() instanceof EnchantedBookItem) {
                                    EnchantedBookItem.addEnchantment(newStack, new EnchantmentLevelEntry(leftEnchantment, nextLevel));
                                } else {
                                    Identifier enchantmentID = Registry.ENCHANTMENT.getId(leftEnchantment);

                                    NbtList copyEnchantments = newStack.copy().getEnchantments();
                                    newStack.getEnchantments().clear();
                                    newStack.addEnchantment(leftEnchantment, nextLevel);

                                    for (NbtElement tag : copyEnchantments) {
                                        NbtCompound cTag = (NbtCompound) tag;
                                        if(!cTag.get("id").asString().equals(enchantmentID.toString())) {
                                            newStack.getEnchantments().add(cTag);
                                        }
                                    }
                                }

                                crystalCost = crystalCosts.get(nextLevel);
                                if(inputOmega.getCount() >= crystalCosts.get(nextLevel)) {
                                    output.setStack(0, newStack);
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }

        output.setStack(0, ItemStack.EMPTY);
    }

    @Override
    public void close(PlayerEntity player) {
        player.getInventory().offerOrDrop(input.getStack(0));
        player.getInventory().offerOrDrop(input.getStack(1));
        player.getInventory().offerOrDrop(input.getStack(2));

        super.close(player);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        return ItemStack.EMPTY;
    }

    // ??????????????????????????????????????
//    @Override
//    public ItemStack transferSlot(PlayerEntity player, int index) {
//        // 0 = left input,
//        // 1 = right input,
//        // 2 = crystal slot,
//        // 3 == result
//
//        Slot slot = this.getSlot(index);
//        ItemStack slotStack = slot.getStack();
//
//        if(!slotStack.isEmpty()) {
//            if(index == 0 || index == 1 || index == 2) {
//                this.insertItem(slotStack, 4, 39, true);
//            }
//
//            else if(slotStack.getItem().equals(OmegaItems.OMEGA_CRYSTAL)) {
//                int currentCrystalCount = this.getSlot(2).getStack().getCount();
//                int amountToMax = 64 - currentCrystalCount;
//                int insertionAmount = slotStack.getCount();
//
//                if(amountToMax > 0) {
//                    if (insertionAmount <= amountToMax) {
//                        slotStack.setCount(0);
//                        this.getSlot(2).getStack().setCount(currentCrystalCount + insertionAmount);
//                    } else {
//                        int leftovers = insertionAmount - amountToMax;
//                        this.getSlot(2).getStack().setCount(64);
//                        slotStack.setCount(leftovers);
//                    }
//                }
//            }
//        }
//
//        this.updateOutput();
//        return new ItemStack(Items.AIR);
//    }
}
