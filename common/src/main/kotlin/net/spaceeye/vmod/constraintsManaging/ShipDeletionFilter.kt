package net.spaceeye.vmod.constraintsManaging

import dev.architectury.event.events.common.TickEvent
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import org.valkyrienskies.core.api.ships.ServerShip
import org.valkyrienskies.core.api.ships.properties.ShipId
import org.valkyrienskies.core.impl.game.ships.ShipObjectServer
import org.valkyrienskies.mod.common.shipObjectWorld
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentSkipListSet

object ShipDeletionFilter {
    private val shipsToNotDelete = ConcurrentHashMap<ShipId, (ship: ServerShip) -> Unit>()
    private val shipsToRemove = ConcurrentHashMap<ShipId, Int>()

    fun filterShipsToDelete(ship: ServerShip, ci: CallbackInfo) {
        if (!shipsToNotDelete.keys.contains(ship.id)) {return}
        ci.cancel()
        shipsToNotDelete[ship.id]!!((ship as ShipObjectServer).shipData)
    }

    fun addShipIdToFilter(shipId: ShipId, callback: (ship: ServerShip) -> Unit) {
        shipsToNotDelete[shipId] = callback
    }

    fun removeShipIdFromFilter(shipId: ShipId) {
        shipsToNotDelete.remove(shipId)
    }

    fun removeShipLater(shipId: ShipId) {
        shipsToNotDelete.remove(shipId)
        shipsToRemove[shipId] = 1
    }

    init {
        TickEvent.SERVER_PRE.register { server ->
            shipsToRemove.filter { it.value <= 0 }.forEach { (id, num) ->
                server.shipObjectWorld.deleteShip(server.shipObjectWorld.allShips.getById(id) ?: return@forEach)
            }
            shipsToRemove.filter { it.value <= 0 }.forEach { shipsToRemove.remove(it.key) }
            shipsToRemove.forEach { (key, value) ->
                shipsToRemove[key] = value - 1
            }
        }
    }
}