package yaboichips.tsow.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yaboichips.tsow.TSoW;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow
    public abstract Level level();

    @Shadow
    private Vec3 deltaMovement;

    @Inject(method = "setDeltaMovement(Lnet/minecraft/world/phys/Vec3;)V", at = @At("HEAD"), cancellable = true)
    private void stellarisGravity(Vec3 vec3, CallbackInfo ci) {
        if (this.level().dimension() == TSoW.INTERSTELLAR) {
            ci.cancel();

            this.deltaMovement = TSoW.modifyGravityFallSpeed(vec3, (Entity) (Object) this);
        }
    }
}
