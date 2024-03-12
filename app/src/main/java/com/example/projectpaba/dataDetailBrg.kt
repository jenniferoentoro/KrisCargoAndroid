package com.example.projectpaba


import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class dataDetailBrg (
    var deskripsi : String,
    var dimen_lebar : Number,
    var dimen_panjang : Number,
    var dimen_tinggi : Number,
    var id : String,
    var order_id : String,
    var qty : Number,
    var qtyArrived : Number

) : Parcelable