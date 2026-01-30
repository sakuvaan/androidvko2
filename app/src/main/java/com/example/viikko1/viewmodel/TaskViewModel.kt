package com.example.viikko1.viewmodel

import androidx.lifecycle.ViewModel
import com.example.viikko1.model.Task
import com.example.viikko1.model.mockTasks
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TaskViewModel : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(mockTasks)
    val tasks: StateFlow<List<Task>> = _tasks

    fun addTask(task: Task) {
        _tasks.value = _tasks.value + task
    }

    fun toggleDone(id: Int) {
        _tasks.value = _tasks.value.map { t ->
            if (t.id == id) t.copy(done = !t.done) else t
        }
    }

    fun removeTask(id: Int) {
        _tasks.value = _tasks.value.filterNot { it.id == id }
    }

    fun updateTask(updated: Task) {
        _tasks.value = _tasks.value.map { t ->
            if (t.id == updated.id) updated else t
        }
    }
}
