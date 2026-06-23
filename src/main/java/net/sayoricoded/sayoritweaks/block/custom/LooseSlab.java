package net.sayoricoded.sayoritweaks.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LooseSlab extends Block implements SimpleWaterloggedBlock, Fallable {

    // ── Block State Properties ────────────────────────────────────────────────

    public static final EnumProperty<SlabType>  TYPE       = BlockStateProperties.SLAB_TYPE;
    public static final BooleanProperty         WATERLOGGED = BlockStateProperties.WATERLOGGED;

    // Vanilla slab VoxelShapes
    private static final VoxelShape BOTTOM_SHAPE = Block.box(0, 0,  0, 16, 8,  16);
    private static final VoxelShape TOP_SHAPE    = Block.box(0, 8,  0, 16, 16, 16);
    private static final VoxelShape DOUBLE_SHAPE = Block.box(0, 0,  0, 16, 16, 16);

    // ── Codec (required by NeoForge 1.21.x block registry) ───────────────────

    public static final MapCodec<LooseSlab> CODEC =
            simpleCodec(LooseSlab::new);

    @Override
    public MapCodec<? extends LooseSlab> codec() {
        return CODEC;
    }

    // ── Constructor ───────────────────────────────────────────────────────────

    public LooseSlab(Properties properties) {
        super(properties);
        // Default state: BOTTOM slab, not waterlogged — mirrors vanilla SlabBlock
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(TYPE,        SlabType.BOTTOM)
                        .setValue(WATERLOGGED, false)
        );
    }

    // ── State Definition ──────────────────────────────────────────────────────

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TYPE, WATERLOGGED);
    }

    // ── VoxelShape ────────────────────────────────────────────────────────────

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level,
                               BlockPos pos, CollisionContext context) {
        return switch (state.getValue(TYPE)) {
            case TOP    -> TOP_SHAPE;
            case DOUBLE -> DOUBLE_SHAPE;
            default     -> BOTTOM_SHAPE;   // BOTTOM
        };
    }

    // ── Placement Logic (mirrors SlabBlock) ───────────────────────────────────

    /**
     * Determines the BlockState to use when a player places the block.
     * Mirrors SlabBlock.getStateForPlacement() so stacking two slabs into a
     * DOUBLE slab still works. Also handles waterlogging on placement.
     */
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos       clickedPos   = context.getClickedPos();
        BlockState     existingState = context.getLevel().getBlockState(clickedPos);

        // If clicking onto an existing FallingSlabBlock of the same type → DOUBLE
        if (existingState.is(this)) {
            return existingState
                    .setValue(TYPE,        SlabType.DOUBLE)
                    .setValue(WATERLOGGED, false);
        }

        // Otherwise determine TOP vs BOTTOM from click face / cursor position
        FluidState fluidState = context.getLevel().getFluidState(clickedPos);
        boolean    waterlogged = fluidState.getType() == Fluids.WATER;

        Direction  clickedFace = context.getClickedFace();
        SlabType   slabType;

        if (clickedFace == Direction.DOWN) {
            slabType = SlabType.TOP;
        } else if (clickedFace == Direction.UP) {
            slabType = SlabType.BOTTOM;
        } else {
            // Side-click: choose based on where within the block the player clicked
            slabType = (context.getClickLocation().y - clickedPos.getY()) > 0.5
                    ? SlabType.TOP
                    : SlabType.BOTTOM;
        }

        return this.defaultBlockState()
                .setValue(TYPE,        slabType)
                .setValue(WATERLOGGED, waterlogged);
    }

    // ── SlabBlock stacking helper ─────────────────────────────────────────────

    /**
     * Allows a second slab to be placed on top of (or below) an existing one
     * to form a DOUBLE slab — identical logic to vanilla SlabBlock.
     */
    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        SlabType type = state.getValue(TYPE);
        if (type == SlabType.DOUBLE || context.getItemInHand().getItem() != this.asItem()) {
            return false;
        }
        if (context.replacingClickedOnBlock()) {
            Direction face      = context.getClickedFace();
            double    hitY      = context.getClickLocation().y - context.getClickedPos().getY();
            return (type == SlabType.BOTTOM && (face == Direction.UP || hitY > 0.5))
                    || (type == SlabType.TOP    && (face == Direction.DOWN || hitY <= 0.5));
        }
        return true;
    }

    // ── Waterlogging ──────────────────────────────────────────────────────────

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED)
                ? Fluids.WATER.getSource(false)
                : super.getFluidState(state);
    }

    /**
     * Keeps waterlogging in sync when neighbour fluids change (e.g. player
     * places water next to the slab).
     */
    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState,
                                  LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }

    // ── Gravity / Fallable ────────────────────────────────────────────────────

    /**
     * Called every tick on the server. If the block below cannot support this
     * slab, trigger a fall — exactly how vanilla FallingBlock works.
     */
    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos,
                        BlockState oldState, boolean movedByPiston) {
        level.scheduleTick(pos, this, this.getDelayAfterPlace());
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (isFree(level.getBlockState(pos.below())) && pos.getY() >= level.getMinBuildHeight()) {
            FallingBlockEntity fallingEntity = FallingBlockEntity.fall(level, pos, state);
            // Optional: keep the entity alive longer for tall drops
            fallingEntity.time = 1;
        }
    }

    /**
     * Schedules a gravity check whenever a neighbouring block updates —
     * ensures slabs fall if support is removed later (e.g. sand falls, then
     * this slab's neighbour disappears).
     */
    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos,
                                Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        level.scheduleTick(pos, this, this.getDelayAfterPlace());
    }

    /**
     * Tick delay before the fall starts (in game ticks).
     * Vanilla uses 2 for sand/gravel.
     */
    protected int getDelayAfterPlace() {
        return 2;
    }

    /**
     * Returns true if the block below is passable/free so that falling should
     * be triggered. Mirrors the same helper in vanilla FallingBlock.
     */
    public static boolean isFree(BlockState state) {
        return state.isAir()
                || state.is(net.minecraft.tags.BlockTags.FIRE)
                || state.liquid()
                || state.canBeReplaced();
    }

    // ── Utility ───────────────────────────────────────────────────────────────

    /** Convenience: is this block currently a full double-slab? */
    public static boolean isDouble(BlockState state) {
        return state.getValue(TYPE) == SlabType.DOUBLE;
    }
}