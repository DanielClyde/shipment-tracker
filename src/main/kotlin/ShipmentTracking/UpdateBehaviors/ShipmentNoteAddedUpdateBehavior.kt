package ShipmentTracking.UpdateBehaviors

import ShipmentTracking.Shipment

class ShipmentNoteAddedUpdateBehavior: ShipmentUpdateBehavior {
    override fun updateShipment(update: List<String>, shipment: Shipment) {
        shipment.notes.add(update[3])
    }
}