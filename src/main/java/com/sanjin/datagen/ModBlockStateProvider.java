package com.sanjin.datagen;

import com.sanjin.EpicMediaeval;
import com.sanjin.block.BarleyCropBlock;
import com.sanjin.block.ChickpeaCropBlock;
import com.sanjin.block.GreenPepperCropBlock;
import com.sanjin.block.HorseradishCropBlock;
import com.sanjin.block.LeekCropBlock;
import com.sanjin.block.NettleCropBlock;
import com.sanjin.block.OatsCropBlock;
import com.sanjin.block.OnionCropBlock;
import com.sanjin.block.WhiteBeansCropBlock;
import com.sanjin.register.ModBlocks;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider{

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, EpicMediaeval.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        makeCrop(ModBlocks.BARLEY_CROP_BLOCK.get(), "barley_crop", BarleyCropBlock.AGE);
        makeCrop(ModBlocks.CHICKPEA_CROP_BLOCK.get(), "chickpea_crop", ChickpeaCropBlock.AGE);
        makeCrop(ModBlocks.GREEN_PEPPER_CROP_BLOCK.get(), "green_pepper_crop", GreenPepperCropBlock.AGE);
        makeCrop(ModBlocks.HORSERADISH_CROP_BLOCK.get(), "horseradish_crop", HorseradishCropBlock.AGE);
        makeCrop(ModBlocks.LEEK_CROP_BLOCK.get(), "leek_crop", LeekCropBlock.AGE);
        makeCrop(ModBlocks.NETTLE_CROP_BLOCK.get(), "nettle_crop", NettleCropBlock.AGE);
        makeCrop(ModBlocks.OATS_CROP_BLOCK.get(), "oats_crop", OatsCropBlock.AGE);
        makeCrop(ModBlocks.ONION_CROP_BLOCK.get(), "onion_crop", OnionCropBlock.AGE);
        makeCrop(ModBlocks.WHITE_BEANS_CROP_BLOCK.get(), "white_beans_crop", WhiteBeansCropBlock.AGE);
        
    }

    // Helper method
    public void makeCrop(Block block, String cropName, IntegerProperty ageProperty) {
        getVariantBuilder(block).forAllStates(state -> {
            int age = state.getValue(ageProperty);
            return ConfiguredModel.builder()
                .modelFile(models().crop(cropName + "_stage" + age,
                    modLoc("block/" + cropName + "_stage" + age)))
                .build();
        });
    }

//    private void blockWithItem(DeferredBlock<?> deferredBlock){
//        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
//    }

}
