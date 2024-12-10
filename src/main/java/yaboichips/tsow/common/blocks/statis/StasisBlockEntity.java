package yaboichips.tsow.common.blocks.statis;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;
import yaboichips.tsow.TSoW;
import yaboichips.tsow.core.TSoWBlockEntities;

import static software.bernie.geckolib.constant.DefaultAnimations.IDLE;

public class StasisBlockEntity extends BlockEntity implements GeoBlockEntity {


    private static final int DURATION = 200;
    private static final int INTERVAL = 10;
    private int currentTick = 0;
    protected static final RawAnimation RUN = RawAnimation.begin().thenPlayXTimes("animation.stasis_machine.run", 1);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public boolean activated;

    public StasisBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(TSoWBlockEntities.STASIS_BLOCK_ENTITY, blockPos, blockState);
    }

    @Override
    protected void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.loadAdditional(compoundTag, provider);
        this.activated = compoundTag.getBoolean("Activated");
        this.currentTick = compoundTag.getInt("Tick");

    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.saveAdditional(compoundTag, provider);
        compoundTag.putBoolean("Activated", activated);
        compoundTag.putInt("Tick", currentTick);
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, StasisBlockEntity be) {
        if (level instanceof ServerLevel sLevel) {
            if (be.isActivated()) {
                System.out.println(be.currentTick);
                be.currentTick++;
                if (be.currentTick % INTERVAL == 0) {
                    // Calculate pitch and particle radius
                    float pitch = Mth.lerp((float) be.currentTick / DURATION, 0.5f, 2.0f);
                    float particleRadius = Mth.lerp((float) be.currentTick / DURATION, 1.0f, 5.0f);

                    // Play sound
                    level.playSound(null, blockPos, SoundEvents.BELL_BLOCK, SoundSource.BLOCKS, 1.0f, pitch);

                    // Spawn particles
                    spawnSmokeParticles(sLevel, blockPos, particleRadius);


                    if (be.currentTick >= DURATION) {
                        // Teleport players at the end
                        teleportPlayers(sLevel, blockPos);
                        be.setActivated(false);
                    }
                }
            } else {
                be.currentTick = 0;
            }
        }

    }

    private static void spawnSmokeParticles(ServerLevel world, BlockPos center, float radius) {
        for (int i = 0; i < 20; i++) { // Spawn multiple particles
            double offsetX = (world.random.nextDouble() - 0.5) * radius;
            double offsetY = world.random.nextDouble() * 1.0;
            double offsetZ = (world.random.nextDouble() - 0.5) * radius;

            world.sendParticles(
                    ParticleTypes.CAMPFIRE_SIGNAL_SMOKE,
                    center.getX() + 0.5 + offsetX,
                    center.getY() + 1.0 + offsetY,
                    center.getZ() + 0.5 + offsetZ,
                    1, // Count
                    0, 0, 0, 0.0 // No velocity
            );
        }
    }

    private static void teleportPlayers(ServerLevel world, BlockPos center) {
        AABB effectArea = new AABB(center).inflate(30); // 30-block radius
        for (Player player : world.getEntitiesOfClass(Player.class, effectArea)) {
            if (player instanceof ServerPlayer sPlayer) {
                sPlayer.teleportTo(world.getServer().getLevel(TSoW.INTERSTELLAR), 0, -16, 0, 0, 0);
            }
        }
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, state -> {
            if ((state.getAnimatable().getLevel().getBlockEntity(state.getAnimatable().worldPosition)) instanceof StasisBlockEntity be) {
                if (be.isActivated()) {
                    state.setAndContinue(RUN);
                    return PlayState.CONTINUE;
                }
            }
            return state.setAndContinue(IDLE);
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
