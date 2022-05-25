package ShipmentTracking

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ShipmentViewHelperTest {
    @Test
    fun initialize() {
        val viewHelper = ShipmentViewHelper(Shipment(listOf("created", "1", "12345")))
        assertEquals(viewHelper.id, "1")
        assertEquals(viewHelper.notes.size, 0)
        assertEquals(viewHelper.currentLocation, null)
        assertEquals(viewHelper.status, "created")
        assertFalse(viewHelper.isTracking)
    }

    @Test
    fun trackNotes() {
        val shipment = Shipment(listOf("created", "1", "12345"))
        val viewHelper = ShipmentViewHelper(shipment)
        viewHelper.track()
        shipment.addNote("Test Note 1")
        assertEquals(viewHelper.notes.size, 1)
        assertEquals(viewHelper.notes[0], "Test Note 1")
        viewHelper.stopTracking()
        shipment.addNote("Should not track this")
        assertEquals(viewHelper.notes.size, 1)
    }

    @Test
    fun trackExpectedDeliveryTimestamp() {
        val shipment = Shipment(listOf("created", "1", "12345"))
        val viewHelper = ShipmentViewHelper(shipment)
        assertEquals(viewHelper.expectedDeliveryTimestamp, null)
        viewHelper.track()
        shipment.setExpectedDeliveryTimestamp(12345)
        assertEquals(viewHelper.expectedDeliveryTimestamp, 12345)
        viewHelper.stopTracking()
        shipment.setExpectedDeliveryTimestamp(23456)
        assertEquals(viewHelper.expectedDeliveryTimestamp, 12345)
    }

    @Test
    fun trackCurrentLocation() {
        val shipment = Shipment(listOf("created", "1", "12345"))
        val viewHelper = ShipmentViewHelper(shipment)
        assertEquals(viewHelper.currentLocation, null)
        viewHelper.track()
        shipment.setLocation("Somewhere over the rainbow")
        assertEquals(viewHelper.currentLocation, "Somewhere over the rainbow")
        viewHelper.stopTracking()
        shipment.setLocation("Cloud 9")
        assertEquals(viewHelper.currentLocation, "Somewhere over the rainbow")
    }

    @Test
    fun trackStatus() {
        val shipment = Shipment(listOf("created", "1", "12345"))
        val viewHelper = ShipmentViewHelper(shipment)
        assertEquals(viewHelper.status, "created")
        viewHelper.track()
        shipment.setStatus("shipped", 12345)
        assertEquals(viewHelper.status, "shipped")
        viewHelper.stopTracking()
        shipment.setStatus("lost", 123456)
        assertEquals(viewHelper.status, "shipped")
    }
}