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
 * Use the [billingDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class billingDetailFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_billing_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val noinvoice = view.findViewById<TextView>(R.id.joa)

        val tglinvoice = view.findViewById<TextView>(R.id.textView24)
        val tgljtuhtmpo = view.findViewById<TextView>(R.id.textView98)
        val totaltagihan = view.findViewById<TextView>(R.id.textView96)
        val byr = view.findViewById<TextView>(R.id.textView94)
        val sisa = view.findViewById<TextView>(R.id.textView92)
        val tglbyr = view.findViewById<TextView>(R.id.textView84)


        if (arguments!= null){
            val data = arguments?.getParcelable<dataBilling>("DataBilUn")
            noinvoice.setText(data!!.no_invoice)

            tglinvoice.setText(data!!.tgl_invoice)
            tgljtuhtmpo.setText(data!!.tgl_jatuh_tempo)
            totaltagihan.setText(data!!.total_tagihan)
            byr.setText(data!!.bayar)
            if(data!!.tanggal_bayar == ""){
                tglbyr.setText("-")
            }else{
                tglbyr.setText(data!!.tanggal_bayar)
            }

            val sisaItung = data.total_tagihan.toDouble() - data.bayar.toDouble()
            sisa.setText(sisaItung.toString())


        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment billingDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            billingDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}