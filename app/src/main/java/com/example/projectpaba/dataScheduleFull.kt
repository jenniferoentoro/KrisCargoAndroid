package com.example.projectpaba

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class dataScheduleFull (
    var title : String,
    var kotaAsal : String,
    var kotaTujuan : String,
    var closeDate : String,
    var duration : String,
    var eta : String,
    var id : Number
) : Parcelable