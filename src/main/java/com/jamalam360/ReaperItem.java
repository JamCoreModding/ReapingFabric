package com.jamalam360;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Vanishable;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

/**
 * @author Jamalam360
 */
public class ReaperItem extends Item implements Vanishable {

    public ReaperItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (entity instanceof AnimalEntity && !entity.isBaby()) {
            doToolLogic(entity, stack);
            entity.setAttacker(user);
            stack.damage(1, user.world.getRandom(), null);
            return ActionResult.SUCCESS;
        } else {
            return ActionResult.PASS;
        }
    }

    public static void doToolLogic(LivingEntity entity, ItemStack stack) {
        dropEntityStacks(entity, stack);

        ((AnimalEntity) entity).setBaby(true);

        entity.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0f, 1.0f);
        entity.damage(DamageSource.GENERIC, 1.0f);
    }

    private static void dropEntityStacks(LivingEntity entity, ItemStack stack) {
        try {
            LootTable lootTable = entity.world.getServer().getLootManager().getTable(entity.getLootTable());
            LootContext.Builder builder = entity.getLootContextBuilder(true, DamageSource.GENERIC);

            int lootingLvl = EnchantmentHelper.getLevel(Enchantments.LOOTING, stack);            System.out.println(EnchantmentHelper.getLevel(Enchantments.LOOTING, stack));
            int rollTimes = lootingLvl == 0 ? 1 : RANDOM.nextInt(lootingLvl) + 1;

            for (int i = 0; i < rollTimes; i++) {
                lootTable.generateLoot(builder.build(LootContextTypes.ENTITY), entity::dropStack);
            }
        } catch (NullPointerException e){ }
    }

    @Override
    public int getEnchantability() {
        return 10;
    }
}
