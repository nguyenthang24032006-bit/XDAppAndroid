package com.example.studentprofilecard.model

// ── File: model/Student.kt ─────────────────────────────────
// Data Class đại diện cho thông tin sinh viên.
// Nhờ 'data class', Kotlin tự sinh copy(), toString(), equals()/hashCode()
// giúp việc cập nhật dữ liệu (immutability) và debug trở nên rất đơn giản.
data class Student(
    val id: String,
    val name: String,
    val className: String,
    val email: String,
    val phone: String = "",   // Bài tập: thêm trường số điện thoại
    val gpa: Double = 0.0
)
