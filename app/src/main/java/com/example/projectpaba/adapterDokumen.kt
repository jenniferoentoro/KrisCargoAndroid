package com.example.projectpaba

import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class adapterDokumen (
    private  val listUrl: ArrayList<String>) : RecyclerView.Adapter<adapterDokumen.ListViewHolder>(){

    private lateinit var onItemClickCallback: OnItemClickCallBack


    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var image2 : ImageView = itemView.findViewById(R.id.imageView2)

    }

    interface OnItemClickCallBack {
        fun onItemClicked(data:String)
    }

    fun setOnItemClickCallBack(onItemClickCallback: OnItemClickCallBack){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.lihatdokumendetail,parent,false)
        return  ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        Log.d("kepanggil","t")
        var url = listUrl[position]



        Glide.with(holder.itemView.context)
            .load(url)
            .into(holder.image2)
        Log.d("url778",holder.image2.resources.toString())
        holder.image2.setOnClickListener {
            onItemClickCallback.onItemClicked(listUrl[position])
        }


    }

    override fun getItemCount(): Int {
        return listUrl.size
    }
}