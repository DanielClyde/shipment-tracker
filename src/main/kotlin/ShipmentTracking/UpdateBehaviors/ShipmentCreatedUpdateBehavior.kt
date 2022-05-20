package ShipmentTracking.UpdateBehaviors

import ShipmentTracking.Shipment
import ShipmentTracking.ShipmentTracker

class ShipmentCreatedUpdateBehavior: ShipmentUpdateBehavior {
    override fun updateShipment(update: List<String>, tracker: ShipmentTracker) {
        tracker.addShipment(Shipment(update))
    }
}