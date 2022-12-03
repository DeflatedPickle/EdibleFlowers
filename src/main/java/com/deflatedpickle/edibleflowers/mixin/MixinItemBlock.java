/* Copyright (c) 2021-2022 DeflatedPickle under the MIT license */

package com.deflatedpickle.edibleflowers.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.CropBlock;
import net.minecraft.block.PlantBlock;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@SuppressWarnings({"UnusedMixin", "unused"})
@Mixin(BlockItem.class)
public abstract class MixinItemBlock extends Item {
  @Shadow
  public abstract Block getBlock();

  private static final FoodComponent flowerFood =
      (new FoodComponent.Builder())
          .hunger(1)
          .saturationModifier(0.2F)
          .statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 150, 2), 0.5F)
          .statusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 300, 1), 0.2f)
          .snack()
          .alwaysEdible()
          .build();

  public MixinItemBlock(Settings settings) {
    super(settings);
  }

  @Override
  public boolean isFood() {
    return getFoodComponent() != null;
  }

  @Nullable
  @Override
  public FoodComponent getFoodComponent() {
    if (getBlock() instanceof PlantBlock && !(getBlock() instanceof CropBlock)) {
      return flowerFood;
    }

    return super.getFoodComponent();
  }
}
