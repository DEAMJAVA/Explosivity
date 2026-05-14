package net.deamjava.explosivity.gamerule

import com.mojang.brigadier.arguments.FloatArgumentType
import com.mojang.serialization.Codec
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.flag.FeatureFlagSet
import net.minecraft.world.level.gamerules.GameRule
import net.minecraft.world.level.gamerules.GameRuleCategory
import net.minecraft.world.level.gamerules.GameRuleType

object FloatGameRuleHelper {

    fun register(
        id: String,
        category: GameRuleCategory,
        default: Float,
        min: Float = 0f,
        max: Float = 100f
    ): GameRule<Float> = Registry.register(
        BuiltInRegistries.GAME_RULE,
        id,
        GameRule(
            category,
            GameRuleType.INT,                        // no FLOAT variant exists; INT is the safe fallback
            FloatArgumentType.floatArg(min, max),
            { visitor, rule -> },                    // no visitFloat in GameRuleTypeVisitor; no-op
            Codec.floatRange(min, max),
            { f -> (f * 100).toInt() },              // commandResultFunction BEFORE defaultValue
            default,
            FeatureFlagSet.of()
        )
    )
}