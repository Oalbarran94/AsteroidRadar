package com.example.asteroidradarapp.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AsteroidData (

    val element_count: String) : Parcelable