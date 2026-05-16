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
            GameRuleType.INT,
            FloatArgumentType.floatArg(min, max),
            { visitor, rule -> },
            Codec.floatRange(min, max),
            { f -> (f * 100).toInt() },
            default,
            FeatureFlagSet.of()
        )
    )
}