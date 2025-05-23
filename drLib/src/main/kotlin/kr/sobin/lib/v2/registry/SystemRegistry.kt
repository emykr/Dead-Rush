package kr.sobin.lib.v2.registry

import kr.sobin.common.command.SimpleManager
import kr.sobin.lib.v2.Command

object SystemRegistry {
    private val commands = mutableListOf<Command>()
    private var manager: SimpleManager? = null

    fun registerCommand(command: Command) {
        commands.add(command)
    }

    fun setManager(m: SimpleManager) {
        manager = m
    }

    fun executeAll() {
        commands.forEach { it.execute() }
    }
}

