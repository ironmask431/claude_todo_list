package org.example.claude_todo_list.dto

data class SignupRequest(
    val email: String,
    val password: String,
    val nickname: String
)
