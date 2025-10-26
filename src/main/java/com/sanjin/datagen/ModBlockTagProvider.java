package com.sanjin.datagen;

import java.util.concurrent.CompletableFuture;

import javax.annotation.Nonnull;

import org.jetbrains.annotations.Nullable;

import com.sanjin.EpicMediaeval;
import com.sanjin.register.ModBlocks;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockTagProvider extends BlockTagsProvider{

    public ModBlockTagProvider(PackOutput output, CompletableFuture<Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, EpicMediaeval.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(@Nonnull Provider arg0) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.STEW_STOVE_BLOCK.get())
                .add(ModBlocks.FERMENTATION_BARREL_BLOCK.get());
    }


    
}
