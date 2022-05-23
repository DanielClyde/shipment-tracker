package ShipmentTracking

import Observer.Observer
import androidx.compose.runtime.*

class ShipmentViewHelper(private val shipment: Shipment): Observer {
    var id by mutableStateOf(shipment.id)
    var notes = mutableStateListOf<String>()
    var expectedDeliveryTimestamp by mutableStateOf(shipment.expectedDeliveryTimestamp)
    var currentLocation by mutableStateOf(shipment.currentLocation)
    var status by mutableStateOf(shipment.status)

    fun track() {
        shipment.addObserver(this)
    }

    fun stopTracking() {
        shipment.removeObserver(this)
    }

    override fun update() {
        id = shipment.id
        notes.clear()
        shipment.getNotes().forEach { notes.add(it) }
        expectedDeliveryTimestamp = shipment.expectedDeliveryTimestamp
        currentLocation = shipment.currentLocation
        status = shipment.status
    }

    override fun toString(): String {
        return shipment.toString()
    }

}