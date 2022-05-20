package ShipmentTracking.UpdateBehaviors

import ShipmentTracking.ShipmentTracker

class ShipmentNoteAddedUpdateBehavior: ShipmentUpdateBehavior {
    override fun updateShipment(update: List<String>, tracker: ShipmentTracker) {
        val shipment = tracker.findShipment(update[1])
        shipment?.addNote(update[3])
    }
}