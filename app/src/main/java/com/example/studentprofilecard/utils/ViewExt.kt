package com.example.studentprofilecard.utils

import android.view.View
import android.widget.EditText

// ── File: utils/ViewExt.kt ──────────────────────────────────

// Ẩn / Hiện View dễ đọc, thay cho view.visibility = View.XXX
fun View.show() { visibility = View.VISIBLE }
fun View.hide() { visibility = View.INVISIBLE }
fun View.gone() { visibility = View.GONE }

// Extension bật/tắt View theo điều kiện Boolean
fun View.visibleIf(condition: Boolean) {
    visibility = if (condition) View.VISIBLE else View.GONE
}

// Lấy text từ EditText nhanh chóng, đã trim khoảng trắng
fun EditText.trimmedText(): String = text.toString().trim()

// ── File: utils/StringExt.kt (gộp chung cho gọn) ────────────
// Extension xếp loại học lực theo điểm GPA
fun Double.toAcademicRanking(): String = when {
    this >= 3.6 -> "Xuất sắc!!"
    this >= 3.2 -> "Giỏi"
    this >= 2.5 -> "Khá"
    else -> "Trung bình"
}
