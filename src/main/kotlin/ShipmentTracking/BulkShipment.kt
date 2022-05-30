package ShipmentTracking

import java.time.*

class BulkShipment(createdUpdate: List<String>): Shipment(createdUpdate) {
    override val type: String = "bulk"
    override fun validate() {
        val expectedDelivery = expectedDeliveryTimestamp?.let { Instant.ofEpochSecond(it).atZone(ZoneId.systemDefault()).toLocalDate(); }
        val threeDaysFromCreated = createdDate.plusDays(3)
        error = if (expectedDelivery?.isBefore(threeDaysFromCreated) == true) {
            "Bulk Shipments should not have expected delivery date sooner than 3 days after it was created"
        } else {
            null
        }
    }
}