package ShipmentTracking.UpdateBehaviors

import ShipmentTracking.ShipmentTracker

class ShipmentLostUpdateBehavior: ShipmentUpdateBehavior {
    override fun updateShipment(update: List<String>, tracker: ShipmentTracker) {
        val shipment = tracker.findShipment(update[1])
        shipment?.setStatus(update[0], update[2].toLong())
    }
}