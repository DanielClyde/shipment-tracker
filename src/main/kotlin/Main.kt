// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.material.MaterialTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

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
fun App() {
    MaterialTheme {
        var message by remember { mutableStateOf("") }
        val messages = remember { mutableStateListOf<String>() }

        Column {
            Row {
                Button({
                    messages.add(message)
                }) { Text("Add Message")}
                TextField(message,{ message = it })
            }
            Row {
                LazyColumn {
                    items(messages, key = { it }) {
                        Message(it)
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
