package net.sayoricoded.sayoritweaks.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.sayoricoded.sayoritweaks.SayoriTweaks;
import net.sayoricoded.sayoritweaks.item.ModItems;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, SayoriTweaks.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.DIRT_PILE.get());
        basicItem(ModItems.SAND_PILE.get());
        basicItem(ModItems.GRAVEL_PILE.get());
        basicItem(ModItems.CLAY_PILE.get());
        basicItem(ModItems.SOUL_SAND_PILE.get());

        basicItem(ModItems.STONE_ROCK.get());
        basicItem(ModItems.DEEPSLATE_ROCK.get());
        basicItem(ModItems.BLACKSTONE_ROCK.get());
    }
}
