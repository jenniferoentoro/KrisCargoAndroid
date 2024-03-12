package com.example.projectpaba

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectpaba.notification.DataNotification
import com.squareup.picasso.Picasso

class AdapterNotification (
    private val listNotif: ArrayList<DataNotification>
) : RecyclerView.Adapter<AdapterNotification.ListViewHolder>(){

//    private lateinit var onItemClickCallBack : OnItemClickCallBack

//    interface OnItemClickCallBack {
//        fun onItemClicked(data : DataNotification)
//    }
//
//    fun setOnItemClickCallback(onItemClickCallBack: OnItemClickCallBack) {
//        this.onItemClickCallBack = onItemClickCallBack
//    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var _title : TextView = itemView.findViewById(R.id.tvNotifTitle)
        var _image : ImageView = itemView.findViewById(R.id.ivNotifIcon)
        var description : TextView = itemView.findViewById(R.id.tvNotifDescription)
        var date : TextView = itemView.findViewById(R.id.tvNotifDate)
        var cvNotif : CardView = itemView.findViewById(R.id.cvNotif)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.itemnotification, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var notif = listNotif[position]

        holder._title.setText(notif.title)
        
        holder.description.setText(notif.description)

        holder.date.setText(notif.date)

//        holder.cvNotif.setOnClickListener{
//            onItemClickCallBack.onItemClicked(listNotif[position])
//        }

//        if (notif.type == "FCL") {
//            holder._image.setImageResource(R.drawable.ic_baseline_width_full_24)
//        } else if (notif.type == "LCL") {
//            holder._image.setImageResource(R.drawable.ic_baseline_width_normal_24)
//        } else if (notif.type == "BILL") {
//            holder._image.setImageResource(R.drawable.ic_baseline_width_normal_24)
//        }



    }

    override fun getItemCount(): Int {
        return listNotif.size
    }
}