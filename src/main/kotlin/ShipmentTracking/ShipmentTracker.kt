package ShipmentTracking

import ShipmentTracking.UpdateBehaviors.*
import androidx.compose.ui.text.toLowerCase
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
        val tracker = this
        for (i in 0 until updateReader.updateCount) {
            delay(1000)
            val update = updateReader.nextUpdate()
            updateBehaviors[update[0]]?.updateShipment(update, tracker)
            println(findShipment(update[1]))
        }
    }
}