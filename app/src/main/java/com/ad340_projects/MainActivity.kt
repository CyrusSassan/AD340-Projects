package com.ad340_projects

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button: Button = findViewById(R.id.buttonMovies)
        button.setOnClickListener {
            val intent = android.content.Intent(this, MoviesList::class.java)
            startActivity(intent)
        }
    }
}