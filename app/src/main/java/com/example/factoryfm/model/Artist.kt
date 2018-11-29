package com.example.factoryfm.model

import android.os.Parcel
import android.os.Parcelable


data class Artist(val name: String, val mbid: String, val image: List<FmImage>) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArrayList(FmImage)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(mbid)
        parcel.writeTypedList(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField val CREATOR = object : Parcelable.Creator<Artist> {
            override fun createFromParcel(parcel: Parcel): Artist = Artist(parcel)


            override fun newArray(size: Int): Array<Artist?> = arrayOfNulls(size)
        }
    }
}
