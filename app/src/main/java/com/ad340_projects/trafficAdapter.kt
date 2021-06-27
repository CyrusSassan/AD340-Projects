package com.ad340_projects

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class trafficAdapter (var tCtx: Context, var resources:Int, var items:List<trafficModel>):
    ArrayAdapter<trafficModel>(tCtx, resources, items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(tCtx)
        val view: View = layoutInflater.inflate(resources, null)
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val titleTextView: TextView = view.findViewById(R.id.textViewResult)

        var vItems:trafficModel = items[position]
        //imageView.setImageDrawable(tCtx.resources.getDrawable(vItems.img))
        titleTextView.text = vItems.description
        return view
    }
}
