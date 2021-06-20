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

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.Vanishable;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

import java.util.Random;

/**
 * @author Jamalam360
 */
public class ReaperItem extends Item implements Vanishable {
    private final ToolMaterial TOOL_MATERIAL;
    private static final Random RANDOM = new Random();
    private final float attackDamage;
    private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

    public ReaperItem(Settings settings, ToolMaterial material) {
        super(settings.maxDamage(material.getDurability()));
        this.TOOL_MATERIAL = material;

        this.attackDamage = (float) (material.getAttackDamage() * 1.5);
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Tool modifier", this.attackDamage, EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Tool modifier", -2.8f, EntityAttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(slot);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (entity instanceof AnimalEntity && !entity.isBaby()) {
            doToolLogic(entity, stack);
            entity.setAttacker(user);
            stack.damage(1, user.world.getRandom(), null);

            user.incrementStat(ReapingModInit.USE_REAPER_TOOL);

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

            int lootingLvl = EnchantmentHelper.getLevel(Enchantments.LOOTING, stack);
            int rollTimes = lootingLvl == 0 ? 1 : RANDOM.nextInt(lootingLvl) + 1;

            for (int i = 0; i < rollTimes; i++) {
                lootTable.generateLoot(builder.build(LootContextTypes.ENTITY), entity::dropStack);
            }
        } catch (NullPointerException ignored) {}
    }

    @Override
    public int getEnchantability() {
        return 10;
    }
}
