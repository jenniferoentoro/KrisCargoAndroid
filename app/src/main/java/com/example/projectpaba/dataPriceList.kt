package com.example.projectpaba

import android.os.Parcel
import android.os.Parcelable

data class dataPriceList(
    var POD : String,
    var POL : String,
    var PPN : Number,
    var berlaku_sejak : String,
    var commodity : String,
    var harga : Number,
    var jenis : String,
    var no_joa : String,
    var rute_dari : String,
    var rute_ke : String,
    var term : String
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        TODO("PPN"),
        parcel.readString().toString(),
        parcel.readString().toString(),
        TODO("harga"),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(POD)
        parcel.writeString(POL)
        parcel.writeString(berlaku_sejak)
        parcel.writeString(commodity)
        parcel.writeString(jenis)
        parcel.writeString(no_joa)
        parcel.writeString(rute_dari)
        parcel.writeString(rute_ke)
        parcel.writeString(term)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<dataPriceList> {
        override fun createFromParcel(parcel: Parcel): dataPriceList {
            return dataPriceList(parcel)
        }

        override fun newArray(size: Int): Array<dataPriceList?> {
            return arrayOfNulls(size)
        }
    }
}
