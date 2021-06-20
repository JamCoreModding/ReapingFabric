package com.jamalam360.mixin;

import com.jamalam360.ReaperItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.SwordItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

/**
 * @author Jamalam360
 */

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Redirect( //Allows ReaperItem to have a sweep attack
            method = "attack(Lnet/minecraft/entity/Entity;)V",
            at = @At(
                    value = "CONSTANT",
                    args = "classValue=net/minecraft/item/SwordItem"
            ),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;"),
                    to = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getFireAspect(Lnet/minecraft/entity/LivingEntity;)I")
            )
    )
    public boolean fixSweepCheck(Object item, Class<SwordItem> clazz) {
        return item instanceof ReaperItem || item instanceof SwordItem;
    }
}
