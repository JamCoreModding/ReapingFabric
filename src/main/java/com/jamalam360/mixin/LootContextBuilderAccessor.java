package com.jamalam360.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

/**
 * @author Jamalam360
 */

@Mixin(LivingEntity.class)
public interface LootContextBuilderAccessor {
    @Invoker net.minecraft.loot.context.LootContext.Builder callGetLootContextBuilder(boolean causedByPlayer, DamageSource source);
}
