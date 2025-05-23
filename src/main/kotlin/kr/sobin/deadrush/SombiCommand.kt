package kr.sobin.deadrush

import kr.sobin.common.command.SimpleCommand
import kr.sobin.common.command.SimpleArguments
import net.minecraft.server.level.ServerPlayer
import net.minecraft.commands.CommandSourceStack

class SombiArguments(val player: ServerPlayer, val source: CommandSourceStack) : SimpleArguments

class SombiCommand : SimpleCommand {
    override fun run(arguments: SimpleArguments) {
        if (arguments is SombiArguments) {
            // 예시: 플레이어에게 메시지 보내기
            arguments.player.sendSystemMessage(net.minecraft.network.chat.Component.literal("SombiCommand 실행됨!"))
        }
    }
}

