package com.example.projectpaba

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class AdapterNews (
    private val listNews: ArrayList<DataNews>
) : RecyclerView.Adapter<AdapterNews.ListViewHolder>(){

    private lateinit var onItemClickCallBack : OnItemClickCallBack

    interface OnItemClickCallBack {
        fun onItemClicked(data : DataNews)
    }

    fun setOnItemClickCallback(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var _title : TextView = itemView.findViewById(R.id.tvTitleNewsCard)
        var _image : ImageView = itemView.findViewById(R.id.imgNewsCard)
        var llNews : LinearLayout = itemView.findViewById(R.id.llNews)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.itemnews, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var news = listNews[position]

        holder._title.setText(news.title)

        holder.llNews.setOnClickListener{
            onItemClickCallBack.onItemClicked(listNews[position])
        }


        Picasso.get()
            .load(news.foto)
            .into(holder._image)


    }

    override fun getItemCount(): Int {
        return listNews.size
    }
}