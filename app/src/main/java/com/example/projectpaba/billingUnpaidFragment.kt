package com.example.projectpaba

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [billingUnpaidFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class billingUnpaidFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        retainInstance = true

    }

    private var arBilUnpaid = arrayListOf<dataBilling>()
    lateinit var rvList: RecyclerView
    lateinit var db: FirebaseFirestore
    lateinit var progressBar: ProgressBar
    lateinit var nodata: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_billing_unpaid, container, false)



        return view
    }

    fun test() {
//        var inflater = layoutInflater
//        var container : ViewGroup
//        val view = inflater.inflate(R.layout.fragment_billing_unpaid, container, false)
//        db = FirebaseFirestore.getInstance()
//        rvList = view.findViewById<RecyclerView>(R.id.rvbilUn)
//        TambahData()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = FirebaseFirestore.getInstance()
        arBilUnpaid.clear()
        rvList = view.findViewById<RecyclerView>(R.id.rvbilUn)
        progressBar = view.findViewById(R.id.progress_bar)
        progressBar.visibility = View.VISIBLE
        nodata = view.findViewById(R.id.nodata)
        nodata.visibility = View.GONE
        TambahData()
    }

    var ada = false
    var ada2 = false
    fun TambahData() {
        val sp = activity?.getSharedPreferences("dataSP", AppCompatActivity.MODE_PRIVATE)
        var user_id = sp?.getString("user_id", null)


        ada = false
        ada2 = false
        var listInovice = arrayListOf<String>()

        db.collection("container_load").get().addOnSuccessListener { resultLoad ->
            for (documenyuhu in resultLoad) {
                if (documenyuhu.data.get("user_id").toString() == user_id) {
                    ada = true
                    listInovice.add(documenyuhu.data.get("invoice_id").toString())
                }
            }
            if (ada == false) {
                progressBar.visibility = View.GONE
                nodata.visibility = View.VISIBLE
            } else {


                db.collection("invoice").get()
                    .addOnSuccessListener { result ->

                        for (documen in result) {
                            val dataBaru = dataBilling(
                                documen.data.get("bayar").toString(),
                                documen.data.get("no_invoice").toString(),
                                documen.data.get("tanggal_bayar").toString(),
                                documen.data.get("tgl_invoice").toString(),
                                documen.data.get("tgl_jatuh_tempo").toString(),
                                documen.data.get("total_tagihan").toString()
                            )

                            if ((dataBaru.bayar != dataBaru.total_tagihan || dataBaru.total_tagihan == "0") && listInovice.contains(dataBaru.no_invoice)) {
                                ada2 = true
                                arBilUnpaid.add(dataBaru)

                            }

                        }
                        if(ada2 == false){
                            progressBar.visibility = View.GONE
                            nodata.visibility = View.VISIBLE
                        }else {


                            Log.d("firebaseSuc", arBilUnpaid.toString())
                            rvList.layoutManager =
                                LinearLayoutManager(view?.context, LinearLayoutManager.VERTICAL, false)
                            val adapter = adapterBillingUnpaid(arBilUnpaid)
                            rvList.adapter = adapter
                            progressBar.visibility = View.GONE
                            adapter.setOnItemClickCallBack(object :
                                adapterBillingUnpaid.OnItemClickCallBack {
                                override fun onItemClicked(data: dataBilling) {
                                    val mBundle = Bundle()
                                    mBundle.putParcelable("DataBilUn", data)
                                    val detailPrice = billingDetailFragment()
                                    detailPrice.arguments = mBundle
                                    val mFragmentManager = parentFragment!!.parentFragmentManager
                                    mFragmentManager.beginTransaction().apply {
                                        replace(
                                            R.id.frameLayout,
                                            detailPrice,
                                            billingDetailFragment::class.java.simpleName
                                        )
                                        addToBackStack(null)
                                        commit()
                                    }


                                }
                            })
                        }



                    }
                    .addOnFailureListener {
                        progressBar.visibility = View.GONE
                        Log.d("Firebase", it.message.toString())
                    }
            }



        }
            .addOnFailureListener {
                progressBar.visibility = View.GONE
                Log.d("Firebase", it.message.toString())
            }


    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment billingUnpaidFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            billingUnpaidFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}