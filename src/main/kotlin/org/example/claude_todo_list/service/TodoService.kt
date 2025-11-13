package org.example.claude_todo_list.service

import org.example.claude_todo_list.dto.TodoRequest
import org.example.claude_todo_list.dto.TodoResponse
import org.example.claude_todo_list.dto.TodoUpdateRequest
import org.example.claude_todo_list.entity.Todo
import org.example.claude_todo_list.repository.TodoRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class TodoService(
    private val todoRepository: TodoRepository
) {

    @Transactional
    fun createTodo(request: TodoRequest): TodoResponse {
        val todo = Todo(
            title = request.title,
            description = request.description,
            isDone = request.isDone
        )
        val savedTodo = todoRepository.save(todo)
        return TodoResponse.from(savedTodo)
    }

    fun getAllTodos(): List<TodoResponse> {
        return todoRepository.findAll()
            .map { TodoResponse.from(it) }
    }

    fun getTodoById(id: Long): TodoResponse {
        val todo = todoRepository.findByIdOrNull(id)
            ?: throw NoSuchElementException("Todo not found with id: $id")
        return TodoResponse.from(todo)
    }

    @Transactional
    fun updateTodo(id: Long, request: TodoUpdateRequest): TodoResponse {
        val todo = todoRepository.findByIdOrNull(id)
            ?: throw NoSuchElementException("Todo not found with id: $id")

        request.title?.let { todo.title = it }
        request.description?.let { todo.description = it }
        request.isDone?.let { todo.isDone = it }
        todo.updatedAt = LocalDateTime.now()

        return TodoResponse.from(todo)
    }

    @Transactional
    fun deleteTodo(id: Long) {
        if (!todoRepository.existsById(id)) {
            throw NoSuchElementException("Todo not found with id: $id")
        }
        todoRepository.deleteById(id)
    }
}
