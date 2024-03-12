package com.example.projectpaba

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson

class schedule : AppCompatActivity() {
    private var _title: ArrayList<String> = arrayListOf()
    private var _tvKA: ArrayList<String> = arrayListOf()
    private var _tvCD: ArrayList<String> = arrayListOf()
    private var _tvDur: ArrayList<String> = arrayListOf()
    private var _tvKT: ArrayList<String> = arrayListOf()
    private var _tvETA: ArrayList<String> = arrayListOf()
    lateinit var _rvSchedule : RecyclerView

    private var arSchedule = arrayListOf<dataSchedule>()

    lateinit var db : FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)

        db = FirebaseFirestore.getInstance()
        _rvSchedule = findViewById(R.id.rvSchedule)
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
                _rvSchedule.layoutManager = LinearLayoutManager(this)

                val adapterP = adapterSchedule(arSchedule)
                _rvSchedule.adapter = adapterP
                Log.d("tambahData", arSchedule.size.toString())
            }
            .addOnFailureListener { exception ->
                Log.w("TAGSUKSES2", "Error getting documents.", exception)
            }

    }
}