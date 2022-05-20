package ShipmentTracking

import ShipmentTracking.UpdateBehaviors.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

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
        return shipments.find { it.id == id }
    }

    fun addShipment(shipment: Shipment) {
        shipments.add(shipment)
    }

    fun runSimulation() {
        val tracker = this
        runBlocking {
            repeat(updateReader.updateCount) { processUpdates(tracker) }
            println("DONE")
        }
    }

    private suspend fun processUpdates(tracker: ShipmentTracker) = coroutineScope {
        launch {
            delay(1000)
            val update = updateReader.nextUpdate()
            updateBehaviors[update[0]]?.updateShipment(update, tracker)
            println(findShipment(update[1]))
        }
    }


}