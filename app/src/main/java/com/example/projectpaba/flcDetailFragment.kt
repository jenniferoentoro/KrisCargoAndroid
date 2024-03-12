package com.example.projectpaba

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.skydoves.expandablelayout.ExpandableLayout

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [flcDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class flcDetailFragment : Fragment() {
    // private var listViewAdapter= ExpandableListViewAdapter

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
        return inflater.inflate(R.layout.fragment_flc_detail, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val noresi = view.findViewById<TextView>(R.id.joa)
        val invoice = view.findViewById<TextView>(R.id.berlaku)

        val pengirim = view.findViewById<TextView>(R.id.textView24)
        val penerima = view.findViewById<TextView>(R.id.textView98)
        val rute = view.findViewById<TextView>(R.id.textView96)
        val tglbrgkt = view.findViewById<TextView>(R.id.textView94)
        val tgltiba = view.findViewById<TextView>(R.id.textView92)
        val kapal = view.findViewById<TextView>(R.id.textView90)
        val nokontainer = view.findViewById<TextView>(R.id.textView82)
        val jenis = view.findViewById<TextView>(R.id.textView80)
        val jmlhbrg = view.findViewById<TextView>(R.id.textView78)
        val volume = view.findViewById<TextView>(R.id.textView76)
        val detail = view.findViewById<TextView>(R.id.detailbrg)
        val lhtdok = view.findViewById<TextView>(R.id.button)
        val ex = view.findViewById<ExpandableLayout>(R.id.expandable)

        val status = ex.parentLayout.findViewById<TextView>(R.id.textView96)

        val statusDetail = ex.secondLayout.findViewById<TextView>(R.id.textView20)


        var g = 0
        ex.parentLayout.setOnClickListener {

            if (g == 0) {
                g = 1
                ex.expand()
            } else {
                g = 0
                ex.collapse()
            }
        }

        if (arguments != null) {
            val data = arguments?.getParcelable<dataFCL>("DataFLCProc")
            noresi.setText(data!!.resi)
            invoice.setText(data!!.invoice_id)
            val sp = activity?.getSharedPreferences("dataSP", AppCompatActivity.MODE_PRIVATE)


            pengirim.setText(sp?.getString("name", null))
            penerima.setText(data!!.penerima)
            rute.setText("${data!!.ruteDari} - ${data!!.ruteKe}")
            tglbrgkt.setText(data!!.tgl_depart)
            tgltiba.setText(data!!.tgl_arrive)
            kapal.setText(data!!.noKapal)
            nokontainer.setText(data!!.no_container)
            if (data!!.tipe == 0) {
                jenis.setText("FLC")
            } else {
                jenis.setText("LCL")
            }
            var detailBrgIsi = ""
            var qty = 0
            var volumeTotal = 0.0
            for (i in data!!.detailbrg) {
                qty += i.qty.toString().toInt()
                var vol =
                    (i.dimen_lebar.toDouble() * i.dimen_panjang.toDouble() * i.dimen_tinggi.toDouble() / 1000000)
                volumeTotal += vol
                if (i.qty != i.qtyArrived) {
                    detailBrgIsi += "${i.qty} ${i.deskripsi} (${i.dimen_panjang} * ${i.dimen_lebar} * ${i.dimen_tinggi} = $vol (arrived = ${i.qtyArrived})\n"

                }else{
                    detailBrgIsi += "${i.qty} ${i.deskripsi} (${i.dimen_panjang} * ${i.dimen_lebar} * ${i.dimen_tinggi} = $vol (arrived)\n"
                }
            }
            jmlhbrg.setText(qty.toString())
            volume.setText(volumeTotal.toString())
            detail.setText(detailBrgIsi)
            var isi = ""
            if (data.tgl_tiba != "") {
                status.setText("Shipment is delivered - " + data.tgl_tiba)
                isi += "○ Shipment is received - ${data.tgl_received}\n○ Shipment is departed - ${data.tgl_depart}\n○ Shipment is arrived - ${data.tgl_arrive}\n● Shipment is delivered - ${data.tgl_tiba}"
            } else if (data.tgl_arrive != "") {
                status.setText("Shipment is arrived - " + data.tgl_arrive)
                isi += "○ Shipment is received - ${data.tgl_received}\n○ Shipment is departed - ${data.tgl_depart}\n● Shipment is arrived - ${data.tgl_arrive}"
            } else if (data.tgl_depart != "") {
                status.setText("Shipment is departed - " + data.tgl_depart)
                isi += "○ Shipment is received - ${data.tgl_received}\n● Shipment is departed - ${data.tgl_depart}"
            } else if (data.tgl_received != "") {
                status.setText("Shipment is received - " + data.tgl_received)
                isi += "● Shipment is received - ${data.tgl_received}"
            }else{
                status.setText("Order created")
                isi += "● Order created"
            }

            statusDetail.setText(isi)

            lhtdok.setOnClickListener {
                val mBundle = Bundle()
                mBundle.putParcelable("DataDokFLCProc", data)
                val detailPrice = lihatDokumen()
                detailPrice.arguments = mBundle
                val mFragmentManager = parentFragmentManager
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frameLayout, detailPrice, lihatDokumen::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }

        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment flcDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            flcDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}