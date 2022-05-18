package ShipmentTracking.ShipmentStatusUpdates

import ShipmentTracking.Shipment

interface ShipmentUpdateBehavior {
    fun updateShipment(shipment: Shipment)
}