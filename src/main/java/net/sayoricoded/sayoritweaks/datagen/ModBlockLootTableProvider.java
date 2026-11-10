package net.sayoricoded.sayoritweaks.datagen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.sayoricoded.sayoritweaks.block.ModBlocks;
import net.sayoricoded.sayoritweaks.item.ModItems;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.LOOSE_DIRT.get());
        dropSelf(ModBlocks.LOOSE_COBBLESTONE.get());
        dropSelf(ModBlocks.LOOSE_COBBLED_DEEPSLATE.get());
        dropSelf(ModBlocks.LOOSE_BLACK_COBBLESTONE.get());
        add(ModBlocks.BLACK_COBBLESTONE.get(),
                block -> ItemsWhenMissingSilkTouch(ModBlocks.BLACK_COBBLESTONE.get(),
                        ModItems.BLACKSTONE_ROCK.get(), 2));
    }

    protected LootTable.Builder ItemsWhenMissingSilkTouch(Block pBlock, Item item, int drops) {
        HolderLookup.RegistryLookup<Enchantment> registryLookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock, LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(drops)))
                ));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
