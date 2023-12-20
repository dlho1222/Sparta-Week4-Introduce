package com.example.introduce

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.edit
import androidx.core.view.isVisible
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        val signUpButton = findViewById<Button>(R.id.btn_SignUp)
        val id = findViewById<EditText>(R.id.et_Id)
        val password = findViewById<EditText>(R.id.et_Password)
        val confirmPassword = findViewById<EditText>(R.id.et_ConfirmPassword)
        val email = findViewById<EditText>(R.id.et_Email)
        val spinner = findViewById<Spinner>(R.id.spinner)
        val emailForm = findViewById<EditText>(R.id.et_EmailForm)
        createSpinner(spinner, emailForm)
        validation(id)
        validation(email)
        validation(password)
        validation(confirmPassword)
        focusOut(id)
        focusOut(email)
        focusOut(password)
        focusOut(confirmPassword)

        signUpButton.setOnClickListener {
            if (id.text.isNullOrEmpty() || confirmPassword.text.isNullOrEmpty() || password.text.isNullOrEmpty() || email.text.isNullOrEmpty() || emailForm.text.isNullOrEmpty()) {
                Toast.makeText(this, "입력값이 비어있습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {//모든 필드가 입력된 상태
                if (isEqualPassword() && check(password)) { //유효성 검사 후 승인
                    Intent().apply {
                        putExtra("updatedId", id.text.toString())
                        putExtra("updatedPassword", password.text.toString())
                        setResult(RESULT_OK, this)
                        clearData()

                    }
                    saveData()
                    finish()
                } else if (check(password)) { //password 와 confirmPassword가 같을 때 처리
                    Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                } else { //둘 다 아닐 때
                    Toast.makeText(this, "비밀번호는 10자리 이상, 특수문자, 숫자포함 입니다.", Toast.LENGTH_SHORT)
                        .show()
                }

            }
        }


    }
    private fun clearData(){
        val id = findViewById<EditText>(R.id.et_Id)
        val password = findViewById<EditText>(R.id.et_Password)
        val confirmPassword = findViewById<EditText>(R.id.et_ConfirmPassword)
        val email = findViewById<EditText>(R.id.et_Email)
        val emailForm = findViewById<EditText>(R.id.et_EmailForm)
        getSharedPreferences(INFO,Context.MODE_PRIVATE).edit {
            clear()
            id.setText("")
            password.setText("")
            confirmPassword.setText("")
            email.setText("")
            emailForm.setText("")
        }
    }
    private fun saveData() {   //입력 값 저장
        val id = findViewById<EditText>(R.id.et_Id)
        val password = findViewById<EditText>(R.id.et_Password)
        val confirmPassword = findViewById<EditText>(R.id.et_ConfirmPassword)
        val email = findViewById<EditText>(R.id.et_Email)
        val emailForm = findViewById<EditText>(R.id.et_EmailForm)
        getSharedPreferences(INFO, Context.MODE_PRIVATE)?.edit {
            putString(ID, id.text.toString())
            putString(EMAIL, email.text.toString())
            putString(EMAIL_FORM, emailForm.text.toString())
            putString(PASSWORD, password.text.toString())
            putString(CONFIRM_PASSWORD, confirmPassword.text.toString())
            apply()
        }
    }

    private fun getSaveData() {  //저장한 값 불러오기
        val id = findViewById<EditText>(R.id.et_Id)
        val password = findViewById<EditText>(R.id.et_Password)
        val confirmPassword = findViewById<EditText>(R.id.et_ConfirmPassword)
        val email = findViewById<EditText>(R.id.et_Email)
        val emailForm = findViewById<EditText>(R.id.et_EmailForm)
        getSharedPreferences(INFO, Context.MODE_PRIVATE).run {
            id.setText(getString(ID, ""))
            email.setText(getString(EMAIL, ""))
            password.setText(getString(PASSWORD, ""))
            confirmPassword.setText(getString(CONFIRM_PASSWORD, ""))
            emailForm.setText(getString(EMAIL_FORM, ""))
        }

    }

    override fun onResume() {  //onResume 시 값 불러옴
        getSaveData()
        super.onResume()
    }

    override fun onDestroy() {  //onDestroy 시 값 저장
        saveData()
        super.onDestroy()
    }

    private fun focusOut(editText: EditText) {//EditText가 비어 있을때 포커스 아웃처리
        editText.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
            } else {
                editText.error = if (editText.text.length == 0 || editText.text.isNullOrEmpty()
                ) {
                    when (editText.id) {
                        R.id.et_Id -> "아이디를 입력하세요"
                        R.id.et_Email -> "이메일을 입력하세요"
                        R.id.et_Password -> "비밀번호를 입력하세요"
                        R.id.et_ConfirmPassword -> "비밀번호를 확인해주세요"
                        else -> ""
                    }
                } else null
            }
        }
    }

    private fun validation(editText: EditText) {  //각 필드 유효성 체크
        editText.addTextChangedListener(object : TextWatcher {
            val tv_passwordDescription = findViewById<TextView>(R.id.tv_DescriptionPassword)
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            //텍스트 변화 있을 때 마다 실행
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (editText.text.length == 0 || editText.text.isNullOrEmpty() || isEmpty(editText)
                ) {
                    when (editText.id) {
                        R.id.et_Id -> editText.error = "아이디를 입력하세요"
                        R.id.et_Email -> editText.error = "이메일을 입력하세요"
                        R.id.et_Password -> {
                            editText.error = "비밀번호를 입력하세요"
                            tv_passwordDescription.isVisible = true
                        }

                        R.id.et_ConfirmPassword -> editText.error = "비밀번호를 확인해주세요."
                        else -> ""
                    }
                } else {
                    if (editText.id == R.id.et_Password) {
                        tv_passwordDescription.isVisible = false
                        if (check(editText) && editText.text.length >= 10) { //비밀번호가 유효하고 10자 이상인지

                        } else {
                            editText.error = "10자리 이상, 특수문자, 숫자포함"
                        }

                    }
                    if (editText.id == R.id.et_ConfirmPassword) {
                        if (isEqualPassword()) {                  //password와confirmPassword가 같은지 체크

                        } else {
                            editText.error = "비밀번호가 같지 않음"
                        }
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    private fun isEmpty(editText: EditText): Boolean {          //공백 문자 처리
        val empty = editText.text.toString()
        empty.replace(" ", "")
        return empty.contains(" ")
    }

    private fun createSpinner(spinner: Spinner, emailForm: EditText) {   //스피너 생성 및 리스너
        spinner.apply {
            adapter = ArrayAdapter.createFromResource(//스피너 생성
                baseContext,
                R.array.email,
                android.R.layout.simple_list_item_1
            )
            onItemSelectedListener = object : OnItemSelectedListener { //리스너
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (spinner.selectedItemPosition == 3) {    //드롭메뉴 직접입력 선택시 직접입력하게 비움
                        emailForm.apply {
                            setText("")
                            setHint("직접입력")
                        }
                    } else emailForm.setText(spinner.selectedItem.toString()) // 드롭메뉴 아이템선택시 EditText 입력
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
        }
    }

    private fun check(editText: EditText): Boolean { //특수문자 숫자 정규식
        val pwd = """^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^+\-=])(?=\S+$).*$"""
        val pattern = Pattern.compile(pwd)
        return pattern.matcher(editText.text).matches()
    }


    private fun isEqualPassword(): Boolean {   //password 와 confirmPassword가 같은지 체크
        val password = findViewById<EditText>(R.id.et_Password)
        val confirmPassword = findViewById<EditText>(R.id.et_ConfirmPassword)
        return password.text.toString() == confirmPassword.text.toString()
    }
}

