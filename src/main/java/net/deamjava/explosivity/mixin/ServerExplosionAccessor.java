package net.deamjava.explosivity.mixin;

import net.minecraft.world.level.ServerExplosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ServerExplosion.class)
public interface ServerExplosionAccessor {

    @Accessor("radius")
    float explosivity$getRadius();

    @Mutable
    @Accessor("radius")
    void explosivity$setRadius(float radius);
}