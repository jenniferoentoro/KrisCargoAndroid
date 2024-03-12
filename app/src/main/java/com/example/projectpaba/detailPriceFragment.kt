package com.example.projectpaba

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [detailPriceFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class detailPriceFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_detail_price, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val joa = view.findViewById<TextView>(R.id.joa)
        val berlaku = view.findViewById<TextView>(R.id.berlaku)
        val rute = view.findViewById<TextView>(R.id.textView24)
        val jenis = view.findViewById<TextView>(R.id.textView98)
        val term = view.findViewById<TextView>(R.id.textView96)
        val commodity = view.findViewById<TextView>(R.id.textView94)
        val pol = view.findViewById<TextView>(R.id.textView92)
        val pod = view.findViewById<TextView>(R.id.textView90)
        val harga = view.findViewById<TextView>(R.id.textView88)
        val ppn = view.findViewById<TextView>(R.id.textView86)
        val hrgatotal = view.findViewById<TextView>(R.id.textView84)


        if (arguments!= null){
            val data = arguments?.getParcelable<dataPriceList>("Data")
            joa.setText(data!!.no_joa)
            berlaku.setText(data!!.berlaku_sejak)
            rute.setText("${data!!.rute_dari} - ${data!!.rute_ke}")
            jenis.setText(data!!.jenis)
            term.setText(data!!.term)
            commodity.setText(data!!.commodity)
            pol.setText(data!!.POL)
            pod.setText(data!!.POD)
            harga.setText(data!!.harga.toString())
            val ppnItung = data.harga.toString().toDouble() * data.PPN.toString().toDouble()
            ppn.setText(ppnItung.toString())
            val totalHrga = ppnItung + data.harga.toString().toDouble()
            hrgatotal.setText(totalHrga.toString())
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment detailPriceFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            detailPriceFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}