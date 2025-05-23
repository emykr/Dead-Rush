package kr.sobin.deadrush


import kr.sobin.deadrush.block.ModBlocks
import net.minecraft.client.Minecraft
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import thedarkcolour.kotlinforforge.forge.MOD_BUS
import thedarkcolour.kotlinforforge.forge.runForDist
import kr.sobin.deadrush.SombiCommand
import kr.sobin.deadrush.SombiArguments
import kr.sobin.lib.v2.Command
import kr.sobin.lib.v2.registry.SystemRegistry
import net.minecraft.server.level.ServerPlayer
import net.minecraft.commands.CommandSourceStack


/**
 * Main mod class. Should be an `object` declaration annotated with `@Mod`.
 * The modid should be declared in this object and should match the modId entry
 * in mods.toml.
 *
 * An example for blocks is in the `blocks` package of this mod.
 */
@Mod(DeathRushMod.ID)
object DeathRushMod {
    const val ID = "deadrush"


    // the logger for our mod
    val LOGGER: Logger = LogManager.getLogger(ID)


    init {
        LOGGER.log(Level.INFO, "Diamond is the best gem!")

        // Register the KDeferredRegister to the mod-specific event bus
        ModBlocks.REGISTRY.register(MOD_BUS)

        val obj = runForDist(
            clientTarget = {
                MOD_BUS.addListener(::onClientSetup)
                Minecraft.getInstance()
            },
            serverTarget = {
                MOD_BUS.addListener(::onServerSetup)
                "test"
            })

        println(obj)

        // SombiCommand 등록 예시
        // 실제로는 서버/명령어 이벤트에서 player, source를 받아야 함
        // 아래는 예시용 더미 객체(null)로 작성
        val dummyPlayer: ServerPlayer = TODO("서버에서 실제 플레이어 객체를 받아와야 합니다.")
        val dummySource: CommandSourceStack = TODO("서버에서 실제 CommandSourceStack을 받아와야 합니다.")
        SystemRegistry.registerCommand(Command(SombiCommand(), SombiArguments(dummyPlayer, dummySource)))
    }

    /**
     * This is used for initializing client specific
     * things such as renderers and keymaps
     * Fired on the mod specific event bus.
     */
    private fun onClientSetup(event: FMLClientSetupEvent) {
        LOGGER.log(Level.INFO, "Initializing client...")

    }

    /**
     * Fired on the global Forge bus.
     */
    private fun onServerSetup(event: FMLDedicatedServerSetupEvent) {
        LOGGER.log(Level.INFO, "Server starting...")
    }
}
