package yaboichips.tsow.common.blocks.statis;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import yaboichips.tsow.TSoW;
import yaboichips.tsow.core.TSoWBlockEntities;
import yaboichips.tsow.core.TSoWItems;
import yaboichips.tsow.core.TSoWSounds;

public class StasisBlock extends BaseEntityBlock implements EntityBlock {
    public StasisBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return TSoWBlockEntities.STASIS_BLOCK_ENTITY.create(blockPos, blockState);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        ResourceKey<Level> dimension = level.dimension();
        ItemStack requiredItem;
        if (dimension == TSoW.INTERSTELLAR) {
            requiredItem = new ItemStack(TSoWItems.TIME);
        } else if (dimension == TSoW.GIANTS_SWAMP) {
            requiredItem = new ItemStack(TSoWItems.GIANTS_HEART);
        } else if (dimension == TSoW.ABANDONMENT) {
            requiredItem = new ItemStack(TSoWItems.CRUDE_BATTERY);
        } else {
            requiredItem = new ItemStack(TSoWItems.KEY);
        }
        if (level.getBlockEntity(blockPos) instanceof StasisBlockEntity be && itemStack.is(requiredItem.getItem())){
            level.playSound(null, blockPos, TSoWSounds.STARTUP, SoundSource.BLOCKS, 1f, 1f);
            be.setActivated(true);
        }
        return ItemInteractionResult.SUCCESS;
    }

    @Override
    protected RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return makeShape();
    }

    public VoxelShape makeShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.0625, 0.9375, 0.3125, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.375, 0.3125, 0.125, 0.625, 0.5, 0.3125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0.3125, 0.375, 0.3125, 0.5, 0.625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.6875, 0.3125, 0.375, 0.875, 0.5, 0.625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.375, 0.3125, 0.6875, 0.625, 0.5, 0.875), BooleanOp.OR);
        return shape;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, TSoWBlockEntities.STASIS_BLOCK_ENTITY, StasisBlockEntity::tick);
    }
}
