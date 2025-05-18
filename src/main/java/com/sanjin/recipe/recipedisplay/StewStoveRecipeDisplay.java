package com.sanjin.recipe.recipedisplay;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sanjin.register.ModRecipeDisplays;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public record StewStoveRecipeDisplay(List<SlotDisplay> inputs, SlotDisplay container, SlotDisplay output) implements RecipeDisplay {

    public static final MapCodec<StewStoveRecipeDisplay> MAP_CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            SlotDisplay.CODEC.listOf().fieldOf("ingredients").forGetter(StewStoveRecipeDisplay::inputs),
                            SlotDisplay.CODEC.fieldOf("container").forGetter(StewStoveRecipeDisplay::container),
                            SlotDisplay.CODEC.fieldOf("result").forGetter(StewStoveRecipeDisplay::output)
                    )
                    .apply(instance, StewStoveRecipeDisplay::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, StewStoveRecipeDisplay> STREAM_CODEC = StreamCodec.composite(
            getInputsStreamCodec(), StewStoveRecipeDisplay::inputs,
            SlotDisplay.STREAM_CODEC, StewStoveRecipeDisplay::container,
            SlotDisplay.STREAM_CODEC, StewStoveRecipeDisplay::output,
            StewStoveRecipeDisplay::new
    );

    private static StreamCodec<RegistryFriendlyByteBuf, List<SlotDisplay>> getInputsStreamCodec(){
        return StreamCodec.of(
                (buf, list) -> {
                    buf.writeInt(list.size());
                    for (SlotDisplay slot : list) {
                        SlotDisplay.STREAM_CODEC.encode(buf, slot);
                    }
                },
                buf -> {
                    int size = buf.readInt();
                    List<SlotDisplay> list = new ArrayList<>(size);
                    for (int i = 0; i < size; i++) {
                        list.add(SlotDisplay.STREAM_CODEC.decode(buf));
                    }
                    return list;
                }
        );
    }
    public List<SlotDisplay> getInputsDisplay(){
        return this.inputs;
    }
    public SlotDisplay getContainerDisplay(){return this.container;}

    @Override
    public @NotNull SlotDisplay result() {
        return output;
    }

    @Override
    public @NotNull SlotDisplay craftingStation() {
        return inputs.getFirst();
    }

    @Override
    public RecipeDisplay.@NotNull Type<? extends RecipeDisplay> type() {
        return ModRecipeDisplays.STEW_STOVE_RECIPE_DISPLAY.get();
    }
}
