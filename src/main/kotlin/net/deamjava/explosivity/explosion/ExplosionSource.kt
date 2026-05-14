package net.deamjava.explosivity.explosion

import net.deamjava.explosivity.ExplosivityDebug
import net.deamjava.explosivity.gamerule.ExplosivityGameRules
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.gamerules.GameRule

enum class ExplosionSource {
    TNT,
    TNT_MINECART,
    CREEPER,
    CHARGED_CREEPER,
    END_CRYSTAL,
    BED,
    RESPAWN_ANCHOR,
    UNKNOWN;

    fun getEntityDamageMultiplier(level: ServerLevel): Float {
        val rule = entityDamageRule
        if (rule == null) {
            ExplosivityDebug.log("getEntityDamageMultiplier: source=$name has no rule, returning 1.0")
            return 1f
        }
        val value = level.gameRules.get(rule)
        ExplosivityDebug.log("getEntityDamageMultiplier: source=$name rule=${rule.id()} value=$value")
        return value
    }

    fun getBlockDamageMultiplier(level: ServerLevel): Float {
        val rule = blockDamageRule
        if (rule == null) {
            ExplosivityDebug.log("getBlockDamageMultiplier: source=$name has no rule, returning 1.0")
            return 1f
        }
        val value = level.gameRules.get(rule)
        ExplosivityDebug.log("getBlockDamageMultiplier: source=$name rule=${rule.id()} value=$value")
        return value
    }

    private val entityDamageRule: GameRule<Float>? get() = when (this) {
        TNT             -> ExplosivityGameRules.TNT_ENTITY_DAMAGE
        TNT_MINECART    -> ExplosivityGameRules.TNT_MINECART_ENTITY_DAMAGE
        CREEPER         -> ExplosivityGameRules.CREEPER_ENTITY_DAMAGE
        CHARGED_CREEPER -> ExplosivityGameRules.CHARGED_CREEPER_ENTITY_DAMAGE
        END_CRYSTAL     -> ExplosivityGameRules.END_CRYSTAL_ENTITY_DAMAGE
        BED             -> ExplosivityGameRules.BED_ENTITY_DAMAGE
        RESPAWN_ANCHOR  -> ExplosivityGameRules.RESPAWN_ANCHOR_ENTITY_DAMAGE
        UNKNOWN         -> null
    }

    private val blockDamageRule: GameRule<Float>? get() = when (this) {
        TNT             -> ExplosivityGameRules.TNT_BLOCK_DAMAGE
        TNT_MINECART    -> ExplosivityGameRules.TNT_MINECART_BLOCK_DAMAGE
        CREEPER         -> ExplosivityGameRules.CREEPER_BLOCK_DAMAGE
        CHARGED_CREEPER -> ExplosivityGameRules.CHARGED_CREEPER_BLOCK_DAMAGE
        END_CRYSTAL     -> ExplosivityGameRules.END_CRYSTAL_BLOCK_DAMAGE
        BED             -> ExplosivityGameRules.BED_BLOCK_DAMAGE
        RESPAWN_ANCHOR  -> ExplosivityGameRules.RESPAWN_ANCHOR_BLOCK_DAMAGE
        UNKNOWN         -> null
    }
}