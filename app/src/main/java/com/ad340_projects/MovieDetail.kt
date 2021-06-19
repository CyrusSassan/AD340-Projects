package com.ad340_projects

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class MovieDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        title = "Movie Details"
        var textView3 = findViewById<TextView>(R.id.textview3)
        val title = intent.getSerializableExtra("title")
        textView3.text = title.toString()

        var textView4 = findViewById<TextView>(R.id.textview4)
        val year = intent.getSerializableExtra("year")
        textView4.text = year.toString()
        var textView5 = findViewById<TextView>(R.id.textview5)
        val director = intent.getSerializableExtra("director")
        textView5.text = director.toString()
        var textView6 = findViewById<TextView>(R.id.textview6)
        val description = intent.getSerializableExtra("description")
        textView6.text = description.toString()

        //val view: View = ImageView(this)
        val imageView: ImageView = findViewById<ImageView>(R.id.image2)
        val image = intent.getStringExtra("img")
        val resID = resources.getIdentifier(image, "drawable", packageName)
        imageView.setImageResource(resID)
    }
}
