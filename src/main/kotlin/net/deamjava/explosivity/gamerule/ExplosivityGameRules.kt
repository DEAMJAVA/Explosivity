package net.deamjava.explosivity.gamerule

import net.minecraft.world.level.gamerules.GameRule
import net.minecraft.world.level.gamerules.GameRuleCategory

object ExplosivityGameRules {

    lateinit var TNT_ENTITY_DAMAGE: GameRule<Float>
    lateinit var TNT_BLOCK_DAMAGE: GameRule<Float>

    lateinit var TNT_MINECART_ENTITY_DAMAGE: GameRule<Float>
    lateinit var TNT_MINECART_BLOCK_DAMAGE: GameRule<Float>

    lateinit var CREEPER_ENTITY_DAMAGE: GameRule<Float>
    lateinit var CREEPER_BLOCK_DAMAGE: GameRule<Float>

    lateinit var CHARGED_CREEPER_ENTITY_DAMAGE: GameRule<Float>
    lateinit var CHARGED_CREEPER_BLOCK_DAMAGE: GameRule<Float>

    lateinit var END_CRYSTAL_ENTITY_DAMAGE: GameRule<Float>
    lateinit var END_CRYSTAL_BLOCK_DAMAGE: GameRule<Float>

    lateinit var BED_ENTITY_DAMAGE: GameRule<Float>
    lateinit var BED_BLOCK_DAMAGE: GameRule<Float>

    lateinit var RESPAWN_ANCHOR_ENTITY_DAMAGE: GameRule<Float>
    lateinit var RESPAWN_ANCHOR_BLOCK_DAMAGE: GameRule<Float>

    fun register() {
        TNT_ENTITY_DAMAGE             = FloatGameRuleHelper.register("tnt_entity_damage_multiplier",              GameRuleCategory.MISC, 1f)
        TNT_BLOCK_DAMAGE              = FloatGameRuleHelper.register("tnt_block_damage_multiplier",               GameRuleCategory.MISC, 1f)
        TNT_MINECART_ENTITY_DAMAGE    = FloatGameRuleHelper.register("tnt_minecart_entity_damage_multiplier",     GameRuleCategory.MISC, 1f)
        TNT_MINECART_BLOCK_DAMAGE     = FloatGameRuleHelper.register("tnt_minecart_block_damage_multiplier",      GameRuleCategory.MISC, 1f)
        CREEPER_ENTITY_DAMAGE         = FloatGameRuleHelper.register("creeper_entity_damage_multiplier",          GameRuleCategory.MISC, 1f)
        CREEPER_BLOCK_DAMAGE          = FloatGameRuleHelper.register("creeper_block_damage_multiplier",           GameRuleCategory.MISC, 1f)
        CHARGED_CREEPER_ENTITY_DAMAGE = FloatGameRuleHelper.register("charged_creeper_entity_damage_multiplier",  GameRuleCategory.MISC, 1f)
        CHARGED_CREEPER_BLOCK_DAMAGE  = FloatGameRuleHelper.register("charged_creeper_block_damage_multiplier",   GameRuleCategory.MISC, 1f)
        END_CRYSTAL_ENTITY_DAMAGE     = FloatGameRuleHelper.register("end_crystal_entity_damage_multiplier",      GameRuleCategory.MISC, 1f)
        END_CRYSTAL_BLOCK_DAMAGE      = FloatGameRuleHelper.register("end_crystal_block_damage_multiplier",       GameRuleCategory.MISC, 1f)
        BED_ENTITY_DAMAGE             = FloatGameRuleHelper.register("bed_entity_damage_multiplier",              GameRuleCategory.MISC, 1f)
        BED_BLOCK_DAMAGE              = FloatGameRuleHelper.register("bed_block_damage_multiplier",               GameRuleCategory.MISC, 1f)
        RESPAWN_ANCHOR_ENTITY_DAMAGE  = FloatGameRuleHelper.register("respawn_anchor_entity_damage_multiplier",   GameRuleCategory.MISC, 1f)
        RESPAWN_ANCHOR_BLOCK_DAMAGE   = FloatGameRuleHelper.register("respawn_anchor_block_damage_multiplier",    GameRuleCategory.MISC, 1f)
    }
}