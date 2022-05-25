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
        val tracker = ShipmentTracker()
        tracker.addShipment(Shipment(listOf("created", "1", "12345")))
        tracker.addShipment(Shipment(listOf("created", "2", "12346")))
        tracker.addShipment(Shipment(listOf("created", "3", "12347")))
        val s1 = tracker.findShipment("1")
        val s2 = tracker.findShipment("2")
        val s3 = tracker.findShipment("3")
        assertEquals(s1?.id, "1")
        assertEquals(s2?.id, "2")
        assertEquals(s3?.id, "3")
    }

    @Test
    fun processCreateUpdate() {
        val tracker = ShipmentTracker()
        var shipment = tracker.findShipment("1")
        assertEquals(shipment, null)
        tracker.processUpdate(listOf("created", "1", "12345"))
        shipment = tracker.findShipment("1")
        assertEquals(shipment?.id, "1")
    }

    @Test
    fun processShippedUpdate() {
        val tracker = ShipmentTracker()
        tracker.addShipment(Shipment(listOf("created", "1", "12345")))
        val shipment = tracker.findShipment("1")
        assertEquals(shipment?.getStatusChangeHistory()?.size, 1)
        assertEquals(shipment?.status, "created")
        assertEquals(shipment?.expectedDeliveryTimestamp, null)
        tracker.processUpdate(listOf("shipped", "1", "12346", "123456"))
        assertEquals(shipment?.getStatusChangeHistory()?.size, 2)
        assertEquals(shipment?.status, "shipped")
        assertEquals(shipment?.expectedDeliveryTimestamp, 123456)
    }

    @Test
    fun processDelayedUpdate() {
        val tracker = ShipmentTracker()
        tracker.addShipment(Shipment(listOf("created", "1", "12345")))
        val shipment = tracker.findShipment("1")
        assertEquals(shipment?.getStatusChangeHistory()?.size, 1)
        tracker.processUpdate(listOf("delayed", "1", "12346", "123456"))
        assertEquals(shipment?.status, "created")
        assertEquals(shipment?.getStatusChangeHistory()?.size, 1)
        assertEquals(shipment?.expectedDeliveryTimestamp, 123456)
    }

    @Test
    fun processLocationUpdate() {
        val tracker = ShipmentTracker()
        tracker.addShipment(Shipment(listOf("created", "1", "12345")))
        val shipment = tracker.findShipment("1")
        assertEquals(shipment?.getStatusChangeHistory()?.size, 1)
        tracker.processUpdate(listOf("location", "1", "12346", "Some Location"))
        assertEquals(shipment?.status, "created")
        assertEquals(shipment?.getStatusChangeHistory()?.size, 1)
        assertEquals(shipment?.expectedDeliveryTimestamp, null)
        assertEquals(shipment?.currentLocation, "Some Location")
    }

    @Test
    fun processNoteAddedUpdate() {
        val tracker = ShipmentTracker()
        tracker.addShipment(Shipment(listOf("created", "1", "12345")))
        val shipment = tracker.findShipment("1")
        assertEquals(shipment?.getStatusChangeHistory()?.size, 1)
        assertEquals(shipment?.getNotes()?.size, 0)
        tracker.processUpdate(listOf("noteadded", "1", "12346", "This is a note"))
        assertEquals(shipment?.status, "created")
        assertEquals(shipment?.getStatusChangeHistory()?.size, 1)
        assertEquals(shipment?.expectedDeliveryTimestamp, null)
        assertEquals(shipment?.currentLocation, null)
        assertEquals(shipment?.getNotes()?.size, 1)
        assertEquals(shipment?.getNotes()?.get(0), "This is a note")
    }

    @Test
    fun processCanceledUpdate() {
        val tracker = ShipmentTracker()
        tracker.addShipment(Shipment(listOf("created", "1", "12345")))
        val shipment = tracker.findShipment("1")
        assertEquals(shipment?.getStatusChangeHistory()?.size, 1)
        assertEquals(shipment?.getNotes()?.size, 0)
        tracker.processUpdate(listOf("canceled", "1", "12346"))
        assertEquals(shipment?.status, "canceled")
        assertEquals(shipment?.getStatusChangeHistory()?.size, 2)
        assertEquals(shipment?.expectedDeliveryTimestamp, null)
        assertEquals(shipment?.currentLocation, null)
        assertEquals(shipment?.getNotes()?.size, 0)
    }

    @Test
    fun processDeliveredUpdate() {
        val tracker = ShipmentTracker()
        tracker.addShipment(Shipment(listOf("created", "1", "12345")))
        val shipment = tracker.findShipment("1")
        assertEquals(shipment?.getStatusChangeHistory()?.size, 1)
        tracker.processUpdate(listOf("delivered", "1", "12346"))
        assertEquals(shipment?.status, "delivered")
        assertEquals(shipment?.getStatusChangeHistory()?.size, 2)
        assertEquals(shipment?.expectedDeliveryTimestamp, null)
        assertEquals(shipment?.currentLocation, null)
    }

    @Test
    fun processLostUpdate() {
        val tracker = ShipmentTracker()
        tracker.addShipment(Shipment(listOf("created", "1", "12345")))
        val shipment = tracker.findShipment("1")
        assertEquals(shipment?.getStatusChangeHistory()?.size, 1)
        tracker.processUpdate(listOf("lost", "1", "12346"))
        assertEquals(shipment?.status, "lost")
        assertEquals(shipment?.getStatusChangeHistory()?.size, 2)
        assertEquals(shipment?.expectedDeliveryTimestamp, null)
        assertEquals(shipment?.currentLocation, null)
    }

    @Test
    fun processInvalidUpdate() {
        val tracker = ShipmentTracker()
        tracker.addShipment(Shipment(listOf("created", "1", "12345")))
        val shipment = tracker.findShipment("1")
        assertEquals(shipment?.getStatusChangeHistory()?.size, 1)
        tracker.processUpdate(listOf("something", "1", "12346"))
        assertEquals(shipment?.status, "created")
        assertEquals(shipment?.getStatusChangeHistory()?.size, 1)
    }



}