package ShipmentTracking.ShipmentStatusUpdates

import ShipmentTracking.Shipment

class ShipmentCreatedUpdateBehavior: ShipmentUpdateBehavior {
    override fun updateShipment(shipment: Shipment) {
        shipment.setStatus(ShipmentStatus.CREATED);
    }
}