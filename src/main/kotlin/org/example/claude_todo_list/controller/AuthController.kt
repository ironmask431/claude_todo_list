package org.example.claude_todo_list.controller

import jakarta.servlet.http.HttpSession
import org.example.claude_todo_list.dto.LoginRequest
import org.example.claude_todo_list.dto.SignupRequest
import org.example.claude_todo_list.service.UserService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
class AuthController(
    private val userService: UserService
) {

    @GetMapping("/signup")
    fun signupPage(): String {
        return "signup"
    }

    @PostMapping("/signup")
    fun signup(
        @ModelAttribute request: SignupRequest,
        redirectAttributes: RedirectAttributes
    ): String {
        return try {
            userService.signup(request)
            redirectAttributes.addFlashAttribute("message", "회원가입이 완료되었습니다. 로그인해주세요.")
            "redirect:/login"
        } catch (e: IllegalArgumentException) {
            redirectAttributes.addFlashAttribute("error", e.message)
            "redirect:/signup"
        }
    }

    @GetMapping("/login")
    fun loginPage(model: Model, session: HttpSession): String {
        // 이미 로그인된 경우 투두 목록으로 리다이렉트
        if (session.getAttribute("userId") != null) {
            return "redirect:/todos"
        }
        return "login"
    }

    @PostMapping("/login")
    fun login(
        @ModelAttribute request: LoginRequest,
        session: HttpSession,
        redirectAttributes: RedirectAttributes
    ): String {
        return try {
            val user = userService.login(request)
            // 세션에 사용자 정보 저장
            session.setAttribute("userId", user.id)
            session.setAttribute("userNickname", user.nickname)
            "redirect:/todos"
        } catch (e: IllegalArgumentException) {
            redirectAttributes.addFlashAttribute("error", e.message)
            "redirect:/login"
        }
    }

    @GetMapping("/logout")
    fun logout(session: HttpSession): String {
        session.invalidate()
        return "redirect:/login"
    }
}
