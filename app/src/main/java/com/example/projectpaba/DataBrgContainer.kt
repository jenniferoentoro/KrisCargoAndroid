package com.example.projectpaba


import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataBrgContainer (
    var id_detail_barang : String,
    var qty : Number,
    var resi : String

) : Parcelable