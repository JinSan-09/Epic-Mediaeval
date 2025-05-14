package com.sanjin.block;

import com.mojang.serialization.MapCodec;
import com.sanjin.entity.blockentity.FermentationBarrelBlockEntity;
import com.sanjin.register.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FermentationBarrelBlock extends BaseEntityBlock {

    public static final MapCodec<FermentationBarrelBlock> CODEC = simpleCodec(FermentationBarrelBlock::new);
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final VoxelShape BODY_SHAPE = Block.box(1, 0, 1, 15, 13, 15);
    public static final VoxelShape TOP_SHAPE = Block.box(0, 13, 0, 16, 15, 16);
    public static final VoxelShape SHAPE = Shapes.joinUnoptimized(BODY_SHAPE,TOP_SHAPE, BooleanOp.OR);
    public static final BooleanProperty FULL = BooleanProperty.create("full");

    public FermentationBarrelBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(FACING, Direction.NORTH)
                .setValue(FULL, Boolean.FALSE)
        );
    }


    // ========= Get recipe output if the FULL is true =========
    @Override
    protected @NotNull InteractionResult useItemOn(@NotNull ItemStack stack, BlockState state, @NotNull Level level, @NotNull BlockPos pos, Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {

        ItemStack heldItem = player.getItemInHand(hand);
        if (state.getValue(FULL)) {
            if (level.getBlockEntity(pos) instanceof FermentationBarrelBlockEntity entity) {
                return entity.extractFermentedItem(player, heldItem);
            }
        }
        return InteractionResult.TRY_WITH_EMPTY_HAND;
    }


    // ========= Update block state =========
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type){
        return level.isClientSide ? null : createTickerHelper(type, ModBlockEntities.FERMENTATION_BARREL_BLOCK_ENTITY.get(), FermentationBarrelBlockEntity::serverTick);
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, FULL);
    }

    @Override
    public @NotNull BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction playerFacing = context.getHorizontalDirection();
        return this.defaultBlockState()
                .setValue(FACING, playerFacing.getOpposite())
                .setValue(FULL, Boolean.FALSE);
    }


    // ========= Open GUI when player clink the block =========
    @Override
    public MenuProvider getMenuProvider(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        return blockEntity instanceof MenuProvider menuProvider ? menuProvider : null;
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hitResult) {
        if (state.getValue(FULL)) {
            return InteractionResult.PASS;
        }
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(state.getMenuProvider(level, pos));
        }
        return InteractionResult.SUCCESS;
    }


    // ========= Set the Shape of Stew stove block same as its model =========
    @Override
    public @NotNull VoxelShape getCollisionShape(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }


    // ========= Set the Stew stove block always faces to player when it put =========
    @Override
    public @NotNull BlockState rotate(BlockState pState, Rotation pRot) {
        return pState.setValue(FACING, pRot.rotate(pState.getValue(FACING)));
    }


    // ========= Other needed settings =========
    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return ModBlockEntities.FERMENTATION_BARREL_BLOCK_ENTITY.get().create(pos, state);
    }
}
