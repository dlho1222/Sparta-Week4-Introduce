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
import android.widget.Toast
import androidx.core.widget.addTextChangedListener

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
        createSpinner(spinner,emailForm)
        validation(id)
        focusOut(id)
        validation(email)
        focusOut(email)
        validation(password)
        focusOut(password)
        validation(confirmPassword)
        focusOut(confirmPassword)



        if (id.text.isNullOrEmpty() || confirmPassword.text.isNullOrEmpty() || password.text.isNullOrEmpty() || email.text.isNullOrEmpty()) {
            return
        } else {
            signUpButton.setOnClickListener {
                Intent().apply {
                    putExtra("updatedId", id.text.toString())
                    putExtra("updatedPassword", password.text.toString())
                    setResult(RESULT_OK, this)
                }
                finish()
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
        editText.addTextChangedListener {
            editText.error =
                if (editText.text.length == 0 || editText.text.isNullOrEmpty() || isEmpty(editText)
                ) {
                    when (editText.id) {
                        R.id.et_Id -> "아이디를 입력하세요"
                        R.id.et_Email -> "이메일을 입력하세요"
                        R.id.et_Password -> "비밀번호를 입력하세요"
                        R.id.et_ConfirmPassword -> "비밀번호를 확인해주세요"
                        else -> ""
                    }
                } else {
                    null
                }
        }
    }

    private fun isEmpty(editText: EditText): Boolean {
        val empty = editText.text.toString()
        empty.replace(" ", "")
        return if (empty.contains(" ")) true else false
    }

    private fun emailFormChange(spinner: Spinner) {
        val emailForm = findViewById<EditText>(R.id.et_EmailForm)
        emailForm.setText(spinner.selectedItem.toString())
    }
    private fun createSpinner(spinner: Spinner,emailForm: EditText){
        spinner.apply {
            adapter = ArrayAdapter.createFromResource(
                baseContext,
                R.array.email,
                android.R.layout.simple_list_item_1
            )
            onItemSelectedListener = object :OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if(spinner.selectedItemPosition == 3){
                        emailForm.apply {
                            setText("")
                            setHint("직접입력")
                        }
                    }
                    else emailForm.setText(spinner.selectedItem.toString())

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
        }
    }
}