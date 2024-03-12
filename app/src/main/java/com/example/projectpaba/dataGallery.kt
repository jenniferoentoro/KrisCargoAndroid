package com.example.projectpaba

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class dataGallery(
    var title : String,
    var path : String,
    var isi : String,
    var kategori: String,
    var folder_id: Int
) : Parcelable
