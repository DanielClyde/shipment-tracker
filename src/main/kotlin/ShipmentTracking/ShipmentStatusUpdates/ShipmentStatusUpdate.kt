package ShipmentTracking.ShipmentStatusUpdates

abstract class ShipmentStatusUpdate(val previousStatus: ShipmentStatus, val currentStatus: ShipmentStatus, val timestamp: Long)