package yaboichips.tsow.mixin;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yaboichips.tsow.TSoW;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {


    @Shadow
    public abstract float getJumpPower();

    @Shadow
    protected boolean jumping;

    @Inject(method = "jumpFromGround", at = @At(value = "HEAD"), cancellable = true)
    private void modifyJumpFactor(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        Level world = entity.level();

        if (world.dimension() == TSoW.INTERSTELLAR) {
            if (entity instanceof Player player) {
                if (player.isCreative())
                    return;
            }
            ci.cancel();
            if (world.isClientSide) {
                world.addParticle(ParticleTypes.GUST_EMITTER_LARGE, entity.getX(), entity.getY(), entity.getZ(), 1, 1, 1);
            }
            entity.playSound(SoundEvents.PHANTOM_FLAP);
            TSoW.modifyJumpFactor(entity, this.getJumpPower());
            this.jumping = true;
        }
    }

    @Inject(method = "calculateFallDamage", at = @At("HEAD"), cancellable = true)
    private void removeFallDamage(float distance, float damageMultiplier, CallbackInfoReturnable<Integer> cir) {
        Level world = ((LivingEntity) (Object) this).level();
        if (world.dimension() == TSoW.INTERSTELLAR) {
            cir.setReturnValue(0);
        }
    }
}
