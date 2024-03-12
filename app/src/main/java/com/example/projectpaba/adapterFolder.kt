package com.example.projectpaba

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class adapterFolder (
    private val listGallery: ArrayList<dataGallery>
) : RecyclerView.Adapter<adapterFolder.ListViewHolder>(){

    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data : dataGallery)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var _image : ImageView = itemView.findViewById(R.id.ivGallery)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.itemfolder, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var gallery = listGallery[position]

        Picasso.get()
            .load(gallery.path)
            .into(holder._image)


        holder._image.setOnClickListener{
            onItemClickCallback.onItemClicked(listGallery[position])
        }
    }

    override fun getItemCount(): Int {
        return listGallery.size

    }
}