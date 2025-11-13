package org.example.claude_todo_list.controller

import org.example.claude_todo_list.dto.TodoRequest
import org.example.claude_todo_list.dto.TodoResponse
import org.example.claude_todo_list.dto.TodoUpdateRequest
import org.example.claude_todo_list.service.TodoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/todos")
class TodoController(
    private val todoService: TodoService
) {

    @PostMapping
    fun createTodo(@RequestBody request: TodoRequest): ResponseEntity<TodoResponse> {
        val response = todoService.createTodo(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping
    fun getAllTodos(): ResponseEntity<List<TodoResponse>> {
        val response = todoService.getAllTodos()
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{id}")
    fun getTodoById(@PathVariable id: Long): ResponseEntity<TodoResponse> {
        val response = todoService.getTodoById(id)
        return ResponseEntity.ok(response)
    }

    @PutMapping("/{id}")
    fun updateTodo(
        @PathVariable id: Long,
        @RequestBody request: TodoUpdateRequest
    ): ResponseEntity<TodoResponse> {
        val response = todoService.updateTodo(id, request)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{id}")
    fun deleteTodo(@PathVariable id: Long): ResponseEntity<Void> {
        todoService.deleteTodo(id)
        return ResponseEntity.noContent().build()
    }
}
