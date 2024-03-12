package com.example.projectpaba

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TrackingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TrackingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val _toFCL = view.findViewById<TextView>(R.id.tvFCL)
        val _toLCL = view.findViewById<TextView>(R.id.tvLCL)
        val _toFCL2 = view.findViewById<ImageView>(R.id.ivFCL)
        val _toLCL2 = view.findViewById<ImageView>(R.id.ivLCL)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbarHome)

        val wa = toolbar.findViewById<ImageView>(R.id.wa)
        wa.setOnClickListener {
            val url = "http://wa.me/6281231592369"
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(url)
            startActivity(openURL)
        }

        _toFCL.setOnClickListener {
            val mfDua = FLCFragment()
            val mFragmentManager = parentFragmentManager
            mFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout, mfDua, FLCFragment::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }

        _toFCL2.setOnClickListener {
            val mfDua = FLCFragment()
            val mFragmentManager = parentFragmentManager
            mFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout, mfDua, FLCFragment::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }

        _toLCL.setOnClickListener {
            val mfDua = LCLFragment()
            val mFragmentManager = parentFragmentManager
            mFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout, mfDua, LCLFragment::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }

        _toLCL2.setOnClickListener {
            val mfDua = LCLFragment()
            val mFragmentManager = parentFragmentManager
            mFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout, mfDua, LCLFragment::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }
    }

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
        return inflater.inflate(R.layout.fragment_tracking, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TrackingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TrackingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}