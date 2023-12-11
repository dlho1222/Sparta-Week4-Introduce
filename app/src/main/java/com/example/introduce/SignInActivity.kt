package com.example.introduce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        val signInId = findViewById<EditText>(R.id.et_Id)
        val password = findViewById<EditText>(R.id.et_Password)
        val signInButton = findViewById<Button>(R.id.btn_Login)
        val navigateToSignUp = findViewById<Button>(R.id.btn_NavigateToSignUp)

        signInButton.setOnClickListener {
            if (signInId.text.isNullOrEmpty() || password.text.isNullOrEmpty()){
                Toast.makeText(this, "아이디/비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(this,HomeActivity::class.java).apply {
                putExtra("id",signInId.text.toString())
            }
            startActivity(intent)
        }
        navigateToSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }


    }
}