package com.sanjin.serializer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sanjin.recipe.FermentationBarrelRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

public class FermentationBarrelRecipeSerializer implements RecipeSerializer<FermentationBarrelRecipe> {

    public static final MapCodec<FermentationBarrelRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                            Codec.list(Ingredient.CODEC).fieldOf("ingredients").xmap(list -> {
                                        NonNullList<Ingredient> nnl = NonNullList.create();
                                        nnl.addAll(list);
                                        return nnl;
                                    },NonNullList::copyOf
                            ).forGetter(FermentationBarrelRecipe::getInputs),
                            ItemStack.CODEC.fieldOf("result").forGetter(FermentationBarrelRecipe::getResult),
                            ItemStack.CODEC.fieldOf("container").forGetter(FermentationBarrelRecipe::getContainer),
                            Codec.FLOAT.optionalFieldOf("experience",0.0f).forGetter(FermentationBarrelRecipe::getExperience),
                            Codec.INT.optionalFieldOf("cooking_time",300).forGetter(FermentationBarrelRecipe::getCookingTime))
                    .apply(instance, FermentationBarrelRecipe::new)

    );

    public static final StreamCodec<RegistryFriendlyByteBuf, FermentationBarrelRecipe> STREAM_CODEC = StreamCodec.of(FermentationBarrelRecipeSerializer::toNetwork, FermentationBarrelRecipeSerializer::fromNetwork);


    private static FermentationBarrelRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {

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

            return new FermentationBarrelRecipe(inputItemsIn, outputIn, container, experienceIn, cookTimeIn);
        } catch (Exception e) {
            System.err.println("Error decoding FermentationBarrelRecipe from network: " + e.getMessage());
            return new FermentationBarrelRecipe( NonNullList.create(), ItemStack.EMPTY, ItemStack.EMPTY, 0.0f, 0);
        }
    }

    private static void toNetwork(RegistryFriendlyByteBuf buffer, FermentationBarrelRecipe recipe) {
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
    public @NotNull MapCodec<FermentationBarrelRecipe> codec() {
        return CODEC;
    }

    @Override
    public @NotNull StreamCodec<RegistryFriendlyByteBuf, FermentationBarrelRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
