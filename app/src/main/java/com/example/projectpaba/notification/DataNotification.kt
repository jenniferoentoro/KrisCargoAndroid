package com.example.projectpaba.notification

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataNotification(
    var id : Int,
    var title : String,
    var description : String,
    var date : String
) : Parcelable
