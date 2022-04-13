package draylar.intotheomega.item;

import draylar.intotheomega.impl.ProjectileManipulator;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;

public class CustomBowItem extends BowItem {

    private final ToolMaterial material;
    private final float maxDrawTime;
    private final ParticleEffect type;
    private boolean staticVelocity = false;

    public CustomBowItem(ToolMaterial material, Item.Settings settings, float maxDrawTime) {
        super(settings);
        this.material = material;
        this.maxDrawTime = maxDrawTime;
        type = null;
    }

    public CustomBowItem(ToolMaterial material, Item.Settings settings, float maxDrawTime, ParticleEffect particles) {
        super(settings);
        this.material = material;
        this.maxDrawTime = maxDrawTime;
        type = particles;
    }

    public void setStaticVelocity(boolean staticVelocity) {
        this.staticVelocity = staticVelocity;
    }

    public boolean hasStaticVelocity() {
        return staticVelocity;
    }

    public float getMaxDrawTime(ItemStack bow) {
        return Math.max(0, maxDrawTime);
    }

    public ParticleEffect getArrowParticles() {
        return type;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity)user;
            boolean skipArrowCheck = playerEntity.getAbilities().creativeMode || EnchantmentHelper.getLevel(Enchantments.INFINITY, stack) > 0;
            ItemStack arrowStack = playerEntity.getArrowType(stack);

            if (!arrowStack.isEmpty() || skipArrowCheck) {
                if (arrowStack.isEmpty()) {
                    arrowStack = new ItemStack(Items.ARROW);
                }

                int currentUseTime = this.getMaxUseTime(stack) - remainingUseTicks;
                float pullProgress = getPullProgress(stack, this, currentUseTime);

                if ((double) pullProgress >= 0.1D) {
                    boolean bl2 = skipArrowCheck && arrowStack.getItem() == Items.ARROW;

                    if (!world.isClient) {
                        // Make Arrow crit if pull progress is fully complete
                        boolean critical = pullProgress == 1.0f;
                        double damage = 2.0 + (EnchantmentHelper.getLevel(Enchantments.POWER, stack) > 0 ? (EnchantmentHelper.getLevel(Enchantments.POWER, stack) * 0.5D + 0.5D) : 0);
                        int punch = EnchantmentHelper.getLevel(Enchantments.PUNCH, stack);
                        int fireTicks = EnchantmentHelper.getLevel(Enchantments.FLAME, stack) > 0 ? 100 : 0;

                        // Set arrow pickup type based on source
                        PersistentProjectileEntity.PickupPermission pickupPermission = PersistentProjectileEntity.PickupPermission.ALLOWED;
                        if (bl2 || playerEntity.getAbilities().creativeMode && (arrowStack.getItem() == Items.SPECTRAL_ARROW || arrowStack.getItem() == Items.TIPPED_ARROW)) {
                            pickupPermission = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
                        }

                        // Damage tool
                        stack.damage(1, playerEntity, (p) -> p.sendToolBreakStatus(playerEntity.getActiveHand()));

                        ArrowData data = new ArrowData(critical, damage, punch, fireTicks, pickupPermission);
                        shootArrow(arrowStack, stack, world, playerEntity, data, pullProgress);
                    }

                    world.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (world.random.nextFloat() * 0.4F + 1.2F) + pullProgress * 0.5F);

                    // decrement source arrow stack
                    if (!bl2 && !playerEntity.getAbilities().creativeMode) {
                        arrowStack.decrement(1);
                        if (arrowStack.isEmpty()) {
                            playerEntity.getInventory().removeOne(arrowStack);
                        }
                    }

                    playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
                }
            }
        }
    }

    public void shootArrow(ItemStack arrowStack, ItemStack bowStack, World world, PlayerEntity playerEntity, ArrowData data, float pullProgress) {
        PersistentProjectileEntity arrow = createArrow(arrowStack, bowStack, world, playerEntity, data);
        arrow.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0F, pullProgress * 3.0F, 1.0F);
        playerEntity.world.spawnEntity(arrow);
    }

    public PersistentProjectileEntity createArrow(ItemStack arrowStack, ItemStack bowStack, World world, PlayerEntity playerEntity, ArrowData data) {
        ArrowItem arrowItem = (ArrowItem) (arrowStack.getItem() instanceof ArrowItem ? arrowStack.getItem() : Items.ARROW);
        PersistentProjectileEntity arrow = arrowItem.createArrow(world, arrowStack, playerEntity);
        ((ProjectileManipulator) arrow).ito_setOrigin(bowStack);

        // Set attributes
        arrow.setCritical(data.critical);
        arrow.setDamage(data.damage);
        arrow.setPunch(data.punch);
        arrow.setFireTicks(data.fireTicks);
        arrow.pickupType = data.pickupType;
        return arrow;
    }

    public static float getPullProgress(ItemStack stack, CustomBowItem bow, int useTicks) {
        float progress = (float) useTicks / bow.getMaxDrawTime(stack);
        progress = (progress * progress + progress * 2.0F) / 3.0F;

        if (progress > 1.0F) {
            progress = 1.0F;
        }

        return progress;
    }

    @Override
    public int getEnchantability() {
        return material.getEnchantability();
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return this.material.getRepairIngredient().test(ingredient) || super.canRepair(stack, ingredient);
    }

    public record ArrowData(boolean critical, double damage, int punch, int fireTicks, PersistentProjectileEntity.PickupPermission pickupType) {

    }
}