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

import com.jamalam360.config.ReapingModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

/**
 * @author Jamalam360
 */
public class ReapingToolDispenserBehavior extends FallibleItemDispenserBehavior {
    public ReapingToolDispenserBehavior() {
    }

    @Override
    public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        World world = pointer.getWorld();
        ReapingModConfig config = AutoConfig.getConfigHolder(ReapingModConfig.class).getConfig();

        if (!world.isClient() && config.enableDispenserBehavior) {
            BlockPos blockPos = pointer.getBlockPos().offset((Direction) pointer.getBlockState().get(DispenserBlock.FACING));
            this.setSuccess(tryReapEntity((ServerWorld) world, blockPos, stack));

            if (this.isSuccess() && stack.damage(1, world.getRandom(), (ServerPlayerEntity) null)) {
                stack.setCount(0);
            }
        }

        return stack;
    }

    private static boolean tryReapEntity(ServerWorld world, BlockPos pos, ItemStack stack) {
        List<LivingEntity> list = world.getEntitiesByClass(LivingEntity.class, new Box(pos), EntityPredicates.EXCEPT_SPECTATOR);
        Iterator var3 = list.iterator();

        while (var3.hasNext()) {
            LivingEntity livingEntity = (LivingEntity) var3.next();
            if (livingEntity instanceof AnimalEntity && !livingEntity.isBaby()) {
                ReaperItem.doToolLogic(livingEntity, stack);
                return true;
            }
        }

        return false;
    }
}
