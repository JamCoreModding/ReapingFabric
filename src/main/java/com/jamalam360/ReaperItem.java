package com.jamalam360;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;

/**
 * @author Jamalam360
 */
public class ReaperItem extends Item {
    public ReaperItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (entity instanceof AnimalEntity && !entity.isBaby()) {
            doToolLogic(entity);
            entity.setAttacker(user);
            stack.damage(1, user.world.getRandom(), null);
            return ActionResult.SUCCESS;
        } else {
            return ActionResult.PASS;
        }
    }

    public static void doToolLogic(LivingEntity entity) {
        dropEntityStacks(entity);
        ((AnimalEntity) entity).setBaby(true);

        entity.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0f, 1.0f);
        entity.damage(DamageSource.GENERIC, 1.0f);
    }

    private static void dropEntityStacks(LivingEntity entity) {
        Identifier identifier = entity.getLootTable();
        try {
            LootTable lootTable = entity.world.getServer().getLootManager().getTable(identifier);
            LootContext.Builder builder = entity.getLootContextBuilder(true, DamageSource.GENERIC);
            lootTable.generateLoot(builder.build(LootContextTypes.ENTITY), entity::dropStack);
        } catch (NullPointerException e) {
        }
    }
}
