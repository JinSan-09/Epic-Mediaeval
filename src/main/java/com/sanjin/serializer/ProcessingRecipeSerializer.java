package com.sanjin.serializer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sanjin.recipe.AbstractProcessingRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

public final class ProcessingRecipeSerializer<R extends AbstractProcessingRecipe> implements RecipeSerializer<R> {

    @FunctionalInterface
    public interface Factory<R extends AbstractProcessingRecipe> {
        R create(NonNullList<Ingredient> inputs, ItemStack result, ItemStack container,
                 String group, float experience, int processingTime);
    }

    private final Factory<R> factory;
    private final MapCodec<R> codec;
    private final StreamCodec<RegistryFriendlyByteBuf, R> streamCodec;

    public ProcessingRecipeSerializer(Factory<R> factory) {
        this.factory = factory;
        this.codec = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Codec.list(Ingredient.CODEC).fieldOf("ingredients").xmap(list -> {
                    NonNullList<Ingredient> ingredients = NonNullList.create();
                    ingredients.addAll(list);
                    return ingredients;
                }, NonNullList::copyOf).forGetter(AbstractProcessingRecipe::getInputs),
                ItemStack.CODEC.fieldOf("result").forGetter(AbstractProcessingRecipe::getResult),
                ItemStack.CODEC.fieldOf("container").forGetter(AbstractProcessingRecipe::getContainer),
                Codec.STRING.fieldOf("group").forGetter(AbstractProcessingRecipe::getGroup),
                Codec.FLOAT.optionalFieldOf("experience", 0.0f).forGetter(AbstractProcessingRecipe::getExperience),
                Codec.INT.optionalFieldOf("cooking_time", 300).forGetter(AbstractProcessingRecipe::getProcessingTime)
        ).apply(instance, factory::create));
        this.streamCodec = StreamCodec.of(this::toNetwork, this::fromNetwork);
    }

    private R fromNetwork(RegistryFriendlyByteBuf buffer) {
        int count = buffer.readVarInt();
        NonNullList<Ingredient> inputs = NonNullList.withSize(count, Ingredient.of());
        for (int i = 0; i < count; i++) {
            inputs.set(i, Ingredient.CONTENTS_STREAM_CODEC.decode(buffer));
        }
        ItemStack output = ItemStack.STREAM_CODEC.decode(buffer);
        ItemStack container = ItemStack.STREAM_CODEC.decode(buffer);
        String group = buffer.readUtf();
        float experience = buffer.readFloat();
        int processingTime = buffer.readVarInt();
        return factory.create(inputs, output, container, group, experience, processingTime);
    }

    private void toNetwork(RegistryFriendlyByteBuf buffer, R recipe) {
        buffer.writeVarInt(recipe.getInputs().size());
        for (Ingredient ingredient : recipe.getInputs()) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, ingredient);
        }
        ItemStack.STREAM_CODEC.encode(buffer, recipe.getResult());
        ItemStack.STREAM_CODEC.encode(buffer, recipe.getContainer());
        buffer.writeUtf(recipe.getGroup());
        buffer.writeFloat(recipe.getExperience());
        buffer.writeVarInt(recipe.getProcessingTime());
    }

    @Override
    public @NotNull MapCodec<R> codec() {
        return codec;
    }

    @Override
    public @NotNull StreamCodec<RegistryFriendlyByteBuf, R> streamCodec() {
        return streamCodec;
    }
}
