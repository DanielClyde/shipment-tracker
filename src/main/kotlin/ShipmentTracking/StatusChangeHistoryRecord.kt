package ShipmentTracking

class StatusChangeHistoryRecord(val previousStatus: String, val newStatus: String, val timestamp: Long) {
    override fun toString(): String {
        return "$previousStatus -> $newStatus - $timestamp"
    }
}