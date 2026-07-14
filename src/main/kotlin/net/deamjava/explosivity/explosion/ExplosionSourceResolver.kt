package net.deamjava.explosivity.explosion

import net.deamjava.explosivity.ExplosivityDebug
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityTypes
import net.minecraft.world.entity.item.PrimedTnt
import net.minecraft.world.entity.monster.Creeper
import net.minecraft.world.entity.vehicle.minecart.MinecartTNT

object ExplosionSourceResolver {

    @JvmStatic
    fun resolve(source: Entity?, damageSource: DamageSource?): ExplosionSource {
        val resolved = when {
            source is Creeper && source.isPowered  -> ExplosionSource.CHARGED_CREEPER
            source is Creeper                      -> ExplosionSource.CREEPER
            source is PrimedTnt                    -> ExplosionSource.TNT
            source is MinecartTNT                  -> ExplosionSource.TNT_MINECART
            source?.type == EntityTypes.END_CRYSTAL -> ExplosionSource.END_CRYSTAL
            else                                   -> ExplosionSource.UNKNOWN
        }
        ExplosivityDebug.log(
            "resolve: sourceEntity=${source?.javaClass?.simpleName ?: "null"} " +
                    "damageSourceNull=${damageSource == null} -> $resolved"
        )
        return resolved
    }
}