package at.kaindorf.arcane_conjouring.block;

import at.kaindorf.arcane_conjouring.block.entity.WandWorkbenchBlockEntity;
import at.kaindorf.arcane_conjouring.block.state.WandWorkbenchPart;
import at.kaindorf.arcane_conjouring.block.state.properties.ModBlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class WandWorkbenchBlock extends BaseEntityBlock {

    public static final EnumProperty<WandWorkbenchPart> PART = ModBlockStateProperties.WAND_WORKBENCH_PART;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;


    public WandWorkbenchBlock(Properties properties) {
        super(properties);
        this.registerDefaultState( this.getStateDefinition().any().setValue(PART, WandWorkbenchPart.FOOT));
    }


    private static final VoxelShape SHAPE =
            Block.box(0, 0, 0, 16, 16, 16);

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction direction = context.getHorizontalDirection().getClockWise();
        BlockPos blockPos1 = context.getClickedPos();
        BlockPos blockPos2 = blockPos1.relative(direction);
        Level level = context.getLevel();
        return  level.getBlockState(blockPos2).canBeReplaced(context) && level.getWorldBorder().isWithinBounds(blockPos2) ? this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()) : null;
//        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity livingEntity, ItemStack itemStack) {
        super.setPlacedBy(level, blockPos, blockState, livingEntity, itemStack);
        if (!level.isClientSide) {
            BlockPos pos = blockPos.relative(blockState.getValue(FACING).getCounterClockWise());
            level.setBlock(pos, blockState.setValue(PART, WandWorkbenchPart.HEAD), 3);
            level.blockUpdated(pos, Blocks.AIR);
            blockState.updateNeighbourShapes(level, blockPos, 3);
        }
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, PART);
    }

    public PushReaction getPistonPushReaction(BlockState blockState) {
        return PushReaction.DESTROY;
    }


    @Override
    public RenderShape getRenderShape(BlockState state) {
        if (state.getValue(PART) == WandWorkbenchPart.HEAD) {
            return RenderShape.INVISIBLE;
        }
        return RenderShape.MODEL;
    }

    public static Direction getConnectedDirection(BlockState blockState) {
        Direction direction = blockState.getValue(FACING);
        return blockState.getValue(PART) == WandWorkbenchPart.HEAD ? direction.getOpposite() : direction;
    }

    public static DoubleBlockCombiner.BlockType getBlockType(BlockState blockState) {
        WandWorkbenchPart part = blockState.getValue(PART);
        return part == WandWorkbenchPart.HEAD ? DoubleBlockCombiner.BlockType.FIRST : DoubleBlockCombiner.BlockType.SECOND;
    }

    private static Direction getNeighbourDirection(WandWorkbenchPart part, Direction direction) {
        return part == WandWorkbenchPart.FOOT ? direction.getCounterClockWise() : direction.getClockWise();
    }

    public BlockState updateShape(BlockState blockState1, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos1, BlockPos blockPos2) {
        if (direction == getNeighbourDirection(blockState1.getValue(PART), blockState1.getValue(FACING))) {
            return blockState2.is(this) && blockState2.getValue(PART) != blockState1.getValue(PART) ? blockState1 : Blocks.AIR.defaultBlockState();
        } else {
            return super.updateShape(blockState1, direction, blockState2, levelAccessor, blockPos1, blockPos2);
        }
    }



    public void playerWillDestroy(Level level, BlockPos blockPos, BlockState blockState, Player player) {
        if (!level.isClientSide && player.isCreative()) {
            WandWorkbenchPart part = blockState.getValue(PART);
            if (part == WandWorkbenchPart.FOOT) {
                BlockPos blockpos = blockPos.relative(getNeighbourDirection(part, blockState.getValue(FACING)));
                BlockState blockstate = level.getBlockState(blockpos);
                if (blockstate.is(this) && blockstate.getValue(PART) == WandWorkbenchPart.HEAD) {
                    level.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 35);
                    level.levelEvent(player, 2001, blockpos, Block.getId(blockstate));
                }
            }
        }

        super.playerWillDestroy(level, blockPos, blockState, player);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof WandWorkbenchBlockEntity) {
                ((WandWorkbenchBlockEntity) blockEntity).drops();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {

        if (level.isClientSide()) {
            return  InteractionResult.CONSUME;
        } else {
            if (blockState.getValue(PART) != WandWorkbenchPart.HEAD) {
                blockPos = blockPos.relative(blockState.getValue(FACING).getCounterClockWise());
                blockState = level.getBlockState(blockPos);
                if (!blockState.is(this)) {
                    return InteractionResult.CONSUME;
                }
            }
            BlockEntity blockEntity = level.getBlockEntity(blockPos);
            if (blockEntity instanceof WandWorkbenchBlockEntity) {
                NetworkHooks.openScreen(((ServerPlayer) player), (WandWorkbenchBlockEntity) blockEntity, blockPos);
            } else {
                throw new IllegalStateException("Container provider missing");
            }

            return InteractionResult.SUCCESS;
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new WandWorkbenchBlockEntity(pos, state);
    }

}
