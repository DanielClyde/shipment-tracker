package ShipmentTracking

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class ShipmentTrackerTest {

    @Test
    fun findShipmentNull() {
        val tracker = ShipmentTracker()
        val shipment = tracker.findShipment("test_id")
        assertEquals(shipment, null)
    }

    @Test
    fun findShipment() {
        val tracker = ShipmentTracker()
        tracker.addShipment(Shipment(listOf("created", "test_id", "12345")))
        val shipment = tracker.findShipment("test_id")
        assertEquals(shipment?.id, "test_id")
        assertEquals(shipment?.status, "created")
        assertEquals(shipment?.expectedDeliveryTimestamp, null)
        assertEquals(shipment?.getNotes()?.size, 0)
        val changeHistory = shipment?.getStatusChangeHistory()
        assertEquals(changeHistory?.size, 1)
        val record = changeHistory?.get(0)
        assertEquals(record?.newStatus, "created")
        assertEquals(record?.timestamp, 12345)
    }

    @Test
    fun addShipment() {
        assertEquals(1, 1)
    }

    @Test
    fun processUpdate() {
        assertEquals(1, 1)
    }
}