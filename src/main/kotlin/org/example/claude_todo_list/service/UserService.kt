package org.example.claude_todo_list.service

import org.example.claude_todo_list.dto.LoginRequest
import org.example.claude_todo_list.dto.SignupRequest
import org.example.claude_todo_list.entity.User
import org.example.claude_todo_list.repository.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository
) {
    private val passwordEncoder = BCryptPasswordEncoder()

    @Transactional
    fun signup(request: SignupRequest): User {
        // 이메일 중복 체크
        if (userRepository.existsByEmail(request.email)) {
            throw IllegalArgumentException("이미 사용 중인 이메일입니다.")
        }

        // 비밀번호 암호화
        val encodedPassword = passwordEncoder.encode(request.password)

        // User 생성 및 저장
        val user = User(
            email = request.email,
            password = encodedPassword,
            nickname = request.nickname
        )

        return userRepository.save(user)
    }

    fun login(request: LoginRequest): User {
        val user = userRepository.findByEmail(request.email)
            ?: throw IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다.")

        // 비밀번호 검증
        if (!passwordEncoder.matches(request.password, user.password)) {
            throw IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다.")
        }

        return user
    }

    fun findById(userId: Long): User {
        return userRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("사용자를 찾을 수 없습니다.") }
    }
}
