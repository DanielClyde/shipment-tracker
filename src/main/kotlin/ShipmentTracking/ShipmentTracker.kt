package ShipmentTracking

class ShipmentTracker {
//    private val shipments: MutableList<Shipment> = mutableListOf()
    private val updateReader = UpdateFileReader("src/test.txt")

//    fun findShipment(id: String): Shipment? {
//        return shipments.find { s -> s.id === id }
//    }

    fun runSimulation() {
        println(updateReader.getCreatedUpdates())
        println(updateReader.nextUpdate())
        // read text file
        // immediately run any "create" updates
    }

}