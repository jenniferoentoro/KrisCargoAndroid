package com.example.projectpaba


import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class dataFCL (
    var invoice_id : String,
    var no_container : String,
    var order_id : String,
    var penerima : String,
    var resi : String,
    var schedule_id : Number,
    var ruteDari : String,
    var ruteKe : String,
    var noKapal : String,
    var status_id : Number,
    var tgl_arrive : String,
    var tgl_depart : String,
    var tgl_received : String,
    var tgl_tiba : String,
    var tipe : Number,
    var user_id : String,
    var detailbrg : ArrayList<dataDetailBrg>,
    var urlDokumen : ArrayList<String>

) : Parcelable