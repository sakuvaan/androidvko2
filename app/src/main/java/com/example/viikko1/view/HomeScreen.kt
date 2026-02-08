package com.example.viikko1.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.viikko1.model.Task
import com.example.viikko1.viewmodel.TaskViewModel
import androidx.compose.runtime.collectAsState


@Composable
fun HomeScreen(
    vm: TaskViewModel,
    goCalendar: () -> Unit
) {
    val tasks by vm.tasks.collectAsState()
    var selected by remember { mutableStateOf<Task?>(null) }
    var showAdd by remember { mutableStateOf(false) }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Home", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = goCalendar) { Text("Calendar") }
            Button(onClick = { showAdd = true }) { Text("Add") }
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
                        Text(task.dueDate)
                    }
                    Checkbox(
                        checked = task.done,
                        onCheckedChange = { vm.toggleDone(task.id) }
                    )
                }
            }
        }
    }

    if (showAdd) {
        val nextId = (tasks.maxOfOrNull { it.id } ?: 0) + 1
        AddTaskDialog(
            nextId = nextId,
            onAdd = { vm.addTask(it) },
            onClose = { showAdd = false }
        )
    }

    selected?.let { task ->
        DetailScreen(
            task = task,
            onSave = { updated ->
                vm.updateTask(updated)
            },
            onDelete = { id ->
                vm.removeTask(id)
            },
            onClose = {
                selected = null
            }
        )
    }

}
