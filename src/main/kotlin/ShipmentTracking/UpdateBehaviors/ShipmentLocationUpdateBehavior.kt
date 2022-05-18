package ShipmentTracking.UpdateBehaviors

import ShipmentTracking.Shipment

class ShipmentLocationUpdateBehavior: ShipmentUpdateBehavior {
    override fun updateShipment(update: List<String>, shipment: Shipment) {
        shipment.currentLocation = update[3]
    }
}