package com.ad340_projects

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.json.JSONException
import org.json.JSONObject


/**
 * An activity that displays a Google map with a marker (pin) to indicate a particular location.
 */
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private var requestQueue: RequestQueue? = null
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_maps)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = "Seattle Traffic Cameras"
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowHomeEnabled(true)
        }

        // Get the SupportMapFragment and request notification when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }


    internal class MyLocationLayerActivity : AppCompatActivity(),
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_maps_current_place)
            val mapFragment =
                supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
            mapFragment.getMapAsync(this)
        }

        @SuppressLint("MissingPermission")
        override fun onMapReady(map: GoogleMap) {
            // TODO: Before enabling the My Location layer, you must request
            // location permission from the user. This sample does not include
            // a request for location permission.
            map.isMyLocationEnabled = true
            map.setOnMyLocationButtonClickListener(this)
            map.setOnMyLocationClickListener(this)
        }

        override fun onMyLocationClick(location: Location) {
            val intent = android.content.Intent(this, MapsActivityCurrentPlace::class.java)
            startActivity(intent)
        }

        override fun onMyLocationButtonClick(): Boolean {
            val intent = android.content.Intent(this, MapsActivityCurrentPlace::class.java)
            startActivity(intent)
            // Return false so that we don't consume the event and the default behavior still occurs
            // (the camera animates to the user's current position).
            return false
        }
    }


    override fun onMapReady(googleMap: GoogleMap) {
        ////////////////////////////////////////////

            requestQueue = Volley.newRequestQueue(this)
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
                            var cam = jsonArray.getJSONArray("Cameras")
                            val descrip = cam.getJSONObject(0)
                            val des = descrip.getString("Description")

                            val latArray = jsonArray.getJSONArray("PointCoordinate")
//                            val latObject = latArray.getJSONObject(0)
                            val latitude = latArray.getString(0).toDouble()
                            val longitude = latArray.getString(1).toDouble()
//                            //convert string to latlang
//                            val latlong = latString.split(",").toTypedArray()
//                            val latitude = latlong[0].toDouble()
//                            val longitude = latlong[1].toDouble()
                            val latlang = LatLng(latitude, longitude)
                            googleMap.addMarker(
                                MarkerOptions()
                                    .position(latlang)
                                    .title(des)
                            )
                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latlang))



                            //val toast = Toast.makeText(applicationContext, latString.toString(), Toast.LENGTH_LONG)
                            //toast.show()
                        }
                        val RedmondCamera = LatLng(47.6696214177986, -122.107831582823)
                        val zoomLevel = 10.0f
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(RedmondCamera, zoomLevel))


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
