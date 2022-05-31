package ShipmentTracking

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset

internal class ShipmentViewHelperTest {
    @Test
    fun initialize() {
        val viewHelper = ShipmentViewHelper(ShipmentFactory.GetShipment(listOf("created", "1", "12345")))
        assertEquals(viewHelper.id, "1")
        assertEquals(viewHelper.notes.size, 0)
        assertEquals(viewHelper.currentLocation, null)
        assertEquals(viewHelper.status, "created")
        assertFalse(viewHelper.isTracking)
    }

    @Test
    fun trackNotes() {
        val shipment = ShipmentFactory.GetShipment(listOf("created", "1", "12345"))
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
        val shipment = ShipmentFactory.GetShipment(listOf("created", "1", "12345"))
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
        val shipment = ShipmentFactory.GetShipment(listOf("created", "1", "12345"))
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
        val shipment = ShipmentFactory.GetShipment(listOf("created", "1", "12345"))
        val viewHelper = ShipmentViewHelper(shipment)
        assertEquals(viewHelper.status, "created")
        viewHelper.track()
        shipment.setStatus("shipped", 12345)
        assertEquals(viewHelper.status, "shipped")
        viewHelper.stopTracking()
        shipment.setStatus("lost", 123456)
        assertEquals(viewHelper.status, "shipped")
    }

    @Test
    fun overnightShipmentValidity() {
        val shipment = ShipmentFactory.GetShipment(listOf("created", "1", "12345", "overnight"))
        assertTrue(shipment is OvernightShipment)
        assertEquals(shipment.type, "overnight")
        assertNull(shipment.error)
        val twoDaysFromNow = LocalDateTime.now().plusDays(2).toEpochSecond(ZoneOffset.MIN)
        shipment.setStatus("shipped", LocalDateTime.now().toEpochSecond(ZoneOffset.MIN))
        shipment.setExpectedDeliveryTimestamp(twoDaysFromNow)
        assertEquals(shipment.error, "Overnight shipments must have an expected delivery of the day after it was created")
        val oneDayFromNow = LocalDateTime.now().plusDays(1).toEpochSecond(ZoneOffset.MIN)
        shipment.setExpectedDeliveryTimestamp(oneDayFromNow)
        assertNull(shipment.error)
    }

    @Test
    fun expressShipmentValidity() {
        val shipment = ShipmentFactory.GetShipment(listOf("created", "1", "12345", "express"))
        assertTrue(shipment is ExpressShipment)
        assertEquals(shipment.type, "express")
        assertNull(shipment.error)
        val fourDaysFromNow = LocalDateTime.now().plusDays(4).toEpochSecond(ZoneOffset.MIN)
        shipment.setStatus("shipped", LocalDateTime.now().toEpochSecond(ZoneOffset.MIN))
        shipment.setExpectedDeliveryTimestamp(fourDaysFromNow)
        assertEquals(shipment.error, "Express shipments cannot have an expected delivery date of more than 3 days after the shipment was created")
        val oneDayFromNow = LocalDateTime.now().plusDays(3).toEpochSecond(ZoneOffset.MIN)
        shipment.setExpectedDeliveryTimestamp(oneDayFromNow)
        assertNull(shipment.error)
    }

    @Test
    fun bulkShipmentValidity() {
        val shipment = ShipmentFactory.GetShipment(listOf("created", "1", "12345", "bulk"))
        assertTrue(shipment is BulkShipment)
        assertEquals(shipment.type, "bulk")
        assertNull(shipment.error)
        val twoDaysFromNow = LocalDateTime.now().plusDays(2).toEpochSecond(ZoneOffset.MIN)
        shipment.setStatus("shipped", LocalDateTime.now().toEpochSecond(ZoneOffset.MIN))
        shipment.setExpectedDeliveryTimestamp(twoDaysFromNow)
        assertEquals(shipment.error, "Bulk Shipments should not have expected delivery date sooner than 3 days after it was created")
        val oneDayFromNow = LocalDateTime.now().plusDays(3).toEpochSecond(ZoneOffset.MIN)
        shipment.setExpectedDeliveryTimestamp(oneDayFromNow)
        assertNull(shipment.error)
    }
}