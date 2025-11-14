package org.example.claude_todo_list.controller

import jakarta.servlet.http.HttpSession
import org.example.claude_todo_list.dto.TodoRequest
import org.example.claude_todo_list.dto.TodoUpdateRequest
import org.example.claude_todo_list.service.TodoService
import org.example.claude_todo_list.service.UserService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/todos")
class TodoViewController(
    private val todoService: TodoService,
    private val userService: UserService
) {

    @GetMapping
    fun list(model: Model, session: HttpSession): String {
        val userId = session.getAttribute("userId") as? Long
            ?: return "redirect:/login"
        
        val user = userService.findById(userId)
        val todos = todoService.getAllTodosByUser(user)
        
        model.addAttribute("todos", todos)
        model.addAttribute("nickname", user.nickname)
        return "list"
    }

    @GetMapping("/create")
    fun createForm(session: HttpSession): String {
        val userId = session.getAttribute("userId") as? Long
            ?: return "redirect:/login"
        return "create"
    }

    @PostMapping("/create")
    fun create(
        @RequestParam title: String,
        @RequestParam(required = false) description: String?,
        session: HttpSession
    ): String {
        val userId = session.getAttribute("userId") as? Long
            ?: return "redirect:/login"
        
        val user = userService.findById(userId)
        val request = TodoRequest(
            title = title,
            description = description,
            isDone = false
        )
        todoService.createTodo(request, user)
        return "redirect:/todos"
    }

    @GetMapping("/{id}/edit")
    fun editForm(@PathVariable id: Long, model: Model, session: HttpSession): String {
        val userId = session.getAttribute("userId") as? Long
            ?: return "redirect:/login"
        
        val user = userService.findById(userId)
        val todo = todoService.getTodoById(id, user)
        model.addAttribute("todo", todo)
        return "edit"
    }

    @PostMapping("/{id}/edit")
    fun edit(
        @PathVariable id: Long,
        @RequestParam title: String,
        @RequestParam(required = false) description: String?,
        session: HttpSession
    ): String {
        val userId = session.getAttribute("userId") as? Long
            ?: return "redirect:/login"
        
        val user = userService.findById(userId)
        val updateRequest = TodoUpdateRequest(
            title = title,
            description = description
        )
        todoService.updateTodo(id, updateRequest, user)
        return "redirect:/todos"
    }

    @PostMapping("/{id}/toggle")
    fun toggleDone(@PathVariable id: Long, session: HttpSession): String {
        val userId = session.getAttribute("userId") as? Long
            ?: return "redirect:/login"
        
        val user = userService.findById(userId)
        val todo = todoService.getTodoById(id, user)
        val updateRequest = TodoUpdateRequest(
            isDone = !todo.isDone
        )
        todoService.updateTodo(id, updateRequest, user)
        return "redirect:/todos"
    }

    @PostMapping("/{id}/delete")
    fun delete(@PathVariable id: Long, session: HttpSession): String {
        val userId = session.getAttribute("userId") as? Long
            ?: return "redirect:/login"
        
        val user = userService.findById(userId)
        todoService.deleteTodo(id, user)
        return "redirect:/todos"
    }
}
