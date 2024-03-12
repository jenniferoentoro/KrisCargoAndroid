package com.example.projectpaba

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ScheduleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScheduleFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _title: ArrayList<String> = arrayListOf()
    private var _tvKA: ArrayList<String> = arrayListOf()
    private var _tvCD: ArrayList<String> = arrayListOf()
    private var _tvDur: ArrayList<String> = arrayListOf()
    private var _tvKT: ArrayList<String> = arrayListOf()
    private var _tvETA: ArrayList<String> = arrayListOf()
    lateinit var _rvSchedule : RecyclerView

    private var arSchedule = arrayListOf<dataSchedule>()

    lateinit var db : FirebaseFirestore

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbarHome)

        val wa = toolbar.findViewById<ImageView>(R.id.wa)
        wa.setOnClickListener {
            val url = "http://wa.me/6281231592369"
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(url)
            startActivity(openURL)
        }
        db = FirebaseFirestore.getInstance()
        val progressBar: ProgressBar = view.findViewById(R.id.progressBarNotification)
        _rvSchedule = view.findViewById(R.id.rvSchedule)
        progressBar.visibility = View.VISIBLE
        db.collection("schedules")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("lihatData", "${document.id} => ${document.data.get("title")}")
                    _title.add("${document.data.get("title")}")
                    _tvKA.add("${document.data.get("kotaAsal")}")
                    _tvCD.add("Close Date: ${document.data.get("closeDate").toString()}")
                    _tvDur.add("Duration: ${document.data.get("duration").toString()}")
                    _tvKT.add("${document.data.get("kotaTujuan")}")
                    _tvETA.add("${document.data.get("etd").toString()} - ${document.data.get("eta").toString()}")
                }
//                Log.d("tambahData", _title.size.toString())
                for (position in _title.indices){
                    val data = dataSchedule(
                        _title[position],
                        _tvKA[position],
                        _tvKT[position],
                        _tvCD[position],
                        _tvDur[position],
                        _tvETA[position],
                    )
                    arSchedule.add(data)
                }
                _rvSchedule.layoutManager = LinearLayoutManager(context)

                val adapterP = adapterSchedule(arSchedule)
                _rvSchedule.adapter = adapterP
                progressBar.visibility = View.GONE
                Log.d("tambahData", arSchedule.size.toString())
            }
            .addOnFailureListener { exception ->
                progressBar.visibility = View.GONE
                Log.w("TAGSUKSES2", "Error getting documents.", exception)
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
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ScheduleFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ScheduleFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}