package com.example.projectpaba

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class adapterBillingHistory (
    private  val listBillHistory: ArrayList<dataBilling>) : RecyclerView.Adapter<adapterBillingHistory.ListViewHolder>(){

    private lateinit var onItemClickCallback: OnItemClickCallBack


    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var noinvoice : TextView = itemView.findViewById(R.id.textView16)
        var tglinvoice : TextView = itemView.findViewById(R.id.textView15)
        var tgljthtempo : TextView = itemView.findViewById(R.id.textView14)
        var total : TextView = itemView.findViewById(R.id.textView17)
        var tglbyr : TextView = itemView.findViewById(R.id.textView18)
        var listBilUN : ConstraintLayout = itemView.findViewById(R.id.listBilHis)
    }

    interface OnItemClickCallBack {
        fun onItemClicked(data:dataBilling)
    }

    fun setOnItemClickCallBack(onItemClickCallback: OnItemClickCallBack){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.billinghistoryitem,parent,false)
        return  ListViewHolder(view)
    }



    override fun getItemCount(): Int {
        return listBillHistory.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var billUnlist = listBillHistory[position]

        holder.noinvoice.setText(billUnlist.no_invoice)
        holder.tglinvoice.setText(billUnlist.tgl_invoice)
        holder.tgljthtempo.setText(billUnlist.tgl_jatuh_tempo)
        holder.total.setText(billUnlist.total_tagihan)
        holder.tglbyr.setText(billUnlist.tanggal_bayar)
        holder.listBilUN.setOnClickListener {
            onItemClickCallback.onItemClicked(listBillHistory[position])
        }
    }
}