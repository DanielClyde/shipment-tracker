package ShipmentTracking.UpdateBehaviors

import ShipmentTracking.Shipment

class ShipmentDeliveredUpdateBehavior: ShipmentUpdateBehavior {
    override fun updateShipment(update: List<String>, shipment: Shipment) {
        shipment.setStatus(update[0], update[2].toLong())
    }
}