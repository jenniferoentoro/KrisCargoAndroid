package com.example.projectpaba

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class adapterPricelist(
    private  val listPriceList: ArrayList<dataPriceList>) : RecyclerView.Adapter<adapterPricelist.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallBack


    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var rute : TextView = itemView.findViewById(R.id.textView16)
        var jenis : TextView = itemView.findViewById(R.id.textView15)
        var term : TextView = itemView.findViewById(R.id.textView14)
        var commodity : TextView = itemView.findViewById(R.id.textView17)
        var hargaTotal : TextView = itemView.findViewById(R.id.textView18)
        var listPrice : ConstraintLayout = itemView.findViewById(R.id.listPrice)
    }

    interface OnItemClickCallBack {
        fun onItemClicked(data:dataPriceList)
    }

    fun setOnItemClickCallBack(onItemClickCallback: OnItemClickCallBack){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.pricelistitem,parent,false)
        return  ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var pricelist = listPriceList[position]
        var ruteFix = "${pricelist.rute_dari} - ${pricelist.rute_ke}"
        holder.rute.setText(ruteFix)
        holder.jenis.setText(pricelist.jenis)
        holder.term.setText(pricelist.term)
        holder.commodity.setText(pricelist.commodity)
        var hrgaTotal = pricelist.harga.toDouble() + pricelist.harga.toDouble() * pricelist.PPN.toDouble()
        holder.hargaTotal.setText(hrgaTotal.toString())



        holder.listPrice.setOnClickListener {
            onItemClickCallback.onItemClicked(listPriceList[position])
        }

    }

    override fun getItemCount(): Int {
        return listPriceList.size
    }

}