package com.example.projectpaba


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class dataOrder(
    var id : String,
    var tgl_barang_received : String,
    var tgl_order : String,
    var user_id: Number,
    var pengirim: String
) : Parcelable
