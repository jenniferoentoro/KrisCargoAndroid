package com.example.projectpaba
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class adapterBillingUnpaid (
    private  val listBillUnpaid: ArrayList<dataBilling>) : RecyclerView.Adapter<adapterBillingUnpaid.ListViewHolder>(){

    private lateinit var onItemClickCallback: OnItemClickCallBack


    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var noinvoice : TextView = itemView.findViewById(R.id.textView16)
        var tglinvoice : TextView = itemView.findViewById(R.id.textView15)
        var tgljthtempo : TextView = itemView.findViewById(R.id.textView14)
        var total : TextView = itemView.findViewById(R.id.textView17)
        var sisa : TextView = itemView.findViewById(R.id.textView18)
        var listBilUN : ConstraintLayout = itemView.findViewById(R.id.listBilUn)
    }

    interface OnItemClickCallBack {
        fun onItemClicked(data:dataBilling)
    }

    fun setOnItemClickCallBack(onItemClickCallback: OnItemClickCallBack){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.billingunpaiditem,parent,false)
        return  ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var billUnlist = listBillUnpaid[position]

        holder.noinvoice.setText(billUnlist.no_invoice)
        holder.tglinvoice.setText(billUnlist.tgl_invoice)
        holder.tgljthtempo.setText(billUnlist.tgl_jatuh_tempo)
        holder.total.setText(billUnlist.total_tagihan)
        val sisabyr = billUnlist.total_tagihan.toDouble() - billUnlist.bayar.toDouble()
        holder.sisa.setText(sisabyr.toString())






        holder.listBilUN.setOnClickListener {
            onItemClickCallback.onItemClicked(listBillUnpaid[position])
        }

    }

    override fun getItemCount(): Int {
        return listBillUnpaid.size
    }
}