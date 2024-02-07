package net.spaceeye.vsource.items

import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.phys.BlockHitResult
import net.spaceeye.vsource.LOG
import net.spaceeye.vsource.rendering.RenderEvents
import net.spaceeye.vsource.rendering.RenderingUtils
import net.spaceeye.vsource.utils.CARenderType
import net.spaceeye.vsource.utils.Vector3d

class TestTool: BaseTool() {
    var blockPos: BlockPos? = null

    override fun activatePrimaryFunction(level: Level, player: Player, clipResult: BlockHitResult) {
        when (level) {
            is ClientLevel -> {
                if (blockPos == null) {blockPos = clipResult.blockPos; return}
                if (blockPos == clipResult.blockPos) {resetState(); return}

                RenderEvents.WORLD.clearListeners()

                val pos1 = Vector3d(blockPos!!) + 0.5
                val pos2 = Vector3d(clipResult.blockPos) + 0.5

                RenderEvents.WORLD.register {
                    poseStack, buffer, camera ->
                    val ivertexBuilder = buffer.getBuffer(CARenderType.WIRE)

                    poseStack.pushPose()

                    val cameraPos = Vector3d(camera.position)

                    val tpos1 = pos1 - cameraPos
                    val tpos2 = pos2 - cameraPos
                    poseStack.translate(tpos1.x, tpos1.y, tpos1.z)

                    val matrix = poseStack.last().pose()
                    RenderingUtils.Quad.makeFlatRect(ivertexBuilder, matrix,
                        255, 0, 0, 255, 255, 1.0,
                        tpos1, tpos2)

                    poseStack.popPose()
                }

                resetState()
            }
            is ServerLevel -> {
            }
        }

    }

    override fun resetState() {
        LOG("RESETTING STATE")
        blockPos = null
    }
}