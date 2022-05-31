package ShipmentTracking

import java.time.*

class BulkShipment(createdUpdate: List<String>): Shipment(createdUpdate) {
    override val type: String = "bulk"
    override fun validate() {
        val expectedDelivery = expectedDeliveryTimestamp?.let { Instant.ofEpochSecond(it).atZone(ZoneId.systemDefault()).toLocalDate(); }
        println("expectedDelivery $expectedDelivery")
        val threeDaysFromCreated = createdDate.plusDays(4).atStartOfDay().toLocalDate()
        println("threeDaysFromCreated $threeDaysFromCreated")
        error = if (expectedDelivery != null && threeDaysFromCreated.isAfter(expectedDelivery)) {
            "Bulk Shipments should not have expected delivery date sooner than 3 days after it was created"
        } else {
            null
        }
    }
}