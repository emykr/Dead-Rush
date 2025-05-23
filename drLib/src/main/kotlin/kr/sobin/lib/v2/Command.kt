package kr.sobin.lib.v2

import kr.sobin.common.command.SimpleCommand
import kr.sobin.common.command.SimpleArguments

class Command(
    private val command: SimpleCommand,
    private val arguments: SimpleArguments
) {
    fun execute() {
        command.run(arguments)
    }
}

