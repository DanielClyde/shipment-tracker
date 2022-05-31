package ShipmentTracking

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit

class ExpressShipment(createdUpdate: List<String>): Shipment(createdUpdate) {
    override val type: String = "express"
    override fun validate() {
        println("VALIDATING EXPRESS")
        val expectedDeliveryDay: LocalDate? =
            expectedDeliveryTimestamp?.let { Instant.ofEpochSecond(it).atZone(ZoneId.systemDefault()).truncatedTo(
                ChronoUnit.DAYS).toLocalDate(); }
        val threeDaysAfterCreated: LocalDate = createdDate.atStartOfDay().plusDays(4).toLocalDate()

        error = if (expectedDeliveryDay?.isAfter(threeDaysAfterCreated) == true) {
            "Express shipments cannot have an expected delivery date of more than 3 days after the shipment was created"
        } else {
            println("no error express")
            null
        }
    }
}