package com.ad340_projects

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonMovies: Button = findViewById(R.id.buttonMovies)
        buttonMovies.setOnClickListener {
            val intent = android.content.Intent(this, MoviesList::class.java)
            startActivity(intent)
        }
        val buttonInfo: Button = findViewById(R.id.buttonInfo)
        buttonInfo.setOnClickListener {
            val intent = android.content.Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }
}