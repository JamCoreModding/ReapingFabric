package com.jamalam360;

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
        if (!world.isClient()) {
            BlockPos blockPos = pointer.getBlockPos().offset((Direction) pointer.getBlockState().get(DispenserBlock.FACING));
            this.setSuccess(tryShearBlock((ServerWorld) world, blockPos) || tryShearEntity((ServerWorld) world, blockPos));
            if (this.isSuccess() && stack.damage(1, world.getRandom(), (ServerPlayerEntity) null)) {
                stack.setCount(0);
            }
        }

        return stack;
    }

    private static boolean tryShearBlock(ServerWorld world, BlockPos pos) {
        return false;
    }

    private static boolean tryShearEntity(ServerWorld world, BlockPos pos) {
        List<LivingEntity> list = world.getEntitiesByClass(LivingEntity.class, new Box(pos), EntityPredicates.EXCEPT_SPECTATOR);
        Iterator var3 = list.iterator();

        while (var3.hasNext()) {
            LivingEntity livingEntity = (LivingEntity) var3.next();
            if (livingEntity instanceof AnimalEntity && !livingEntity.isBaby()) {
                ReaperItem.doToolLogic(livingEntity);
                return true;
            }
        }

        return false;
    }
}
