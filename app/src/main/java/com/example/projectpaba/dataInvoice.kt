package com.example.projectpaba


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class dataInvoice(
    var bayar : String,
    var no_invoice : String,
    var tanggal_bayar : String,
    var tgl_invoice: String,
    var tgl_jatuh_tempo : String,
    var total_tagihan: String
) : Parcelable
