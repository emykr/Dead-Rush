package kr.sobin.lib

import kr.sobin.common.GameCallback

object LibBase {
    private var callback: GameCallback? = null

    fun registerCallback(cb: GameCallback) {
        callback = cb
    }

    fun doSomething() {
        callback?.onEvent("drLib에서 콜백 호출!")
    }
}

