package ShipmentTracking

class Shipment(createdUpdate: List<String>) {
    val id: String
    private val notes = mutableListOf<String>()
    private val statusChangeHistory = mutableListOf<StatusChangeHistoryRecord>()

    var status: String = "created"
        private set;

    var expectedDeliveryTimestamp: Long? = null
        private set;
    var currentLocation: String? = null
        private set;

    init {
        id = createdUpdate[1]
        setStatus(createdUpdate[0], createdUpdate[2].toLong())
    }

    fun setStatus(s: String, timestamp: Long) {
        statusChangeHistory.add(StatusChangeHistoryRecord(status, s, timestamp))
        status = s
    }

    fun addNote(note: String) {
        notes.add(note)
        // TODO: NOTIFY
    }

    fun setLocation(location: String) {
        currentLocation = location
        // TODO: NOTIFY
    }

    fun setExpectedDeliveryTimestamp(timestamp: Long) {
        expectedDeliveryTimestamp = timestamp
        // TODO: NOTIFY
    }

    override fun toString(): String {
        return "id: ${id}, status: ${status}"
    }
}