package net.sayoricoded.sayoritweaks.item;

import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.sayoricoded.sayoritweaks.SayoriTweaks;
import net.sayoricoded.sayoritweaks.item.custom.MortaringItem;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SayoriTweaks.MODID);

    // Shovables/Piles
    public static final DeferredItem<Item> DIRT_PILE = ITEMS.register("dirt_pile",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SAND_PILE = ITEMS.register("sand_pile",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> GRAVEL_PILE = ITEMS.register("gravel_pile",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CLAY_PILE = ITEMS.register("clay_pile",
            () -> new MortaringItem(new Item.Properties()));
    public static final DeferredItem<Item> SOUL_SAND_PILE = ITEMS.register("soul_sand_pile",
            () -> new Item(new Item.Properties()));

    // Mineables/Rocks
    public static final DeferredItem<Item> STONE_ROCK = ITEMS.register("stone_rock",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> DEEPSLATE_ROCK = ITEMS.register("deepslate_rock",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BLACKSTONE_ROCK = ITEMS.register("blackstone_rock",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
