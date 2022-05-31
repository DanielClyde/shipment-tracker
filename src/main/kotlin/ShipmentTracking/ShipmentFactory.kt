package ShipmentTracking

import androidx.compose.ui.text.toLowerCase
import java.util.*

object ShipmentFactory {
    fun GetShipment(createdUpdate: List<String>): Shipment {
        val shipmentType = if (createdUpdate.size > 3) createdUpdate[3] else "standard"
        println("IN Factory $shipmentType")
        return if (shipmentType.lowercase(Locale.getDefault()) == "standard") {
            StandardShipment(createdUpdate)
        } else if (shipmentType.lowercase(Locale.getDefault()) == "express") {
            ExpressShipment(createdUpdate)
        } else if (shipmentType.lowercase(Locale.getDefault()) == "overnight") {
            OvernightShipment(createdUpdate)
        } else if (shipmentType.lowercase(Locale.getDefault()) == "bulk") {
            BulkShipment(createdUpdate)
        } else {
            println("HERE")
            throw Error("Invalid Shipment Type: $shipmentType")
        }
    }
}