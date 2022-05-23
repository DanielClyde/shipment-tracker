package ShipmentTracking

import Observer.Subject

class Shipment(createdUpdate: List<String>): Subject() {
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

    fun getNotes(): MutableList<String> {
        return notes
    }

    fun setStatus(s: String, timestamp: Long) {
        statusChangeHistory.add(StatusChangeHistoryRecord(status, s, timestamp))
        status = s
        notifyObservers()
    }

    fun addNote(note: String) {
        notes.add(note)
        notifyObservers()
    }

    fun setLocation(location: String?) {
        currentLocation = location
        notifyObservers()
    }

    fun setExpectedDeliveryTimestamp(timestamp: Long?) {
        expectedDeliveryTimestamp = timestamp
        notifyObservers()
    }

    override fun toString(): String {
        return "id: ${id}, status: ${status}, expectedDelivery: ${expectedDeliveryTimestamp}, location: ${currentLocation}, noteCount: ${notes.size}"
    }
}