package ShipmentTracking.UpdateBehaviors

import ShipmentTracking.ShipmentTracker

interface ShipmentUpdateBehavior {
    fun updateShipment(update: List<String>, tracker: ShipmentTracker)
}