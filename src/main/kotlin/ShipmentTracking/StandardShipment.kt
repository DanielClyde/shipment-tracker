package ShipmentTracking

class StandardShipment(createdUpdate: List<String>): Shipment(createdUpdate) {
    override val type: String = "standard"
    override fun validate() {
        error = null
    }
}