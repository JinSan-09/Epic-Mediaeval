package com.sanjin.recipe.recipedisplay;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sanjin.register.ModBlocks;
import com.sanjin.register.ModRecipeDisplays;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public record FermentationBarrelRecipeDisplay(List<SlotDisplay> inputs, SlotDisplay output) implements RecipeDisplay {

    public static final MapCodec<FermentationBarrelRecipeDisplay> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            SlotDisplay.CODEC.listOf().fieldOf("ingredients").forGetter(FermentationBarrelRecipeDisplay::inputs),
            SlotDisplay.CODEC.fieldOf("result").forGetter(FermentationBarrelRecipeDisplay::output)
            ).apply(instance, FermentationBarrelRecipeDisplay::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, FermentationBarrelRecipeDisplay> STREAM_CODEC = StreamCodec.composite(
            getInputsStreamCodec(), FermentationBarrelRecipeDisplay::inputs,
            SlotDisplay.STREAM_CODEC, FermentationBarrelRecipeDisplay::output,
            FermentationBarrelRecipeDisplay::new
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

    @Override
    public @NotNull SlotDisplay result() {
        return output;
    }

    @Override
    public @NotNull SlotDisplay craftingStation() {
        return new SlotDisplay.ItemStackSlotDisplay(ModBlocks.FERMENTATION_BARREL_BLOCK.toStack());
    }

    @Override
    public @NotNull Type<? extends RecipeDisplay> type() {
        return ModRecipeDisplays.FERMENTATION_BARREL_RECIPE_DISPLAY.get();
    }
}
