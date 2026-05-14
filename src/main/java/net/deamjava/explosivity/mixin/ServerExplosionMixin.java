// Updated ServerExplosionMixin.java
package net.deamjava.explosivity.mixin;

import net.deamjava.explosivity.ExplosivityDebug;
import net.deamjava.explosivity.explosion.ExplosionSource;
import net.deamjava.explosivity.explosion.ExplosionSourceResolver;
import net.deamjava.explosivity.explosion.ExplosivityPendingTag;
import net.deamjava.explosivity.explosion.IExplosivityTagged;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.ServerExplosion;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerExplosion.class)
public class ServerExplosionMixin implements IExplosivityTagged {

    @Unique
    private ExplosionSource explosivity$explosionSource = ExplosionSource.UNKNOWN;

    @Unique
    private boolean explosivity$radiusScaled = false;

    @Override
    public ExplosionSource explosivity$getSource() {
        return this.explosivity$explosionSource;
    }

    @Override
    public void explosivity$setSource(ExplosionSource source) {
        this.explosivity$explosionSource = source;
    }

    @Inject(
            method = "<init>(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/damagesource/DamageSource;Lnet/minecraft/world/level/ExplosionDamageCalculator;Lnet/minecraft/world/phys/Vec3;FZLnet/minecraft/world/level/Explosion$BlockInteraction;)V",
            at = @At("TAIL")
    )
    private void explosivity$captureSource(
            ServerLevel level,
            @Nullable Entity source,
            @Nullable DamageSource damageSource,
            @Nullable ExplosionDamageCalculator damageCalculator,
            Vec3 center,
            float radius,
            boolean fire,
            BlockInteraction blockInteraction,
            CallbackInfo ci
    ) {
        ExplosionSource pending = ExplosivityPendingTag.takeAndClear();
        if (pending != null) {
            ExplosivityDebug.log("captureSource: using pending tag -> " + pending);
            this.explosivity$explosionSource = pending;
        } else {
            this.explosivity$explosionSource = ExplosionSourceResolver.resolve(source, damageSource);
            ExplosivityDebug.log("captureSource: auto-resolved -> " + this.explosivity$explosionSource);
        }
    }

    @Inject(method = "explode", at = @At("HEAD"))
    private void explosivity$applyBlockRadiusScale(CallbackInfoReturnable<Integer> cir) {
        if (this.explosivity$radiusScaled) return;
        this.explosivity$radiusScaled = true;

        ServerExplosionAccessor self = (ServerExplosionAccessor) (Object) this;
        float originalRadius = self.explosivity$getRadius();
        float multiplier = this.explosivity$explosionSource.getBlockDamageMultiplier(
                ((ServerExplosion) (Object) this).level()
        );
        float newRadius = originalRadius * multiplier;
        ExplosivityDebug.log("applyBlockRadiusScale: source=" + this.explosivity$explosionSource
                + " originalRadius=" + originalRadius
                + " multiplier=" + multiplier
                + " newRadius=" + newRadius);
        self.explosivity$setRadius(newRadius);
    }

    @ModifyArg(
            method = "hurtEntities",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;hurtServer(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;F)Z"
            ),
            index = 2
    )
    private float explosivity$scaleEntityDamage(float original) {
        float multiplier = this.explosivity$explosionSource.getEntityDamageMultiplier(
                ((ServerExplosion) (Object) this).level()
        );
        float scaled = original * multiplier;
        ExplosivityDebug.log("scaleEntityDamage: source=" + this.explosivity$explosionSource
                + " original=" + original
                + " multiplier=" + multiplier
                + " scaled=" + scaled);
        return scaled;
    }
}