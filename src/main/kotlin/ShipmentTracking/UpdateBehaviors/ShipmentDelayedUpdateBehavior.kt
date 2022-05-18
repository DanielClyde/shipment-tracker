package ShipmentTracking.UpdateBehaviors

import ShipmentTracking.Shipment

class ShipmentDelayedUpdateBehavior: ShipmentUpdateBehavior {
    override fun updateShipment(update: List<String>, shipment: Shipment) {
        shipment.expectedDeliveryTimestamp = update[3].toLong()
    }
}