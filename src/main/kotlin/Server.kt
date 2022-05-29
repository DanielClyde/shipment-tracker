import ShipmentTracking.ShipmentTracker
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.lang.Error

object Server {
    private var isRunning = false
    suspend fun startServer(tracker: ShipmentTracker) {
        if (isRunning) {
            throw Error("Web Server already running!")
        }
        isRunning = true
        embeddedServer(Netty, 8080) {
            install(CORS) {
                anyHost()
                allowHeader("Content-Type")
                allowHeader("Accept")
            }
            routing {
                post("/shipment-update") {
                    val update = call.receive<String>().trim('\"')
                    val contents = update.split(Regex(","))
                    tracker.processUpdate(contents)
                    call.respond(HttpStatusCode.OK)
                }
            }
        }.start(wait = false)
    }
}