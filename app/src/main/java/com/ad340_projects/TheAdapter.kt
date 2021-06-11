package com.ad340_projects

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class TheAdapter (var mCtx: Context, var resources:Int, var items:List<Model>):
    ArrayAdapter<Model>(mCtx, resources, items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(resources, null)
        val imageView: ImageView = view.findViewById(R.id.image)
        val indexTextView: TextView = view.findViewById(R.id.textviewindex)
        val titleTextView: TextView = view.findViewById(R.id.textview1)
        val yearTextView: TextView = view.findViewById(R.id.textview2)

        var vItems:Model = items[position]
        imageView.setImageDrawable(mCtx.resources.getDrawable(vItems.img))
        indexTextView.text = vItems.indexi
        titleTextView.text = vItems.Title
        yearTextView.text = vItems.Year

        return view
    }
}