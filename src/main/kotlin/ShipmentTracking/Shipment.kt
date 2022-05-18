package ShipmentTracking

class Shipment(createdUpdate: List<String>) {
    val id: String
    val notes = mutableListOf<String>()
    val statusChangeHistory = mutableListOf<StatusChangeHistoryRecord>()

    var status: String = "created"
        private set;

    var expectedDeliveryTimestamp: Long? = null
    var currentLocation: String? = null

    init {
        id = createdUpdate[1]
        setStatus(createdUpdate[0], createdUpdate[2].toLong())
    }

    fun setStatus(s: String, timestamp: Long) {
        statusChangeHistory.add(StatusChangeHistoryRecord(status, s, timestamp))
        status = s
    }
    override fun toString(): String {
        return "id: ${id}, status: ${status}"
    }
}