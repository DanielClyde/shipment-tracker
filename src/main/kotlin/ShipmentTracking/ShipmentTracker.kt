package ShipmentTracking

import ShipmentTracking.ShipmentStatusUpdates.ShipmentCreatedUpdateBehavior
import ShipmentTracking.ShipmentStatusUpdates.ShipmentUpdateBehavior

class ShipmentTracker {
    private val updateBehaviors: Map<String, ShipmentUpdateBehavior> = mapOf(
        Pair("created", ShipmentCreatedUpdateBehavior())
    )
    private val shipments: MutableList<Shipment> = mutableListOf()

    fun findShipment(id: String): Shipment? {
        return shipments.find { s -> s.id === id }
    }

    fun runSimulation() {
        // read text file
        // immediately run any "create" updates
    }

}