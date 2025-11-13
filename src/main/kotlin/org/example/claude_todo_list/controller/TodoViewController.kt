package org.example.claude_todo_list.controller

import org.example.claude_todo_list.dto.TodoRequest
import org.example.claude_todo_list.dto.TodoUpdateRequest
import org.example.claude_todo_list.service.TodoService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/todos")
class TodoViewController(
    private val todoService: TodoService
) {

    @GetMapping
    fun list(model: Model): String {
        val todos = todoService.getAllTodos()
        model.addAttribute("todos", todos)
        return "list"
    }

    @GetMapping("/create")
    fun createForm(): String {
        return "create"
    }

    @PostMapping("/create")
    fun create(
        @RequestParam title: String,
        @RequestParam(required = false) description: String?
    ): String {
        val request = TodoRequest(
            title = title,
            description = description,
            isDone = false
        )
        todoService.createTodo(request)
        return "redirect:/todos"
    }

    @GetMapping("/{id}/edit")
    fun editForm(@PathVariable id: Long, model: Model): String {
        val todo = todoService.getTodoById(id)
        model.addAttribute("todo", todo)
        return "edit"
    }

    @PostMapping("/{id}/edit")
    fun edit(
        @PathVariable id: Long,
        @RequestParam title: String,
        @RequestParam(required = false) description: String?
    ): String {
        val updateRequest = TodoUpdateRequest(
            title = title,
            description = description
        )
        todoService.updateTodo(id, updateRequest)
        return "redirect:/todos"
    }

    @PostMapping("/{id}/toggle")
    fun toggleDone(@PathVariable id: Long): String {
        val todo = todoService.getTodoById(id)
        val updateRequest = TodoUpdateRequest(
            isDone = !todo.isDone
        )
        todoService.updateTodo(id, updateRequest)
        return "redirect:/todos"
    }

    @PostMapping("/{id}/delete")
    fun delete(@PathVariable id: Long): String {
        todoService.deleteTodo(id)
        return "redirect:/todos"
    }
}
