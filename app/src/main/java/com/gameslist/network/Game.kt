package com.gameslist.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Game(
    var id: Int,
    var name: String,
    var description: String,
    var link: String,
    var img: String
) : Parcelable