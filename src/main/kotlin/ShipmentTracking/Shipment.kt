package ShipmentTracking

import Observer.Subject
import java.time.LocalDate

abstract class Shipment(createdUpdate: List<String>): Subject() {

    var error: String? = null;
    abstract val type: String

    val id: String
    val createdDate: LocalDate
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
        createdDate = LocalDate.now()
        setStatus(createdUpdate[0], createdUpdate[2].toLong())
    }

    abstract fun validate()

    fun getNotes(): MutableList<String> {
        return notes
    }

    fun getStatusChangeHistory(): MutableList<StatusChangeHistoryRecord> {
        return statusChangeHistory
    }

    fun setStatus(s: String, timestamp: Long) {
        statusChangeHistory.add(StatusChangeHistoryRecord(status, s, timestamp))
        status = s
        onUpdated()
    }

    fun addNote(note: String) {
        notes.add(note)
        onUpdated()
    }

    fun setLocation(location: String?) {
        currentLocation = location
        onUpdated()
    }

    fun setExpectedDeliveryTimestamp(timestamp: Long?) {
        expectedDeliveryTimestamp = timestamp
        onUpdated()
    }

    fun onUpdated() {
        validate()
        notifyObservers()
    }

    override fun toString(): String {
        return "id: ${id}, status: ${status}, expectedDelivery: ${expectedDeliveryTimestamp}, location: ${currentLocation}, noteCount: ${notes.size}"
    }
}