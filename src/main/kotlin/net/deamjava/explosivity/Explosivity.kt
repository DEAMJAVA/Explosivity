package net.deamjava.explosivity

import net.deamjava.explosivity.gamerule.ExplosivityGameRules
import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

object Explosivity : ModInitializer {
	const val MOD_ID = "explosivity"
	val LOGGER = LoggerFactory.getLogger(MOD_ID)

	override fun onInitialize() {
		ExplosivityGameRules.register()
		LOGGER.info("Explosivity initialized")
	}
}