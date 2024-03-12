package com.example.projectpaba

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.internal.ContextUtils.getActivity
import android.app.Activity
class adapterflcProcess (
    private  val listFCLProcc: ArrayList<dataFCL>,private val pengirimStr:String) : RecyclerView.Adapter<adapterflcProcess.ListViewHolder>(){

    private lateinit var onItemClickCallback: OnItemClickCallBack


    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var noresi : TextView = itemView.findViewById(R.id.textView16)
        var pengirim : TextView = itemView.findViewById(R.id.textView15)
        var tglReceived : TextView = itemView.findViewById(R.id.textView14)
        var jlmBrg : TextView = itemView.findViewById(R.id.textView17)
        var kapal : TextView = itemView.findViewById(R.id.textView18)
        var status : TextView = itemView.findViewById(R.id.textView19)
        var listFCLProc : ConstraintLayout = itemView.findViewById(R.id.listFCL)
    }

    interface OnItemClickCallBack {
        fun onItemClicked(data:dataFCL)
    }

    fun setOnItemClickCallBack(onItemClickCallback: OnItemClickCallBack){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.fclitem,parent,false)
        return  ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var flcproc = listFCLProcc[position]
       // val sp = getActivity().getSharedPreferences("dataSP", AppCompatActivity.MODE_PRIVATE)
        holder.noresi.setText(flcproc.resi)
       holder.pengirim.setText(pengirimStr)
        holder.tglReceived.setText(flcproc.tgl_received)
        var qty = 0
        for (i in flcproc.detailbrg){
            qty+=i.qty.toString().toInt()
            Log.d("firebasee",qty.toString())
        }
        holder.jlmBrg.setText(qty.toString())

        holder.kapal.setText(flcproc.noKapal)

        if (flcproc.tgl_tiba != ""){
            holder.status.setText("Shipment is delivered - "+flcproc.tgl_tiba)
        }else if(flcproc.tgl_arrive != ""){
            holder.status.setText("Shipment is arrived - "+flcproc.tgl_arrive)
        }else if(flcproc.tgl_depart != ""){
            holder.status.setText("Shipment is departed - "+flcproc.tgl_depart)
        }else {
            holder.status.setText("Shipment is received - "+flcproc.tgl_received)
        }



        holder.listFCLProc.setOnClickListener {
            onItemClickCallback.onItemClicked(listFCLProcc[position])
        }

    }

    override fun getItemCount(): Int {
        return listFCLProcc.size
    }
}