package net.sayoricoded.sayoritweaks.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.sayoricoded.sayoritweaks.SayoriTweaks;
import net.sayoricoded.sayoritweaks.block.ModBlocks;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, SayoriTweaks.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.LOOSE_DIRT);
        blockWithItem(ModBlocks.LOOSE_COBBLESTONE);
        blockWithItem(ModBlocks.LOOSE_COBBLED_DEEPSLATE);
        blockWithItem(ModBlocks.LOOSE_BLACK_COBBLESTONE);
        blockWithItem(ModBlocks.BLACK_COBBLESTONE);
    }

    private void blockWithItem(DeferredBlock<?> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }

}
