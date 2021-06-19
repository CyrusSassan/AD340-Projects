package com.ad340_projects

import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import org.json.JSONException


@RequiresApi(Build.VERSION_CODES.O)

class TrafficCameraActivity : AppCompatActivity() {
   private var requestQueue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_traffic_camera)
        //setContentView(R.layout.activity_movies_list)

        title = "Traffic"
        //textView = findViewById(R.id.textViewResult)
        val button = findViewById<Button>(R.id.btnParse)
         requestQueue = Volley.newRequestQueue(this)

        button.setOnClickListener {
                jsonParse()
            }
    }


    ////////////////////////////////////////////
    private fun jsonParse() {

       val mContext = getApplicationContext();
        val url = "https://canvas.northseattle.edu/courses/2086372/assignments/22358156"
       val listView = findViewById<ListView>(R.id.listview_2)
        var list = mutableListOf<trafficModel>()

        val mutableListIterator = list.listIterator()
       listView.adapter = trafficAdapter(this, R.layout.activity_traffic_camera, list)

        val request = JsonObjectRequest(
            Request.Method.GET,
            url,
            null, Response.Listener
            { response ->

                try {
                    val jsonArray = response.getJSONArray("Cameras")
                    val cameras = jsonArray.getJSONObject(0)
                    val descrip = cameras.getString("Description")
//                    val toast = Toast.makeText(applicationContext, descrip, Toast.LENGTH_LONG)
//                    toast.show()
                    for (i in 0 until jsonArray.length() - 1) {


                        val imageUrl = cameras.getInt("ImageUrl")

                        var webJpg = Picasso.get()
                            .load(imageUrl)
                            // .resize(50, 50)
                            // .centerCrop()
                            .toString()
                        var resID = resources.getIdentifier(webJpg, "drawable", packageName)

                        //textView.append("$Description, ImageUrl, $mail\n\n")
                        mutableListIterator.add(trafficModel( descrip, resID))
                        listView.adapter = trafficAdapter(this, R.layout.activity_traffic_camera, list)

                    }
                }
                catch (e: JSONException) {
                    e.printStackTrace()
                }
            },

            { error -> error.printStackTrace() })
        requestQueue?.add(request)

               }
            }

