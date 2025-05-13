package com.sanjin.serializer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sanjin.recipe.StewStoveRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

public class StewStoveRecipeSerializer implements RecipeSerializer<StewStoveRecipe> {

    public static final MapCodec<StewStoveRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                            Codec.list(Ingredient.CODEC).fieldOf("ingredients").xmap(list -> {
                                NonNullList<Ingredient> nnl = NonNullList.create();
                                nnl.addAll(list);
                                return nnl;
                            },NonNullList::copyOf
                            ).forGetter(StewStoveRecipe::getInputs),
                            ItemStack.CODEC.fieldOf("result").forGetter(StewStoveRecipe::getResult),
                            ItemStack.CODEC.fieldOf("container").forGetter(StewStoveRecipe::getContainer),
                            Codec.FLOAT.optionalFieldOf("experience",0.0f).forGetter(StewStoveRecipe::getExperience),
                            Codec.INT.optionalFieldOf("cooking_time",300).forGetter(StewStoveRecipe::getCookingTime))
                    .apply(instance, StewStoveRecipe::new)

    );

    public static final StreamCodec<RegistryFriendlyByteBuf, StewStoveRecipe> STREAM_CODEC = StreamCodec.of(StewStoveRecipeSerializer::toNetwork, StewStoveRecipeSerializer::fromNetwork);

    private static StewStoveRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {

        try {
            int count = buffer.readVarInt();
            NonNullList<Ingredient> inputItemsIn = NonNullList.withSize(count, Ingredient.of());
            for (int i = 0; i < count; i++) {
                inputItemsIn.set(i, Ingredient.CONTENTS_STREAM_CODEC.decode(buffer));
            }
            ItemStack outputIn = ItemStack.STREAM_CODEC.decode(buffer);
            ItemStack container = ItemStack.STREAM_CODEC.decode(buffer);
            float experienceIn = buffer.readFloat();
            int cookTimeIn = buffer.readVarInt();

            return new StewStoveRecipe(inputItemsIn, outputIn, container, experienceIn, cookTimeIn);
        } catch (Exception e) {
            System.err.println("Error decoding StewStoveRecipe from network: " + e.getMessage());
            return new StewStoveRecipe( NonNullList.create(), ItemStack.EMPTY, ItemStack.EMPTY, 0.0f, 0);
        }
    }

    private static void toNetwork(RegistryFriendlyByteBuf buffer, StewStoveRecipe recipe) {

        buffer.writeVarInt(recipe.getInputs().size());
        for (Ingredient ingredient : recipe.getInputs()) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, ingredient);
        }
        ItemStack.STREAM_CODEC.encode(buffer, recipe.getResult());
        ItemStack.STREAM_CODEC.encode(buffer, recipe.getContainer());
        buffer.writeFloat(recipe.getExperience());
        buffer.writeVarInt(recipe.getCookingTime());
    }

    @Override
    public @NotNull MapCodec<StewStoveRecipe> codec() {
        return CODEC;
    }

    @Override
    public @NotNull StreamCodec<RegistryFriendlyByteBuf, StewStoveRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
