package org.example.claude_todo_list.dto

data class TodoUpdateRequest(
    val title: String? = null,
    val description: String? = null,
    val isDone: Boolean? = null
)
