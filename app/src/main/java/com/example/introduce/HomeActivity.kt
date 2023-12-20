package com.example.introduce

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import org.w3c.dom.Text
import kotlin.random.Random

class HomeActivity : AppCompatActivity() {
    val images = listOf(
        R.drawable.image_1,
        R.drawable.image_2,
        R.drawable.image_3,
        R.drawable.image_4,
        R.drawable.image_5
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val profile = findViewById<ImageView>(R.id.iv_Profile)
        val finishButton = findViewById<Button>(R.id.btn_finish)
        val id = intent.getStringExtra("id")
        val tv_Id = findViewById<TextView>(R.id.tv_Id)
        profile.setImageResource(images[Random.nextInt(4)])
        finishButton.setOnClickListener {
            finish()
        }

        tv_Id.text = "아이디: $id"

    }
}