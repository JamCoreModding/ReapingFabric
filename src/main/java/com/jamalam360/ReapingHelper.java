/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2021 Jamalam360
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

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
        if (!VALID_REAPING_TOOLS.contains(toolStack.getItem().getClass())) {
            return ActionResult.PASS;
        } else if (reapedEntity instanceof AnimalEntity && !reapedEntity.isBaby()) {
            dropEntityStacks(reapedEntity, toolStack);

            ((AnimalEntity) reapedEntity).setBaby(true);

            reapedEntity.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0f, 1.0f);
            reapedEntity.damage(DamageSource.GENERIC, 1.0f);

            return ActionResult.SUCCESS;
        } else {
            return ActionResult.PASS;
        }
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
        } catch (NullPointerException ignored) {
        }
    }

    public static void registerValidReapingTool(Class itemClass) {
        VALID_REAPING_TOOLS.add(itemClass);
    }
}