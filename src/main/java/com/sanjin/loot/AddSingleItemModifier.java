package com.sanjin.loot;

import javax.annotation.Nonnull;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;


public class AddSingleItemModifier extends LootModifier{

    private final Item item;

    // Codec
    public static final MapCodec<AddSingleItemModifier> CODEC = RecordCodecBuilder.mapCodec(inst ->
    LootModifier.codecStart(inst).and(
        BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(e -> e.item)
        ).apply(inst, AddSingleItemModifier::new)
    );

    public AddSingleItemModifier(LootItemCondition[] conditions, Item item) {
        super(conditions);
        this.item = item;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(@Nonnull ObjectArrayList<ItemStack> generatedLoot, @Nonnull LootContext context) {

        for(LootItemCondition condition: this.conditions){
            if(!condition.test(context)){
                return generatedLoot;
            }
        }
        generatedLoot.add(new ItemStack(this.item));
        
        return generatedLoot;
    }

}
