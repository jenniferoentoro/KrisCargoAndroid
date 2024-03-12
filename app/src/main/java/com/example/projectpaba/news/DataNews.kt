package com.example.projectpaba

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataNews (
    var id : Int,
    var title : String,
    var foto : String,
    var description : String,
    var date : String
) : Parcelable