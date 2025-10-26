package com.sanjin.block;

import com.mojang.serialization.MapCodec;
import com.sanjin.entity.blockentity.StewStoveBlockEntity;
import com.sanjin.register.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StewStoveBlock extends BaseEntityBlock {

    public static final MapCodec<StewStoveBlock> CODEC = simpleCodec(StewStoveBlock::new);
    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 11, 16);
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final BooleanProperty HAS_SOUP = BooleanProperty.create("has_soup");

    public StewStoveBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(FACING, Direction.NORTH)
                .setValue(LIT, Boolean.FALSE)
                .setValue(HAS_SOUP, Boolean.FALSE)
        );
    }

    // ========= Set particle effects to Stew stove in correct time =========
    @Override
    public void animateTick(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull RandomSource random) {
        if (state.getValue(LIT)) {
            Direction direction = state.getValue(FACING);
            addFlameParticles(level, pos, direction, random);
            addSmokeParticles(level, pos, random);
            if (level.getBlockEntity(pos) instanceof StewStoveBlockEntity stoveEntity && stoveEntity.getCookingState()) {
                addCookingParticles(level, pos, random);
            }
        }
    }
    private void addFlameParticles(Level level, BlockPos pos, Direction direction, RandomSource random) {
        // Calculate the position of fire particles
        double x = pos.getX() + 0.5D;
        double y = pos.getY() + 0.1D;
        double z = pos.getZ() + 0.5D;

        // Set the position in the front of Stew stove
        double offsetX = 0;
        double offsetZ = 0;

        switch (direction) {
            case NORTH -> offsetZ = -0.45;
            case SOUTH -> offsetZ = 0.45;
            case WEST -> offsetX = -0.45;
            case EAST -> offsetX = 0.45;
            default -> offsetX = -0.45;
        }

        // Random Generation fire particles
        for (int i = 0; i < random.nextInt(2) + 1; i++) {
            double particleX = x + offsetX + (random.nextDouble() - 0.5) * 0.2;
            double particleY = y + random.nextDouble() * 0.2;
            double particleZ = z + offsetZ + (random.nextDouble() - 0.5) * 0.2;
            level.addParticle(ParticleTypes.FLAME, particleX, particleY, particleZ, 0, 0, 0);
            if (random.nextFloat() < 0.1) {
                level.addParticle(ParticleTypes.LAVA, particleX, particleY, particleZ, 0, 0, 0);
            }
        }
    }
    private void addSmokeParticles(Level level, BlockPos pos, RandomSource random) {
        // Same with addFlameParticles method
        double x = pos.getX() + 0.5D;
        double y = pos.getY() + 0.9D;
        double z = pos.getZ() + 0.5D;

        for (int i = 0; i < random.nextInt(2) + 1; i++) {
            double particleX = x + (random.nextDouble() - 0.5) * 0.4;
            double particleZ = z + (random.nextDouble() - 0.5) * 0.4;

            level.addParticle(ParticleTypes.SMOKE, particleX, y, particleZ, 0, 0.05, 0);
            if (random.nextFloat() < 0.2) {
                level.addParticle(ParticleTypes.LARGE_SMOKE, particleX, y, particleZ, 0, 0.07, 0);
            }
        }
    }
    private void addCookingParticles(Level level, BlockPos pos, RandomSource random) {
        double x = pos.getX() + 0.5D;
        double y = pos.getY() + 1.0D;
        double z = pos.getZ() + 0.5D;

        // Random generation particles
        ParticleOptions particleType;
        if (random.nextFloat() < 0.7) {
            particleType = ParticleTypes.CLOUD;
        } else if (random.nextFloat() < 0.5) {
            particleType = ParticleTypes.HAPPY_VILLAGER;
        } else {
            particleType = ParticleTypes.EFFECT;
        }

        double particleX = x + (random.nextDouble() - 0.5) * 0.3;
        double particleY = y + random.nextDouble() * 0.2;
        double particleZ = z + (random.nextDouble() - 0.5) * 0.3;

        level.addParticle(particleType, particleX, particleY, particleZ, 0, 0.02, 0);
    }

    // ========= Set the Shape of Stew stove block same as its model =========
    @Override
    public @NotNull VoxelShape getCollisionShape(@Nonnull BlockState state, @Nonnull BlockGetter worldIn, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
        return SHAPE;
    }

    @Override
    public @NotNull RenderShape getRenderShape(@Nonnull BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public @NotNull VoxelShape getShape(@Nonnull BlockState state, @Nonnull BlockGetter worldIn, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
        return SHAPE;
    }

    // ========= Set the Stew stove block always faces to player when it put =========
    @Override
    public @NotNull BlockState rotate(@Nonnull BlockState pState, @Nonnull Rotation pRot) {
        return pState.setValue(FACING, pRot.rotate(pState.getValue(FACING)));
    }

    // ========= Open GUI when player clink the block =========
    @Override
    public MenuProvider getMenuProvider(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        return blockEntity instanceof MenuProvider menuProvider ? menuProvider : null;
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull Player player, @Nonnull BlockHitResult hitResult) {
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(state.getMenuProvider(level, pos));
        }
        return InteractionResult.SUCCESS;
    }

    // ========= Block Lit settings =========
    @Override
    public int getLightEmission(@Nonnull BlockState state, @Nonnull BlockGetter level, @Nonnull BlockPos pos) {
        return state.getValue(LIT) ? 15 : 0;
    }

    // ========= Update block state =========
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@Nonnull Level level, @Nonnull BlockState state, @Nonnull BlockEntityType<T> type){
        return level.isClientSide ? null : createTickerHelper(type, ModBlockEntities.STEW_STOVE_BLOCK_ENTITY.get(), StewStoveBlockEntity::serverTick);
    }

    @Override
    public @NotNull BlockState getStateForPlacement(@Nonnull BlockPlaceContext context) {
        Direction playerFacing = context.getHorizontalDirection();
        return this.defaultBlockState()
                .setValue(FACING, playerFacing.getOpposite())
                .setValue(LIT, Boolean.FALSE)
                .setValue(HAS_SOUP, Boolean.FALSE);
    }

    @Override
    protected void createBlockStateDefinition(final @Nonnull StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, LIT, HAS_SOUP);
    }

    // ========== Other needed settings ==========
    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
        return ModBlockEntities.STEW_STOVE_BLOCK_ENTITY.get().create(pos, state);
    }
}
