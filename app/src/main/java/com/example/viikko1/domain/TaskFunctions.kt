package com.example.viikko1.domain

import com.example.viikko1.model.Task

fun addTask(list: List<Task>, task: Task): List<Task> {
    return list + task
}

fun toggleDone(list: List<Task>, id: Int): List<Task> {
    return list.map { t ->
        if (t.id == id) t.copy(done = !t.done) else t
    }
}

fun filterByDone(list: List<Task>, done: Boolean): List<Task> {
    return list.filter { it.done == done }
}

fun sortByDueDate(list: List<Task>): List<Task> {
    return list.sortedBy { it.dueDate }
}
