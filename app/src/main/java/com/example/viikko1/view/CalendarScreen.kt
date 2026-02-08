package com.example.viikko1.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.viikko1.model.Task
import com.example.viikko1.viewmodel.TaskViewModel

@Composable
fun CalendarScreen(
    vm: TaskViewModel,
    goHome: () -> Unit
) {
    val tasks = vm.tasks.collectAsState().value
    var selected by remember { mutableStateOf<Task?>(null) }

    val grouped = tasks.groupBy { it.dueDate }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Calendar", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(12.dp))

        Button(onClick = goHome) { Text("Back") }
        Spacer(Modifier.height(12.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            grouped.forEach { (date, list) ->
                item {
                    Text(date, style = MaterialTheme.typography.titleMedium)
                }

                items(
                    items = list,
                    key = { task -> task.id }
                ) { task ->
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
    }

    selected?.let { task ->
        DetailScreen(
            task = task,
            onSave = { vm.updateTask(it) },
            onDelete = { vm.removeTask(task.id) },
            onClose = { selected = null }
        )
    }

}
