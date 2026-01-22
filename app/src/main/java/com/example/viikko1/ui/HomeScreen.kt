package com.example.viikko1.ui

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
import com.example.viikko1.domain.Task

@Composable
fun HomeScreen(vm: TaskViewModel = viewModel()) {

    var title by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Viikko2", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(12.dp))

        // TextFieldit + Add
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

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
                val nextId = (vm.tasks.maxOfOrNull { it.id } ?: 0) + 1
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
            }) { Text("Add") }

            Button(onClick = { vm.sortByDueDate() }) { Text("Sort") }
        }

        Spacer(Modifier.height(8.dp))

        // Filter-napit
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { vm.showAll() }) { Text("All") }
            Button(onClick = { vm.filterByDone(false) }) { Text("Not done") }
            Button(onClick = { vm.filterByDone(true) }) { Text("Done") }
        }

        Spacer(Modifier.height(12.dp))

        // Lista
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(vm.tasks, key = { it.id }) { task ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Checkbox(
                            checked = task.done,
                            onCheckedChange = { vm.toggleDone(task.id) }
                        )
                        Column {
                            Text(task.title)
                            Text(task.description)
                        }
                    }

                    Button(onClick = { vm.removeTask(task.id) }) {
                        Text("Del")
                    }
                }
            }
        }
    }
}
