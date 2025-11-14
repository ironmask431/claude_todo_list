package org.example.claude_todo_list.controller

import jakarta.servlet.http.HttpSession
import org.example.claude_todo_list.dto.TodoRequest
import org.example.claude_todo_list.dto.TodoResponse
import org.example.claude_todo_list.dto.TodoUpdateRequest
import org.example.claude_todo_list.service.TodoService
import org.example.claude_todo_list.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/todos")
class TodoController(
    private val todoService: TodoService,
    private val userService: UserService
) {

    @PostMapping
    fun createTodo(
        @RequestBody request: TodoRequest,
        session: HttpSession
    ): ResponseEntity<TodoResponse> {
        val userId = session.getAttribute("userId") as? Long
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        
        val user = userService.findById(userId)
        val response = todoService.createTodo(request, user)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping
    fun getAllTodos(session: HttpSession): ResponseEntity<List<TodoResponse>> {
        val userId = session.getAttribute("userId") as? Long
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        
        val user = userService.findById(userId)
        val response = todoService.getAllTodosByUser(user)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{id}")
    fun getTodoById(
        @PathVariable id: Long,
        session: HttpSession
    ): ResponseEntity<TodoResponse> {
        val userId = session.getAttribute("userId") as? Long
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        
        val user = userService.findById(userId)
        val response = todoService.getTodoById(id, user)
        return ResponseEntity.ok(response)
    }

    @PutMapping("/{id}")
    fun updateTodo(
        @PathVariable id: Long,
        @RequestBody request: TodoUpdateRequest,
        session: HttpSession
    ): ResponseEntity<TodoResponse> {
        val userId = session.getAttribute("userId") as? Long
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        
        val user = userService.findById(userId)
        val response = todoService.updateTodo(id, request, user)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{id}")
    fun deleteTodo(
        @PathVariable id: Long,
        session: HttpSession
    ): ResponseEntity<Void> {
        val userId = session.getAttribute("userId") as? Long
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        
        val user = userService.findById(userId)
        todoService.deleteTodo(id, user)
        return ResponseEntity.noContent().build()
    }
}
