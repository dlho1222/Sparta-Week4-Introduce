package com.example.introduce

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
import android.widget.ListAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
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
            if (id.text.isNullOrEmpty() || confirmPassword.text.isNullOrEmpty() || password.text.isNullOrEmpty() || email.text.isNullOrEmpty()) {
                Toast.makeText(this, "입력값이 비어있습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                if(isEqualPassword(confirmPassword)){
                    Intent().apply {
                        putExtra("updatedId", id.text.toString())
                        putExtra("updatedPassword", password.text.toString())
                        setResult(RESULT_OK, this)
                    }
                    finish()
                }

            }
        }


    }

    private fun focusOut(editText: EditText) {
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

    private fun validation(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            val tv_passwordDescription = findViewById<TextView>(R.id.tv_DescriptionPassword)
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                    if (editText.text.length == 0 || editText.text.isNullOrEmpty() || isEmpty(editText)
                    ) {
                        when (editText.id) {
                            R.id.et_Id ->editText.error = "아이디를 입력하세요"
                            R.id.et_Email -> editText.error ="이메일을 입력하세요"
                            R.id.et_Password ->{
                                editText.error = "비밀번호를 입력하세요"
                                tv_passwordDescription.isVisible = true
                            }
                            R.id.et_ConfirmPassword -> editText.error ="비밀번호를 확인해주세요."
                            else -> ""
                        }
                    }else{
                        if(editText.id == R.id.et_Password){
                            tv_passwordDescription.isVisible = false
                        }
                        if(editText.id == R.id.et_ConfirmPassword){
                            isEqualPassword(editText)
                        }
                    }

            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    private fun isEmpty(editText: EditText): Boolean {
        val empty = editText.text.toString()
        empty.replace(" ", "")
        return if (empty.contains(" ")) true else false
    }

    private fun createSpinner(spinner: Spinner, emailForm: EditText) {
        spinner.apply {
            adapter = ArrayAdapter.createFromResource(
                baseContext,
                R.array.email,
                android.R.layout.simple_list_item_1
            )
            onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (spinner.selectedItemPosition == 3) {
                        emailForm.apply {
                            setText("")
                            setHint("직접입력")
                        }
                    } else emailForm.setText(spinner.selectedItem.toString())

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
        }
    }

    private fun isRegularPwd(password: EditText): Boolean {
        return if (password.text.toString().contains("!@#$%^&*()-_=+")) true else false
    }

    private fun isEqualPassword(editText: EditText):Boolean{
        val password = findViewById<EditText>(R.id.et_Password)
        val confirmPassword = findViewById<EditText>(R.id.et_ConfirmPassword)
        return if(password.text.toString() == confirmPassword.text.toString()){
            true
        } else {
            editText.error = "비밀번호가 같지 않습니다."
            false
        }
    }
}

