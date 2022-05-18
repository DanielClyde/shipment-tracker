package ShipmentTracking

import java.io.File

class UpdateFileReader(path: String) {
    private var updates: List<List<String>>
    private var createdUpdates: List<List<String>>
    var updateCount: Int = 0
        get() {
            return updates.size
        }
    init {
        val lines = File(path).useLines { it.toList() }
        createdUpdates = lines.map { it.split(",") }.filter { it[0] == "created" }
        updates = lines.map { it.split(",") }.filter { it[0] != "created" }
    }

    fun getCreatedUpdates(): List<List<String>> {
        return createdUpdates
    }

    fun nextUpdate(): List<String> {
        val update = updates.first()
        updates = updates.subList(1, updates.size)
        return update
    }
}