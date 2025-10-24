package com.sanjin.helper;

import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

import javax.annotation.Nonnull;

import org.jetbrains.annotations.NotNull;

public class LootModifierHelper extends LootModifier {
    protected LootModifierHelper(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(@Nonnull ObjectArrayList<ItemStack> objectArrayList, @Nonnull LootContext lootContext) {
        return null;
    }

    @Override
    public @NotNull MapCodec<? extends IGlobalLootModifier> codec() {
        return null;
    }
}
