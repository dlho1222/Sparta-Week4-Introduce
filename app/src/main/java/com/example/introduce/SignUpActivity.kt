package com.example.introduce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        val signUpButton = findViewById<Button>(R.id.btn_SignUp)
        val name = findViewById<EditText>(R.id.et_Name)
        val id = findViewById<EditText>(R.id.et_Id)
        val password = findViewById<EditText>(R.id.et_Password)
        signUpButton.setOnClickListener {
            if(name.text.isNullOrEmpty() || id.text.isNullOrEmpty() || password.text.isNullOrEmpty()){
                Toast.makeText(this, "입력되지 않은 정보가 있습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            Intent().apply {
                putExtra("updatedId",id.text.toString())
                putExtra("updatedPassword",password.text.toString())
                setResult(RESULT_OK,this)
            }
            finish()
        }
    }
}