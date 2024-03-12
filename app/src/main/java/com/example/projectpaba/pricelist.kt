package com.example.projectpaba

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
 * Use the [pricelist.newInstance] factory method to
 * create an instance of this fragment.
 */
class pricelist : Fragment() {
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
        return inflater.inflate(R.layout.fragment_pricelist, container, false)
    }

    private var arPricelist = arrayListOf<dataPriceList>()
    lateinit var rvList: RecyclerView
    lateinit var db: FirebaseFirestore
    lateinit var progressBar: ProgressBar
    lateinit var nodata: TextView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = FirebaseFirestore.getInstance()
        rvList = view.findViewById(R.id.rvPL)

        progressBar = view.findViewById(R.id.progress_bar)
        progressBar.visibility = View.VISIBLE
       arPricelist.clear()
        nodata = view.findViewById(R.id.nodata)
        nodata.visibility = View.GONE
        TambahData()
    }

    var ada = false

    fun TambahData() {
        ada = false
        db.collection("pricelist").get()
            .addOnSuccessListener { result ->

                for (documen in result) {
                    val dataBaru = dataPriceList(
                        documen.data.get("POD").toString(),
                        documen.data.get("POL").toString(),
                        documen.data.get("PPN").toString().toDouble(),
                        documen.data.get("berlaku_sejak").toString(),
                        documen.data.get("commodity").toString(),
                        documen.data.get("harga").toString().toDouble(),
                        documen.data.get("jenis").toString(),
                        documen.data.get("no_joa").toString(),
                        documen.data.get("rute_dari").toString(),
                        documen.data.get("rute_ke").toString(),
                        documen.data.get("term").toString()
                    )


                    arPricelist.add(dataBaru)
                }
                if (arPricelist == null) {
                    progressBar.visibility = View.GONE
                    nodata.visibility = View.VISIBLE
                }else{


                Log.d("firebaseSuc", arPricelist.toString())
                rvList.layoutManager =
                    LinearLayoutManager(view?.context, LinearLayoutManager.VERTICAL, false)
                val adapter = adapterPricelist(arPricelist)
                rvList.adapter = adapter
                progressBar.visibility = View.GONE
                adapter.setOnItemClickCallBack(object : adapterPricelist.OnItemClickCallBack {
                    override fun onItemClicked(data: dataPriceList) {
                        val mBundle = Bundle()
                        mBundle.putParcelable("Data", data)
                        val detailPrice = detailPriceFragment()
                        detailPrice.arguments = mBundle
                        val mFragmentManager = parentFragmentManager
                        mFragmentManager.beginTransaction().apply {
                            replace(
                                R.id.frameLayout,
                                detailPrice,
                                detailPriceFragment::class.java.simpleName
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


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment pricelist.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            pricelist().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}