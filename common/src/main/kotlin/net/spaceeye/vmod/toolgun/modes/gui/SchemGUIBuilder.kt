package net.spaceeye.vmod.toolgun.modes.gui

import com.google.common.io.Files
import gg.essential.elementa.components.*
import gg.essential.elementa.constraints.*
import gg.essential.elementa.dsl.*
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.Component
import net.spaceeye.vmod.guiElements.Button
import net.spaceeye.vmod.guiElements.makeTextEntry
import net.spaceeye.vmod.limits.StrLimit
import net.spaceeye.vmod.networking.FakePacketContext
import net.spaceeye.vmod.toolgun.modes.GUIBuilder
import net.spaceeye.vmod.toolgun.modes.state.ClientPlayerSchematics
import net.spaceeye.vmod.toolgun.modes.state.SchemMode
import net.spaceeye.vmod.toolgun.modes.state.SchemNetworking
import java.awt.Color
import java.util.*

class SaveForm(val mode: SchemMode): UIBlock(Color.GRAY.brighter()) {
    var filename = ""
    var isDragging = false

    private var dragOffset: Pair<Float, Float> = 0f to 0f

    init {
        constrain {
            x = CenterConstraint()
            y = CenterConstraint()

            width = 150.pixels()
            height = 50.pixels()
        }
        onMouseClick { event->
            isDragging = true

            dragOffset = event.absoluteX to event.absoluteY
        }.onMouseRelease {
            isDragging = false
        }.onMouseDrag { mouseX, mouseY, _ ->
            return@onMouseDrag
            if (!isDragging) return@onMouseDrag

            val absoluteX = mouseX + getLeft()
            val absoluteY = mouseY + getTop()

            val deltaX = absoluteX - dragOffset.first
            val deltaY = absoluteY - dragOffset.second

            dragOffset = absoluteX to absoluteY

            val newX = this@SaveForm.getLeft() + deltaX
            val newY = this@SaveForm.getTop() + deltaY

            this@SaveForm.setX(newX.pixels())
            this@SaveForm.setY(newY.pixels())
        }

        makeTextEntry("Filename", ::filename, 2f, 2f, this, StrLimit(50))

        Button(Color.GRAY.brighter().brighter(), "Save") {
            parent.removeChild(this)
            mode.filename = filename
            ClientPlayerSchematics.saveSchemStream.r2tRequestData.transmitData(FakePacketContext(), ClientPlayerSchematics.SendSchemRequest(Minecraft.getInstance().player!!))
        }.constrain {
            x = 2.pixels()
            y = SiblingConstraint() + 2.pixels()
        } childOf this

        Button(Color.GRAY.brighter().brighter(), "Cancel") {
            parent.removeChild(this)
        }.constrain {
            x = 2.pixels()
            y = SiblingConstraint() + 2.pixels()
        } childOf this
    }
}

interface SchemGUIBuilder: GUIBuilder {
    override val itemName get() = Component.literal("Schem")

    var itemsScroll: ScrollComponent?
    var parentWindow: UIBlock

    fun makeScroll() {
        this as SchemMode
        itemsScroll = ScrollComponent().constrain {
            x = 1f.percent()
            y = SiblingConstraint() + 2.pixels()

            width = 98.percent()
            height = 90.percent()
        } childOf parentWindow

        makeScrollItems()
    }

    fun makeScrollItems() {
        this as SchemMode
        val paths = ClientPlayerSchematics.listSchematics().sortedWith {a, b ->
            a.toString().lowercase(Locale.getDefault())
            .compareTo(b.toString().lowercase(Locale.getDefault()))}

        for (path in paths) {
            val block = UIBlock().constrain {
                x = 0f.pixels()
                y = SiblingConstraint()

                width = 100.percent()
                height = ChildBasedMaxSizeConstraint() + 2.pixels()
            } childOf itemsScroll!!

            Button(Color.GRAY.brighter(), "Load") {
                schem = ClientPlayerSchematics.loadSchematic(path)
                if (schem != null) {
                    SchemNetworking.c2sLoadSchematic.sendToServer(SchemNetworking.C2SLoadSchematic())
                }
            }.constrain {
                x = 0.pixels()
                y = 0.pixels()

                width = ChildBasedSizeConstraint() + 4.pixels()
                height = ChildBasedSizeConstraint() + 4.pixels()
            } childOf block

            val name = Files.getNameWithoutExtension(path.fileName.toString())

            UIText(name, false).constrain {
                x = SiblingConstraint() + 2.pixels()
                y = 2.pixels()

                textScale = 1.pixels()
                color = Color.BLACK.toConstraint()
            } childOf block
        }
    }

    fun reloadScrollItems() {
        itemsScroll!!.clearChildren()
        makeScrollItems()
    }

    override fun makeGUISettings(parentWindow: UIBlock) {
        this as SchemMode
        this.parentWindow = parentWindow

        Button(Color.GRAY.brighter(), "Save") {
            SaveForm(this) childOf parentWindow
        }.constrain {
            x = 2.pixels()
            y = 2.pixels()
        } childOf parentWindow

        makeScroll()
    }
}