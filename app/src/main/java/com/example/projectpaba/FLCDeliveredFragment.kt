package com.example.projectpaba

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import android.widget.ProgressBar
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FLCDeliveredFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FLCDeliveredFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_f_l_c_delivered, container, false)
    }


    private var arDataFCL = arrayListOf<dataFCL>()
    private var arDataUrl = arrayListOf<String>()
    lateinit var rvList : RecyclerView
    lateinit var db : FirebaseFirestore
    lateinit var progressBar: ProgressBar
    lateinit var nodata: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = FirebaseFirestore.getInstance()
        rvList = view.findViewById<RecyclerView>(R.id.rvFCLProcess)
        arDataFCL.clear()
        arDataUrl.clear()
        progressBar = view.findViewById(R.id.progress_bar)
        progressBar.visibility = View.VISIBLE
        nodata = view.findViewById(R.id.nodata)
        nodata.visibility = View.GONE
        TambahData()



    }
    var ada = false
    fun TambahData(){
        ada = false

        var qty = 0

        var tglreceived = ""
        val sp = activity?.getSharedPreferences("dataSP", AppCompatActivity.MODE_PRIVATE)

        var user_id = sp?.getString("user_id",null)
        db.collection("container_load").get()
            .addOnSuccessListener { result ->

                for (documen in result){
                    var rutedari = ""
                    var ruteke = ""
                    var nokapal = ""
                    var detailBrg = arrayListOf<dataDetailBrg>()


//                    val dataBaru = dataFCL(documen.data.get("bayar").toString(),documen.data.get("no_invoice").toString(),documen.data.get("tanggal_bayar").toString(),documen.data.get("tgl_invoice").toString(),documen.data.get("tgl_jatuh_tempo").toString(),documen.data.get("total_tagihan").toString())

                    if (documen.data.get("user_id").toString() == user_id && documen.data.get("status_id").toString() == "1" && documen.data.get("tipe").toString() == "0"){
                        ada = true
                        var sch_id =  documen.data.get("schedule_id")
                        Log.d("firebasee2a","dapet")
                        db.collection("schedules").get()
                            .addOnSuccessListener { resultSchedule ->

                                for (docSchedule in resultSchedule){
                                    if(docSchedule.data.get("id") == sch_id){
                                        Log.d("firebasee2b","dapet")
                                        rutedari = docSchedule.data.get("kotaAsal").toString()
                                        ruteke = docSchedule.data.get("kotaTujuan").toString()
                                        nokapal = docSchedule.data.get("title").toString()

                                        db.collection("detail_barang_container_load").get()
                                            .addOnSuccessListener { resultbrg ->
                                                for (docDBCL in resultbrg){

                                                    if(docDBCL.data.get("resi") == documen.data.get("resi")){
                                                        Log.d("firebasee2c","dapet")
                                                        qty = docDBCL.data.get("qty").toString().toInt()
                                                        db.collection("delivered_detail_barang").get()
                                                            .addOnSuccessListener { resultbrgDelived ->
                                                                var dapet = false
                                                                var qtyDelivered = 0
                                                                for (docDelived in resultbrgDelived){
                                                                    if(docDBCL.data.get("id_detail_barang") == docDelived.data.get("id_detail_barang")){
                                                                        dapet = true
                                                                        Log.d("firebasee2d","dapet")
                                                                        qtyDelivered = docDelived.data.get("qty").toString().toInt()
                                                                    }
                                                                }
                                                                if (dapet == false){
                                                                    qtyDelivered = 0
                                                                }

                                                                        db.collection("detail_barang").get()
                                                                            .addOnSuccessListener { resultDetailBrg ->
                                                                                for (docDetail in resultDetailBrg){

                                                                                    if(docDetail.data.get("id") == docDBCL.data.get("id_detail_barang")){
                                                                                        Log.d("firebasee2e","dapet")
                                                                                        detailBrg.add(dataDetailBrg(docDetail.data.get("deskripsi").toString(),docDetail.data.get("dimen_lebar").toString().toDouble(),docDetail.data.get("dimen_panjang").toString().toDouble(),docDetail.data.get("dimen_tinggi").toString().toDouble(),docDetail.data.get("id").toString(),docDetail.data.get("order_id").toString(),docDetail.data.get("qty").toString().toInt(),qtyDelivered))
                                                                                    }
                                                                                }
                                                                                db.collection("order").get().
                                                                                addOnSuccessListener { resultOrder ->
                                                                                    for (docOrder in resultOrder){
                                                                                        if(docOrder.data.get("id") == documen.data.get("order_id")){
                                                                                            Log.d("firebasee2f","dapet")
                                                                                            db.collection("dokumen_foto").get().
                                                                                            addOnSuccessListener { result_foto ->
                                                                                                for (docfoto in result_foto){
                                                                                                    if(docfoto.data.get("resi") == documen.data.get("resi")){
                                                                                                        Log.d("firebasee2g","dapet")
                                                                                                        arDataUrl.add(docfoto.data.get("url").toString())
                                                                                                    }
                                                                                                }


                                                                                                arDataFCL.add(dataFCL(documen.data.get("invoice_id").toString(),documen.data.get("no_container").toString(),documen.data.get("order_id").toString(),documen.data.get("penerima").toString(),documen.data.get("resi").toString(),documen.data.get("schedule_id").toString().toInt(),rutedari,ruteke,nokapal,documen.data.get("status_id").toString().toInt(),documen.data.get("tgl_arrive").toString(),documen.data.get("tgl_depart").toString(),docOrder.data.get("tgl_barang_received").toString(),documen.data.get("tgl_tiba").toString(),documen.data.get("tipe").toString().toInt(),documen.data.get("user_id").toString(),detailBrg,arDataUrl))


                                                                                                Log.d("firebaseSuc",arDataFCL.toString())
                                                                                                rvList.layoutManager = LinearLayoutManager(view?.context, LinearLayoutManager.VERTICAL, false)

                                                                                                val adapter = adapterflcProcess(arDataFCL,sp?.getString("name", null).toString())
                                                                                                rvList.adapter = adapter
                                                                                                progressBar.visibility = View.GONE
                                                                                                adapter.setOnItemClickCallBack(object : adapterflcProcess.OnItemClickCallBack{
                                                                                                    override fun onItemClicked(data: dataFCL) {
                                                                                                        val mBundle = Bundle()
                                                                                                        mBundle.putParcelable("DataFLCProc",data)
                                                                                                        val detailPrice = flcDetailFragment()
                                                                                                        detailPrice.arguments = mBundle
                                                                                                        val mFragmentManager = parentFragment!!.parentFragmentManager
                                                                                                        mFragmentManager.beginTransaction().apply {
                                                                                                            replace(R.id.frameLayout,detailPrice,flcDetailFragment::class.java.simpleName)
                                                                                                            addToBackStack(null)
                                                                                                            commit()
                                                                                                        }

                                                                                                    }
                                                                                                })


                                                                                            }


                                                                                        }
                                                                                    }
                                                                                }






                                                                            }


                                                            }




                                                    }
                                                }
                                            }


                                    }
                                }


                            }


                    }






                }
                if (ada == false){
                    progressBar.visibility = View.GONE
                    nodata.visibility = View.VISIBLE
                }


            }
            .addOnFailureListener {
                progressBar.visibility = View.GONE
                Log.d("Firebase",it.message.toString())
            }
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FLCDeliveredFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FLCDeliveredFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}