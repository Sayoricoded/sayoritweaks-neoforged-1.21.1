package net.sayoricoded.sayoritweaks.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.sayoricoded.sayoritweaks.SayoriTweaks;
import net.sayoricoded.sayoritweaks.block.custom.LooseBlock;
import net.sayoricoded.sayoritweaks.item.ModItems;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(SayoriTweaks.MODID);

    public static final DeferredBlock<Block> LOOSE_DIRT = registerBlock(
            "loose_dirt", () ->
                    new LooseBlock(BlockBehaviour.Properties.of()
                            .strength(0.5F)
                            .sound(SoundType.GRAVEL)));

    public static final DeferredBlock<Block> LOOSE_COBBLESTONE = registerBlock(
            "loose_cobblestone", () ->
                    new LooseBlock(BlockBehaviour.Properties.of()
                            .requiresCorrectToolForDrops()
                            .strength(2f, 6f)
                            .sound(SoundType.STONE)));

    public static final DeferredBlock<Block> LOOSE_COBBLED_DEEPSLATE = registerBlock(
            "loose_cobbled_deepslate", () ->
                    new LooseBlock(BlockBehaviour.Properties.of()
                            .requiresCorrectToolForDrops()
                            .strength(3f, 6f)
                            .sound(SoundType.DEEPSLATE)));

    public static final DeferredBlock<Block> LOOSE_BLACK_COBBLESTONE = registerBlock(
            "loose_black_cobblestone", () ->
                    new LooseBlock(BlockBehaviour.Properties.of()
                            .requiresCorrectToolForDrops()
                            .strength(4f, 7f)
                            .sound(SoundType.STONE)));

    public static final DeferredBlock<Block> BLACK_COBBLESTONE = registerBlock(
            "black_cobblestone", () ->
            new Block(BlockBehaviour.Properties.of()
                    .requiresCorrectToolForDrops()
                    .strength(4.0F, 7.0F)
                    .sound(SoundType.DEEPSLATE)));

    public static final DeferredBlock<Block> LOOSE_GRAVEL_SLAB = registerBlock(
            "loose_gravel_slab", () ->
                    new LooseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL)));

    // Block Registering Black Magic
    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
