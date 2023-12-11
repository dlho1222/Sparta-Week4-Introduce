package com.example.introduce

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import org.w3c.dom.Text

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val id = intent.getStringExtra("id")
        val tv_Id = findViewById<TextView>(R.id.tv_Id)
        val tv_name = findViewById<TextView>(R.id.tv_Name)
        val tv_age = findViewById<TextView>(R.id.tv_Age)
        val tv_MBTI = findViewById<TextView>(R.id.tv_MBTI)
        tv_Id.text = "아이디: $id"
        tv_name.text = "이름: 이호"
        tv_age.text = "나이: 29"
        tv_MBTI.text = "MBTI: ESFJ"

    }
}