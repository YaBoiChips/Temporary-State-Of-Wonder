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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;
import yaboichips.tsow.TSoW;
import yaboichips.tsow.core.TSoWBlockEntities;
import yaboichips.tsow.core.TSoWBlocks;

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
                be.currentTick++;
                if (be.currentTick % INTERVAL == 0) {
                    // Calculate pitch and particle radius
                    float particleRadius = Mth.lerp((float) be.currentTick / DURATION, 1.0f, 20.0f);

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
            double offsetY = world.random.nextDouble();
            double offsetZ = (world.random.nextDouble() - 0.5) * radius;

            world.sendParticles(
                    ParticleTypes.POOF,
                    center.getX() + 0.5 + offsetX,
                    center.getY() + 1.0 + offsetY,
                    center.getZ() + 0.5 + offsetZ,
                    3, // Count
                    0, 0, 0, 0.0 // No velocity
            );
            world.sendParticles(
                    ParticleTypes.FIREWORK,
                    center.getX() + 0.2 + offsetX,
                    center.getY() + 1.2 + offsetY,
                    center.getZ() + 0.7 + offsetZ,
                    5, // Count
                    0, 0, 0, 0.0 // No velocity
            );
            world.sendParticles(
                    ParticleTypes.ANGRY_VILLAGER,
                    center.getX() + 0.1 + offsetX,
                    center.getY() + 0.4 + offsetY,
                    center.getZ() + 0.8 + offsetZ,
                    1, // Count
                    0, 0, 0, 0.0 // No velocity
            );
            world.sendParticles(
                    ParticleTypes.LANDING_OBSIDIAN_TEAR,
                    center.getX() + 1.1 + offsetX,
                    center.getY() + 0.4 + offsetY,
                    center.getZ() + 1.5 + offsetZ,
                    1, // Count
                    0, 0, 0, 0.0 // No velocity
            );
        }
    }

    private static void teleportPlayers(ServerLevel world, BlockPos center) {
        int radius = 1;
        AABB effectArea = new AABB(center).inflate(60);
        for (Player player : world.getEntitiesOfClass(Player.class, effectArea)) {
            if (player instanceof ServerPlayer sPlayer) {
                if (player.level().dimension() == Level.OVERWORLD) {
                    sPlayer.teleportTo(world.getServer().getLevel(TSoW.INTERSTELLAR), 0, -16, 0, 0, 0);
                    sPlayer.setRespawnPosition(TSoW.INTERSTELLAR, new BlockPos(0, -16, 0), -16, true, false);
                    sPlayer.level().setBlockAndUpdate(new BlockPos(0, -16, 0), TSoWBlocks.STASIS_BLOCK.defaultBlockState());
                } else if (player.level().dimension() == TSoW.INTERSTELLAR) {
                    sPlayer.teleportTo(world.getServer().getLevel(TSoW.GIANTS_SWAMP), 0, -16, 0, 0, 0);
                    sPlayer.setRespawnPosition(TSoW.GIANTS_SWAMP, new BlockPos(0, -16, 0), -16, true, false);
                    sPlayer.level().setBlockAndUpdate(new BlockPos(0, -16, 0), TSoWBlocks.STASIS_BLOCK.defaultBlockState());
                } else if (player.level().dimension() == TSoW.GIANTS_SWAMP) {
                    sPlayer.teleportTo(world.getServer().getLevel(TSoW.ABANDONMENT), 0, -16, 0, 0, 0);
                    sPlayer.setRespawnPosition(TSoW.ABANDONMENT, new BlockPos(0, -16, 0), -16, true, false);
                    sPlayer.level().setBlockAndUpdate(new BlockPos(0, -16, 0), TSoWBlocks.STASIS_BLOCK.defaultBlockState());
                } else if (player.level().dimension() == TSoW.ABANDONMENT) {
                    sPlayer.teleportTo(world.getServer().getLevel(Level.OVERWORLD), -605, 57, -215.5, 0, 0);
                    sPlayer.setRespawnPosition(Level.OVERWORLD, new BlockPos(0, 65, 0), -16, true, false);
                }
                for (int x = -radius; x <= radius; x++) {
                    for (int y = 0; y <= radius; y++) {
                        for (int z = -radius; z <= radius; z++) {
                            BlockPos targetPos = sPlayer.blockPosition().offset(x, y, z);
                            world.setBlockAndUpdate(targetPos, Blocks.AIR.defaultBlockState());
                        }
                    }
                }
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
