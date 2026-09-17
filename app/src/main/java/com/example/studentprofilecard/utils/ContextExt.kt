package com.example.studentprofilecard.utils

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

// ── File: utils/ContextExt.kt ───────────────────────────────

// Toast ngắn gọn cho Context/Activity, thay cho Toast.makeText(this, ...).show()
fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

// Higher-Order Function: hiển thị AlertDialog xác nhận, nhận callback onConfirm
// Dùng cho các thao tác nguy hiểm như "Xóa hồ sơ".
fun Context.showConfirmDialog(
    title: String,
    message: String,
    onConfirm: () -> Unit
) {
    AlertDialog.Builder(this).apply {
        setTitle(title)
        setMessage(message)
        setPositiveButton("Đồng ý") { _, _ -> onConfirm() }
        setNegativeButton("Hủy", null)
    }.show()
}
