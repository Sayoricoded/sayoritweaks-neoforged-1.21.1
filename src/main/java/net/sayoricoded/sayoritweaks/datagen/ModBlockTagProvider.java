package net.sayoricoded.sayoritweaks.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.sayoricoded.sayoritweaks.SayoriTweaks;
import net.sayoricoded.sayoritweaks.block.ModBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, SayoriTweaks.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.LOOSE_COBBLESTONE.get())
                .add(ModBlocks.LOOSE_COBBLED_DEEPSLATE.get())
                .add(ModBlocks.LOOSE_BLACK_COBBLESTONE.get())
                .add(ModBlocks.BLACK_COBBLESTONE.get());
        tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(ModBlocks.LOOSE_DIRT.get());

    }
}
