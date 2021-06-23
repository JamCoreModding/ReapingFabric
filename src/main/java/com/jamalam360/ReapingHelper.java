package com.jamalam360;

import com.jamalam360.mixin.LootContextBuilderAccessor;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Jamalam360
 */
@SuppressWarnings("rawtypes")
public class ReapingHelper {
    public static final ArrayList<Class> VALID_REAPING_TOOLS = new ArrayList<>();
    private static final Random RANDOM = new Random();

    public static ActionResult doReapingLogic(LivingEntity reapedEntity, ItemStack toolStack) {
        if(!VALID_REAPING_TOOLS.contains(toolStack.getItem().getClass())){
            return ActionResult.PASS;
        }

        dropEntityStacks(reapedEntity, toolStack);

        ((AnimalEntity) reapedEntity).setBaby(true);

        reapedEntity.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0f, 1.0f);
        reapedEntity.damage(DamageSource.GENERIC, 1.0f);

        return ActionResult.SUCCESS;
    }

    private static void dropEntityStacks(LivingEntity entity, ItemStack stack) {
        try {
            LootTable lootTable = entity.world.getServer().getLootManager().getTable(entity.getLootTable());
            LootContext.Builder builder = ((LootContextBuilderAccessor) entity).callGetLootContextBuilder(true, DamageSource.GENERIC);

            int lootingLvl = EnchantmentHelper.getLevel(Enchantments.LOOTING, stack);
            int rollTimes = lootingLvl == 0 ? 1 : RANDOM.nextInt(lootingLvl) + 1;

            for (int i = 0; i < rollTimes; i++) {
                lootTable.generateLoot(builder.build(LootContextTypes.ENTITY), entity::dropStack);
            }
        } catch (NullPointerException ignored) {}
    }

    public static void registerValidReapingTool(Class itemClass){
        VALID_REAPING_TOOLS.add(itemClass);
    }
}
