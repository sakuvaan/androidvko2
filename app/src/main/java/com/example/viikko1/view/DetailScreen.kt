package com.example.viikko1.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import com.example.viikko1.model.Task

@Composable
fun DetailScreen(
    task: Task,
    onSave: (Task) -> Unit,
    onDelete: (Int) -> Unit,
    onClose: () -> Unit
) {
    var title by remember { mutableStateOf(task.title) }
    var desc by remember { mutableStateOf(task.description) }

    AlertDialog(
        onDismissRequest = onClose,
        title = { Text("Edit task") },
        text = {
            Column {
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") })
                OutlinedTextField(value = desc, onValueChange = { desc = it }, label = { Text("Description") })
            }
        },
        confirmButton = {
            Button(onClick = {
                onSave(task.copy(title = title, description = desc))
                onClose()
            }) { Text("Save") }
        },
        dismissButton = {
            Button(onClick = { onDelete(task.id); onClose() }) { Text("Delete") }
        }
    )
}
