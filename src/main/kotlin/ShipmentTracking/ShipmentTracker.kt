package ShipmentTracking

import ShipmentTracking.UpdateBehaviors.*
import java.util.*

class ShipmentTracker {
    private val shipments = mutableListOf<Shipment>()
    private val updateBehaviors = mapOf<String, ShipmentUpdateBehavior>(
        Pair("created", ShipmentCreatedUpdateBehavior()),
        Pair("shipped", ShipmentShippedUpdateBehavior()),
        Pair("location", ShipmentLocationUpdateBehavior()),
        Pair("delivered", ShipmentDeliveredUpdateBehavior()),
        Pair("delayed", ShipmentDelayedUpdateBehavior()),
        Pair("lost", ShipmentLostUpdateBehavior()),
        Pair("canceled", ShipmentCanceledUpdateBehavior()),
        Pair("noteadded", ShipmentNoteAddedUpdateBehavior())
    )

    fun findShipment(id: String): Shipment? {
        return shipments.find { it.id.lowercase(Locale.getDefault()) == id.lowercase(Locale.getDefault()) }
    }

    fun addShipment(shipment: Shipment) {
        println("ADDING SHIPMENT $shipment")
        shipments.add(shipment)
    }

    fun processUpdate(update: List<String>) {
        println("Processing...${update}")
        updateBehaviors[update[0]]?.updateShipment(update, this);
    }
}