package com.example.factoryfm.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class FmImage(
    @SerializedName("#text") val url: String,
    val size: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(url)
        parcel.writeString(size)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FmImage> {
        override fun createFromParcel(parcel: Parcel): FmImage {
            return FmImage(parcel)
        }

        override fun newArray(size: Int): Array<FmImage?> {
            return arrayOfNulls(size)
        }
    }
}