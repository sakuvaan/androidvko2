package com.example.viikko1.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.viikko1.model.Task
import com.example.viikko1.viewmodel.TaskViewModel

@Composable
fun HomeScreen(vm: TaskViewModel = viewModel()) {

    val tasks by vm.tasks.collectAsState()
    var selected by remember { mutableStateOf<Task?>(null) }

    var title by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("week3", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(12.dp))

        // Add UI
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = desc,
            onValueChange = { desc = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))

        Button(onClick = {
            val nextId = (tasks.maxOfOrNull { it.id } ?: 0) + 1
            vm.addTask(
                Task(
                    id = nextId,
                    title = if (title.isBlank()) "Uusi task" else title,
                    description = if (desc.isBlank()) "-" else desc,
                    priority = 1,
                    dueDate = "2026-02-01",
                    done = false
                )
            )
            title = ""
            desc = ""
        }) {
            Text("Add")
        }

        Spacer(Modifier.height(12.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(tasks, key = { it.id }) { task ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selected = task }
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(task.title)
                        Text(task.description)
                    }
                    Checkbox(
                        checked = task.done,
                        onCheckedChange = { vm.toggleDone(task.id) }
                    )
                }
            }
        }
    }

    selected?.let { task ->
        DetailScreen(
            task = task,
            onSave = { vm.updateTask(it) },
            onDelete = { vm.removeTask(it) },
            onClose = { selected = null }
        )
    }
}
