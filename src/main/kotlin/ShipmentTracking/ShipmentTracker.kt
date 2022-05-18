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
        Pair("shipped", ShipmentShippedUpdateBehavior()),
        Pair("location", ShipmentLocationUpdateBehavior()),
        Pair("delivered", ShipmentDeliveredUpdateBehavior()),
        Pair("delayed", ShipmentDelayedUpdateBehavior()),
        Pair("lost", ShipmentLostUpdateBehavior()),
        Pair("canceled", ShipmentCanceledUpdateBehavior()),
        Pair("noteadded", ShipmentNoteAddedUpdateBehavior())
    )

//    fun findShipment(id: String): Shipment? {
//        return shipments.find { s -> s.id === id }
//    }

    fun runSimulation() {
        for (createdUpdate in updateReader.getCreatedUpdates()) {
            shipments.add(Shipment(createdUpdate))
        }
        runBlocking {
            repeat(updateReader.updateCount) { processUpdates() }
            println("DONE")
        }
    }

    private suspend fun processUpdates() = coroutineScope {
        launch {
            delay(1000)
            val update = updateReader.nextUpdate()
            val shipment = shipments.find { it.id == update[1] }
            if (shipment != null) {
                updateBehaviors[update[0]]?.updateShipment(update, shipment)
            }
            println(shipment)
        }
    }


}