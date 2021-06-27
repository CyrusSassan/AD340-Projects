package com.ad340_projects

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject
import android.content.Context
import android.net.ConnectivityManager
import android.widget.SimpleAdapter


@RequiresApi(Build.VERSION_CODES.O)

class TrafficCameraActivity : AppCompatActivity() {
   private var requestQueue: RequestQueue? = null
    // Show BackButton on Actionbar
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private val isNetworkConnected: Boolean
        get() = (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetwork != null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_traffic_camera)
        //setContentView(R.layout.activity_movies_list)

        // Show BackButton and Set custom Title on Actionbar
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = "Seattle Traffic Camera"
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowHomeEnabled(true)
        }

        title = "Traffic"
        //textView = findViewById(R.id.textViewResult)
        val button = findViewById<Button>(R.id.btnParse)


        button.setOnClickListener {
            if (isNetworkConnected) {
                // Call AsyncTask for getting developer list from server (JSON Api)
                jsonParse()
            } else {
                Toast.makeText(applicationContext, "No Internet Connection Yet!", Toast.LENGTH_SHORT).show()
            }            }
    }

    ////////////////////////////////////////////
    private fun jsonParse() {
        val listView = findViewById<ListView>(R.id.listview_2)
        var list = mutableListOf<trafficModel>()

        val imageView: ImageView = findViewById(R.id.imageView)

        val mutableListIterator = list.listIterator()
       //val mContext = getApplicationContext();

        if (isNetworkConnected) {
            // Call AsyncTask for getting developer list from server (JSON Api)
            //getDeveloper().execute()
        } else {
            Toast.makeText(applicationContext, "No Internet Connection Yet!", Toast.LENGTH_SHORT).show()
        }

        requestQueue = Volley.newRequestQueue(this)
        listView.adapter = trafficAdapter(this, R.layout.activity_traffic_camera, list)

        val url = "https://web6.seattle.gov/Travelers/api/Map/Data?zoomId=13&type=2"

        val request = StringRequest(
            Request.Method.GET, url, Response.Listener<String>
            { response ->
                try {

                    val jsonObj = JSONObject(response)

                    val jsonArray = jsonObj.getJSONArray("Features")

                    for (i in 0 until jsonArray.length() - 1) {

                        //val cameras = jsonObj.getJSONObject("Description")
                        val jsonArray = jsonObj.getJSONArray("Features").getJSONObject(i)
                        //val jsonArray = jsonObj.getJSONArray("Cameras")
                        var jsonInner = jsonArray.getJSONArray("Cameras")
                        val descrip = jsonInner.getJSONObject(0)
                        val imageUrl = jsonInner.getJSONObject(0)

                        val des = descrip.getString("Description")
                        val img = imageUrl.getString("ImageUrl")
                        var webJpg = Picasso.get()
                            .load(img)
                            .resize(50, 50)
                            .centerCrop()
                            .into(imageView)



                       // var resID = resources.getIdentifier(webJpg,null,null)

                        val toast = Toast.makeText(applicationContext, des.toString(), Toast.LENGTH_LONG)
                        toast.show()
                        val toastImg = Toast.makeText(applicationContext, img.toString(), Toast.LENGTH_LONG)
                        toastImg.show()
                        mutableListIterator.add(trafficModel( des, webJpg))
                        listView.adapter = trafficAdapter(this, R.layout.activity_traffic_camera, list)

                    }
                }
                catch (e: JSONException) {
                    val toast = Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG)
                    toast.show()
                }
            },

            { error ->
                error.printStackTrace() })
        requestQueue?.add(request)

               }
            }

