package com.example.projectpaba

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class adapterSchedule (
    private val listSchedule: ArrayList<dataSchedule>
) : RecyclerView.Adapter<adapterSchedule.ListViewHolder>(){

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var _title : TextView = itemView.findViewById(R.id.tvTitle)
        var _tvKA : TextView = itemView.findViewById(R.id.tvKA)
        var _tvCD : TextView = itemView.findViewById(R.id.tvCD)
        var _tvDur : TextView = itemView.findViewById(R.id.tvDur)
        var _tvKT : TextView = itemView.findViewById(R.id.tvKT)
        var _tvETA : TextView = itemView.findViewById(R.id.tvETA)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.itemschedule, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var schedule = listSchedule[position]

        holder._title.setText(schedule.title)
        holder._tvKA.setText(schedule.kotaAsal)
        holder._tvKT.setText(schedule.kotaTujuan)
        holder._tvCD.setText(schedule.closeDate)
        holder._tvDur.setText(schedule.duration)
        holder._tvETA.setText(schedule.eta)

    }

    override fun getItemCount(): Int {
        return listSchedule.size
    }
}