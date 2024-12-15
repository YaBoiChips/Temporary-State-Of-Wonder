package yaboichips.tsow.common.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Giant;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;
import yaboichips.tsow.core.TSoWEntities;

import java.util.stream.Stream;

public class GiantEntity extends Monster implements GeoEntity {
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    protected static final RawAnimation RUN = RawAnimation.begin().thenLoop("walk");

    private static final EntityDataAccessor<Boolean> CHILD = SynchedEntityData.defineId(GiantEntity.class, EntityDataSerializers.BOOLEAN);

    public GiantEntity(EntityType<? extends Monster> entityType, Level level) {
        super(TSoWEntities.GIANT, level);
    }

    protected void registerGoals() {
        this.addBehaviourGoals();
    }

    protected void addBehaviourGoals() {
        this.goalSelector.addGoal(2, new GiantAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(CHILD, false);
        super.defineSynchedData(builder);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData) {
        setChild(random.nextBoolean());
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData);
    }

    public boolean isChild() {
        return this.entityData.get(CHILD);
    }

    public void setChild(boolean bool) {
        this.entityData.set(CHILD, bool);
    }

    @Override
    public void aiStep() {
        AABB aabb = this.getBoundingBox().inflate(3);
        Stream<BlockPos> pos = BlockPos.betweenClosedStream(aabb);
        pos.forEach(pos1 -> {
            if (this.level().isInWorldBounds(pos1)) {
                BlockState state = level().getBlockState(pos1);
                if (state.getTags().toList().contains(BlockTags.LOGS) || state.getTags().toList().contains(BlockTags.LEAVES)) {
                    Block.dropResources(state, level(), pos1);
                    level().setBlockAndUpdate(pos1, Blocks.AIR.defaultBlockState());
                }
            }
        });
        super.aiStep();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 20f)
                .add(Attributes.MOVEMENT_SPEED, 0.5F)
                .add(Attributes.ATTACK_DAMAGE, 7.0)
                .add(Attributes.FOLLOW_RANGE, 48.0);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, state -> {
            if (state.isMoving()) {
                state.setAndContinue(RUN);
                return PlayState.CONTINUE;
            } else {
                return PlayState.STOP;
            }
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return geoCache;
    }

    public static class GiantAttackGoal extends MeleeAttackGoal{

        GiantEntity giant;
        public GiantAttackGoal(GiantEntity pathfinderMob, double d, boolean bl) {
            super(pathfinderMob, d, bl);
            giant = pathfinderMob;
        }

        @Override
        public boolean canUse() {
            return !giant.isChild();
        }
    }
}