package ShipmentTracking.UpdateBehaviors

import ShipmentTracking.ShipmentFactory
import ShipmentTracking.ShipmentTracker

class ShipmentCreatedUpdateBehavior: ShipmentUpdateBehavior {
    override fun updateShipment(update: List<String>, tracker: ShipmentTracker) {
        tracker.addShipment(ShipmentFactory.GetShipment(update))
    }
}