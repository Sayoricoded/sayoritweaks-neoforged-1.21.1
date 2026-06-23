package net.sayoricoded.sayoritweaks.item.custom;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.sayoricoded.sayoritweaks.block.ModBlocks;

import java.util.Map;

public class MortaringItem extends Item {
    private static final Map<Block, Block> MORTARING_MAP =
            Map.of(
                    ModBlocks.LOOSE_COBBLESTONE.get(), Blocks.COBBLESTONE,
                    ModBlocks.LOOSE_COBBLED_DEEPSLATE.get(), Blocks.COBBLED_DEEPSLATE,
                    ModBlocks.LOOSE_BLACK_COBBLESTONE.get(), ModBlocks.BLACK_COBBLESTONE.get()
            );

    public MortaringItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Block clickedBlock = level.getBlockState(context.getClickedPos()).getBlock();

        if (MORTARING_MAP.containsKey(clickedBlock)) {
            if (!level.isClientSide()) {
                level.setBlockAndUpdate(context.getClickedPos(), MORTARING_MAP.get(clickedBlock).defaultBlockState());

                context.getItemInHand().consume(1, context.getPlayer());
                level.playSound(null, context.getClickedPos(), SoundEvents.SLIME_BLOCK_PLACE, SoundSource.BLOCKS);
            }
        }

        return InteractionResult.SUCCESS;
    }
}
