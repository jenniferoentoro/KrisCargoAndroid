package com.example.projectpaba
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataContainer(
    var invoice_id : String,
    var no_container : String,
    var order_id : String,
    var penerima : String,
    var resi : String,
    var schedule_id : Number,
    var status_id : String,
    var tgl_arrive : String,
    var tgl_depart : String,
    var tgl_tiba : String,
    var tipe : String,
    var user_id : String
) : Parcelable