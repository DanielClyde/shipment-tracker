package ShipmentTracking.UpdateBehaviors

import ShipmentTracking.ShipmentTracker

class ShipmentDelayedUpdateBehavior: ShipmentUpdateBehavior {
    override fun updateShipment(update: List<String>, tracker: ShipmentTracker) {
        val shipment = tracker.findShipment(update[1])
        shipment?.setExpectedDeliveryTimestamp(update[3].toLong())
    }
}