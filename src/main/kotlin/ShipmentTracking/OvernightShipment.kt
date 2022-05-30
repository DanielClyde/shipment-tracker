package ShipmentTracking

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit

class OvernightShipment(createdUpdate: List<String>): Shipment(createdUpdate) {
    override val type: String = "overnight"
    override fun validate() {
        val expectedDeliveryDay: LocalDate? =
            expectedDeliveryTimestamp?.let { Instant.ofEpochSecond(it).atZone(ZoneId.systemDefault()).truncatedTo(ChronoUnit.DAYS).toLocalDate(); }
        val oneDayAfterCreated: LocalDate = createdDate.atStartOfDay().plusDays(1).toLocalDate()

        error = if (expectedDeliveryDay?.isAfter(oneDayAfterCreated) == true) {
            "Overnight shipments must have an expected delivery of the day after it was created"
        } else {
            null
        }

    }
}
