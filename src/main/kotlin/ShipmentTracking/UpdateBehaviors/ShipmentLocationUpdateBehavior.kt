package ShipmentTracking.UpdateBehaviors

import ShipmentTracking.ShipmentTracker

class ShipmentLocationUpdateBehavior: ShipmentUpdateBehavior {
    override fun updateShipment(update: List<String>, tracker: ShipmentTracker) {
        val shipment = tracker.findShipment(update[1])
        shipment?.setLocation(update[3])
    }
}