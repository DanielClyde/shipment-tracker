package ShipmentTracking

import Observer.Observer
import androidx.compose.runtime.*
import java.util.*

class ShipmentViewHelper(private val shipment: Shipment): Observer {
    var id by mutableStateOf(shipment.id)
    var notes = mutableStateListOf<String>()
    var statusChangeHistory = mutableStateListOf<StatusChangeHistoryRecord>()
    var expectedDeliveryTimestamp by mutableStateOf(shipment.expectedDeliveryTimestamp)
    var currentLocation by mutableStateOf(shipment.currentLocation)
    var status by mutableStateOf(shipment.status)
    var isTracking by mutableStateOf(false)
    val type by mutableStateOf(shipment.type.uppercase(Locale.getDefault()))
    var errorMsg by mutableStateOf(shipment.error)

    fun track() {
        shipment.addObserver(this)
        isTracking = true
        update()
    }

    fun stopTracking() {
        shipment.removeObserver(this)
        isTracking = false
    }

    override fun update() {
        id = shipment.id
        notes.clear()
        shipment.getNotes().forEach { notes.add(it) }
        statusChangeHistory.clear()
        shipment.getStatusChangeHistory().forEach { statusChangeHistory.add(it) }
        expectedDeliveryTimestamp = shipment.expectedDeliveryTimestamp
        currentLocation = shipment.currentLocation
        status = shipment.status
        errorMsg = shipment.error
    }

    override fun toString(): String {
        return shipment.toString()
    }

}