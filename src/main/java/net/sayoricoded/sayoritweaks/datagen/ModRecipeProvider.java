package net.sayoricoded.sayoritweaks.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import net.sayoricoded.sayoritweaks.block.ModBlocks;
import net.sayoricoded.sayoritweaks.item.ModItems;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {

        /*
        para automatização no futuro, essa recomendação do claude eh boa
        https://claude.ai/share/0dff1745-c1ff-4518-b3bd-a51fe7891a27
         */

        // blocks from items

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.LOOSE_DIRT.get())
                .pattern("AA")
                .pattern("AA")
                .define('A', ModItems.DIRT_PILE)
                .unlockedBy("has_loose_dirt", has(ModBlocks.LOOSE_DIRT))
                .save(recipeOutput, "loose_dirt_block_from_pile");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.SAND)
                .pattern("AA")
                .pattern("AA")
                .define('A', ModItems.SAND_PILE)
                .unlockedBy("has_sand", has(Blocks.SAND))
                .save(recipeOutput, "sand_block_from_pile");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.GRAVEL)
                .pattern("AA")
                .pattern("AA")
                .define('A', ModItems.GRAVEL_PILE)
                .unlockedBy("has_gravel", has(Blocks.GRAVEL))
                .save(recipeOutput, "gravel_block_from_pile");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.CLAY)
                .pattern("AA")
                .pattern("AA")
                .define('A', ModItems.CLAY_PILE)
                .unlockedBy("has_clay", has(Blocks.CLAY))
                .save(recipeOutput, "clay_block_from_pile");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.SOUL_SAND)
                .pattern("AA")
                .pattern("AA")
                .define('A', ModItems.SOUL_SAND_PILE)
                .unlockedBy("has_soul_sand", has(Blocks.SOUL_SAND))
                .save(recipeOutput, "soul_sand_block_from_pile");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.LOOSE_COBBLESTONE.get())
                .pattern("AA")
                .pattern("AA")
                .define('A', ModItems.STONE_ROCK)
                .unlockedBy("has_loose_cobblestone", has(ModBlocks.LOOSE_COBBLESTONE))
                .save(recipeOutput, "loose_cobblestone_from_rocks");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.LOOSE_COBBLED_DEEPSLATE.get())
                .pattern("AA")
                .pattern("AA")
                .define('A', ModItems.DEEPSLATE_ROCK)
                .unlockedBy("has_loose_cobbled_deepslate", has(ModBlocks.LOOSE_COBBLED_DEEPSLATE))
                .save(recipeOutput, "loose_cobbled_deepslate_from_rocks");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.LOOSE_BLACK_COBBLESTONE.get())
                .pattern("AA")
                .pattern("AA")
                .define('A', ModItems.BLACKSTONE_ROCK)
                .unlockedBy("has_loose_black_cobblestone", has(ModBlocks.LOOSE_BLACK_COBBLESTONE))
                .save(recipeOutput, "loose_blackstone_from_rocks");

        //items from blocks

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.DIRT_PILE.get(), 2)
                .requires(ModBlocks.LOOSE_DIRT)
                .unlockedBy("has_dirt_pile", has(ModItems.DIRT_PILE))
                .save(recipeOutput, "dirt_pile_from_block");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SAND_PILE.get(), 2)
                .requires(Blocks.SAND)
                .unlockedBy("has_sand_pile", has(ModItems.SAND_PILE))
                .save(recipeOutput, "sand_pile_from_block");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.GRAVEL_PILE.get(), 2)
                .requires(Blocks.GRAVEL)
                .unlockedBy("has_gravel_pile", has(ModItems.GRAVEL_PILE))
                .save(recipeOutput, "gravel_pile_from_block");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CLAY_PILE.get(), 2)
                .requires(Blocks.CLAY)
                .unlockedBy("has_clay_pile", has(ModItems.CLAY_PILE))
                .save(recipeOutput, "clay_pile_from_block");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SOUL_SAND_PILE.get(), 2)
                .requires(Blocks.SOUL_SAND)
                .unlockedBy("has_soul_sand_pile", has(ModItems.SOUL_SAND_PILE))
                .save(recipeOutput, "soul_sand_pile_from_block");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.STONE_ROCK.get(), 2)
                .requires(ModBlocks.LOOSE_COBBLESTONE)
                .unlockedBy("has_stone_rock", has(ModItems.STONE_ROCK))
                .save(recipeOutput, "stone_rock_from_block");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.DEEPSLATE_ROCK.get(), 2)
                .requires(ModBlocks.LOOSE_COBBLED_DEEPSLATE)
                .unlockedBy("has_deepslate_rock", has(ModItems.DEEPSLATE_ROCK))
                .save(recipeOutput, "deepslate_rock_from_block");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BLACKSTONE_ROCK.get(), 2)
                .requires(ModBlocks.LOOSE_BLACK_COBBLESTONE)
                .unlockedBy("has_blackstone_rock", has(ModItems.BLACKSTONE_ROCK))
                .save(recipeOutput, "blackstone_rock_from_block");

        /* para receitas de smelting e blasting, será usado um item ainda não adicionado
        de mistura entre areia e argila para tijolo.
        */
    }
}
