package net.deamjava.explosivity

object ExplosivityDebug {
    var enabled: Boolean = false

    @JvmStatic
    fun log(message: String) {
        if (enabled) Explosivity.LOGGER.info("[Explosivity Debug] $message")
    }
}