package ShipmentTracking.UpdateBehaviors

import ShipmentTracking.Shipment

interface ShipmentUpdateBehavior {
    fun updateShipment(update: List<String>, shipment: Shipment)
}