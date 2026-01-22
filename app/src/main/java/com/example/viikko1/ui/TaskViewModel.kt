package com.example.viikko1.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.viikko1.domain.Task
import com.example.viikko1.domain.addTask
import com.example.viikko1.domain.mockTasks
import com.example.viikko1.domain.sortByDueDate
import com.example.viikko1.domain.toggleDone

class TaskViewModel : ViewModel() {

    // UI kuuntelee tätä, Compose päivittyy kun arvo vaihtuu
    var tasks by mutableStateOf<List<Task>>(mockTasks)
        private set

    // null = kaikki, true = vain done, false = vain not done
    private var filterDone: Boolean? = null

    fun addTask(task: Task) {
        tasks = addTask(tasks, task)
    }

    fun toggleDone(id: Int) {
        tasks = toggleDone(tasks, id)
    }

    fun removeTask(id: Int) {
        tasks = tasks.filterNot { it.id == id }
    }

    fun filterByDone(done: Boolean) {
        filterDone = done
        applyFilter()
    }

    fun showAll() {
        filterDone = null
        applyFilter()
    }

    fun sortByDueDate() {
        tasks = sortByDueDate(tasks)
    }

    private fun applyFilter() {
        tasks = when (filterDone) {
            null -> mockTasks
            true -> mockTasks.filter { it.done }
            false -> mockTasks.filter { !it.done }
        }
    }
}
