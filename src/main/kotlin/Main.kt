import ShipmentTracking.ShipmentTracker
import ShipmentTracking.ShipmentViewHelper
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Composable
fun Message(message: String) {
    Row {
        Surface {
            Row {
                Text(message)
            }
        }
    }
}

@Composable
fun ShipmentCard(viewHelper: ShipmentViewHelper) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Gray)
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column(
                modifier = Modifier
                    .padding(5.dp)
            ) {
                Text("ID: ${viewHelper.id}")
                Text("Status: ${viewHelper.status}")
                Text("Expected Delivery: ${viewHelper.expectedDeliveryTimestamp ?: "NA"}")
                Text("Current Location: ${viewHelper.currentLocation ?: "NA"} ")
                Text("Notes:  ")
                viewHelper.notes.forEach {Text(" - $it", modifier = Modifier.padding(start = 3.dp))}
            }
            Column {
                Button(onClick = {
                    if (viewHelper.isTracking) {
                        viewHelper.stopTracking()
                    } else {
                        viewHelper.track()
                    }
                }) { Text(if (viewHelper.isTracking) "Stop Tracking" else "Track") }
            }
        }

    }
}


@Composable
fun App() {
    val tracker = ShipmentTracker()
    var inputId by remember { mutableStateOf("") }
    var errorMsg by remember { mutableStateOf("") }
    val viewHelpers = remember { mutableStateListOf<ShipmentViewHelper>() }
    val coroutineScope = rememberCoroutineScope()
    coroutineScope.launch {
        tracker.runSimulation()
    }
    MaterialTheme {
        Column {
            Row(
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = inputId,
                    onValueChange = { inputId = it },
                    label = { Text("Shipment ID")},
                )
                Button(
                    onClick = {
                        errorMsg = ""
                        val shipment = tracker.findShipment(inputId)
                        if (shipment != null) {
                            val newViewHelper = ShipmentViewHelper(shipment)
                            newViewHelper.track()
                            viewHelpers.add(newViewHelper)
                        } else {
                            errorMsg = "Shipment for $inputId not found!"
                        }
                        inputId = ""
                    },
                ) { Text("Track") }
            }
            if (errorMsg != "") {
                Row(Modifier.fillMaxWidth()) {
                    Column {
                        Text(errorMsg, color = Color.Red)
                    }
                }
            }
            Row(Modifier.fillMaxWidth()) {
                LazyColumn {
                    items(viewHelpers) {
                        ShipmentCard(it)
                    }
                }
            }
        }
    }

}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
