package com.example.viikko1.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.viikko1.model.Task

@Composable
fun AddTaskDialog(
    nextId: Int,
    onAdd: (Task) -> Unit,
    onClose: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var dueDate by remember { mutableStateOf("2026-02-01") }

    AlertDialog(
        onDismissRequest = onClose,
        title = { Text("Add task") },
        text = {
            Column {
                OutlinedTextField(title, { title = it }, label = { Text("Title") })
                OutlinedTextField(desc, { desc = it }, label = { Text("Description") })
                OutlinedTextField(dueDate, { dueDate = it }, label = { Text("Due date") })
            }
        },
        confirmButton = {
            Button(onClick = {
                onAdd(
                    Task(
                        id = nextId,
                        title = if (title.isBlank()) "Uusi task" else title,
                        description = if (desc.isBlank()) "-" else desc,
                        priority = 1,
                        dueDate = dueDate,
                        done = false
                    )
                )
                onClose()
            }) { Text("Save") }
        },
        dismissButton = {
            Button(onClick = onClose) { Text("Cancel") }
        }
    )
}
