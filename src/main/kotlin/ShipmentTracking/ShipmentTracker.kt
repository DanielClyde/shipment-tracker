package ShipmentTracking

import ShipmentTracking.UpdateBehaviors.*
import kotlinx.coroutines.delay
import java.util.*

class ShipmentTracker {
    private val shipments = mutableListOf<Shipment>()
    private val updateReader = UpdateFileReader("src/test.txt")
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
        shipments.add(shipment)
    }

    suspend fun runSimulation() {
        for (i in 0 until updateReader.updateCount) {
            delay(1000)
            val update = updateReader.nextUpdate()
            processUpdate(update)
            println(findShipment(update[1]))
        }
    }

    fun processUpdate(update: List<String>) {
        updateBehaviors[update[0]]?.updateShipment(update, this);
    }
}