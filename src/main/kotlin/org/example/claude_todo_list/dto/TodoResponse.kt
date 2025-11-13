package org.example.claude_todo_list.dto

import org.example.claude_todo_list.entity.Todo
import java.time.LocalDateTime

data class TodoResponse(
    val id: Long,
    val title: String,
    val description: String?,
    val isDone: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun from(todo: Todo): TodoResponse {
            return TodoResponse(
                id = todo.id!!,
                title = todo.title,
                description = todo.description,
                isDone = todo.isDone,
                createdAt = todo.createdAt,
                updatedAt = todo.updatedAt
            )
        }
    }
}
