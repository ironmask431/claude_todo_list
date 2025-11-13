package org.example.claude_todo_list.dto

data class TodoRequest(
    val title: String,
    val description: String? = null,
    val isDone: Boolean = false
)
