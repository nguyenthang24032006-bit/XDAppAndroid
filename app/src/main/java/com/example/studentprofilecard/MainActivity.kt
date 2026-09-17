package com.example.studentprofilecard

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.studentprofilecard.databinding.ActivityMainBinding
import com.example.studentprofilecard.model.Student
import com.example.studentprofilecard.utils.showConfirmDialog
import com.example.studentprofilecard.utils.toAcademicRanking
import com.example.studentprofilecard.utils.toast
import com.example.studentprofilecard.utils.trimmedText

class MainActivity : AppCompatActivity() {

    // ── ViewBinding: cầu nối Type-Safe giữa XML và Kotlin ────
    // lateinit vì binding chỉ được khởi tạo bên trong onCreate()
    private lateinit var binding: ActivityMainBinding

    // ── Dữ liệu sinh viên hiện tại (Data Class + Immutability) ──
    private var currentStudent = Student(
        id = "22120005",
        name = "Nguyen Van An",
        className = "DD2026",
        email = "anv@ute.udn.vn",
        phone = "0905123456",
        gpa = 3.8
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // BƯỚC 2 & 3: Nạp layout XML thông qua inflate rồi gán root làm giao diện chính
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Khôi phục GPA đã lưu trước đó nếu Activity bị hủy do xoay màn hình
        savedInstanceState?.getDouble(KEY_SAVED_GPA)?.let { savedGpa ->
            if (savedGpa > 0.0) currentStudent = currentStudent.copy(gpa = savedGpa)
        }

        // Gán dữ liệu ban đầu lên các Views
        bindStudentData(currentStudent)

        setupUpdateGpaButton()
        setupCallButton()
        setupDeleteButton()
    }

    // Lưu trạng thái GPA trước khi Activity bị hủy (xoay màn hình / thiếu RAM)
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putDouble(KEY_SAVED_GPA, currentStudent.gpa)
    }

    // ── Hàm gán dữ liệu lên Views, dùng with(binding) để gom nhóm ──
    private fun bindStudentData(student: Student) {
        with(binding) {
            tvName.text = student.name
            tvStudentId.text = "MSSV: ${student.id} • Lớp: ${student.className}"
            tvEmail.text = student.email
            tvPhone.text = if (student.phone.isNotBlank()) {
                "SĐT: ${student.phone}"
            } else {
                "SĐT: (chưa cập nhật)"
            }
            tvGpaBadge.text = "${student.gpa} GPA (${student.gpa.toAcademicRanking()})"
            edtNewGpa.setText(student.gpa.toString())
        }
    }

    // ── Sự kiện: Cập nhật GPA & Validate dữ liệu nhập ────────
    private fun setupUpdateGpaButton() {
        binding.btnUpdateGpa.setOnClickListener {
            val newGpa = binding.edtNewGpa.trimmedText().toDoubleOrNull()

            if (newGpa == null || newGpa !in 0.0..4.0) {
                binding.edtNewGpa.error = "Vui lòng nhập GPA hợp lệ (0.0 - 4.0)"
                toast(getString(R.string.msg_gpa_invalid))
                return@setOnClickListener
            }

            // Cập nhật sinh viên bất biến bằng copy(), rồi vẽ lại UI
            currentStudent = currentStudent.copy(gpa = newGpa)
            bindStudentData(currentStudent)
            toast(getString(R.string.msg_gpa_updated))
        }
    }

    // ── Bài tập 1: nút Gọi điện, mở ứng dụng gọi điện qua Intent.ACTION_DIAL ──
    // Dùng ACTION_DIAL (chỉ mở màn hình quay số) thay vì ACTION_CALL
    // để không cần xin quyền android.permission.CALL_PHONE.
    private fun setupCallButton() {
        binding.btnCall.setOnClickListener {
            val phone = currentStudent.phone
            if (phone.isBlank()) {
                toast(getString(R.string.msg_no_phone))
                return@setOnClickListener
            }

            val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phone")
            }
            startActivity(dialIntent)
        }
    }

    // ── Bài tập 2: nút Xóa hồ sơ, hiển thị AlertDialog xác nhận ──
    private fun setupDeleteButton() {
        binding.btnDeleteProfile.setOnClickListener {
            showConfirmDialog(
                title = getString(R.string.dialog_delete_title),
                message = getString(R.string.dialog_delete_message)
            ) {
                // Khối lambda này chỉ chạy khi người dùng bấm "Đồng ý"
                deleteCurrentStudentProfile()
            }
        }
    }

    private fun deleteCurrentStudentProfile() {
        // Xóa hồ sơ: reset dữ liệu về rỗng và cập nhật lại giao diện
        currentStudent = currentStudent.copy(
            name = "(Đã xóa)",
            className = "",
            email = "",
            phone = "",
            gpa = 0.0
        )
        bindStudentData(currentStudent)
        toast(getString(R.string.msg_deleted))
    }

    companion object {
        private const val KEY_SAVED_GPA = "KEY_SAVED_GPA"
    }
}
