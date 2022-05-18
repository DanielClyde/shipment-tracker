package ShipmentTracking

import ShipmentTracking.ShipmentStatusUpdates.ShipmentStatus
import ShipmentTracking.ShipmentStatusUpdates.ShipmentStatusUpdate

class Shipment(id: String) {

    val notes: MutableList<String> = mutableListOf();
    val updateHistory: MutableList<ShipmentStatusUpdate> = mutableListOf();

    val id = id

    var status: ShipmentStatus = ShipmentStatus.CREATED
        private set;
    var expectedDeliveryDateTimestamp: Long = 0
        private set;

    fun setStatus(s: ShipmentStatus) {
        status = s;
    }

    fun setExpectedDeliveryDateTimestamp(timestamp: Long) {
        expectedDeliveryDateTimestamp = timestamp
    }
}