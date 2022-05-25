package ShipmentTracking

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class UpdateFileReaderTest {
    @Test
    fun updateCount() {
        val reader = UpdateFileReader("src/test2.txt")
        assertEquals(reader.updateCount, 8)
        for (i in 0 until reader.updateCount) {
            val update = reader.nextUpdate()
            assertEquals(update[1], "s10000")
        }
    }
}