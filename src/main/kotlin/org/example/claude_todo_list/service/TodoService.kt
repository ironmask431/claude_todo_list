package org.example.claude_todo_list.service

import org.example.claude_todo_list.dto.TodoRequest
import org.example.claude_todo_list.dto.TodoResponse
import org.example.claude_todo_list.dto.TodoUpdateRequest
import org.example.claude_todo_list.entity.Todo
import org.example.claude_todo_list.entity.User
import org.example.claude_todo_list.repository.TodoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class TodoService(
    private val todoRepository: TodoRepository
) {

    @Transactional
    fun createTodo(request: TodoRequest, user: User): TodoResponse {
        val todo = Todo(
            title = request.title,
            description = request.description,
            isDone = request.isDone,
            user = user
        )
        val savedTodo = todoRepository.save(todo)
        return TodoResponse.from(savedTodo)
    }

    fun getAllTodosByUser(user: User): List<TodoResponse> {
        return todoRepository.findAllByUser(user)
            .map { TodoResponse.from(it) }
    }

    fun getTodoById(id: Long, user: User): TodoResponse {
        val todo = todoRepository.findByIdAndUser(id, user)
            ?: throw NoSuchElementException("Todo not found with id: $id")
        return TodoResponse.from(todo)
    }

    @Transactional
    fun updateTodo(id: Long, request: TodoUpdateRequest, user: User): TodoResponse {
        val todo = todoRepository.findByIdAndUser(id, user)
            ?: throw NoSuchElementException("Todo not found with id: $id")

        request.title?.let { todo.title = it }
        request.description?.let { todo.description = it }
        request.isDone?.let { todo.isDone = it }
        todo.updatedAt = LocalDateTime.now()

        return TodoResponse.from(todo)
    }

    @Transactional
    fun deleteTodo(id: Long, user: User) {
        val todo = todoRepository.findByIdAndUser(id, user)
            ?: throw NoSuchElementException("Todo not found with id: $id")
        todoRepository.delete(todo)
    }
}
