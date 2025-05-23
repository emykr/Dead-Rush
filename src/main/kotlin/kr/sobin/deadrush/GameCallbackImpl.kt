package kr.sobin.deadrush

import kr.sobin.common.GameCallback
import org.apache.logging.log4j.LogManager

class GameCallbackImpl : GameCallback {
    private val logger = LogManager.getLogger("GameCallbackImpl")
    override fun onEvent(message: String) {
        logger.info("[콜백] $message")
    }
}

