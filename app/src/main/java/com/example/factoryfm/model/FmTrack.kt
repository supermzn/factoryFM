package com.example.factoryfm.model

import android.os.Parcel
import android.os.Parcelable

data class FmTrack (val track: List<Track>) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.createTypedArrayList(Track))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(track)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FmTrack> {
        override fun createFromParcel(parcel: Parcel): FmTrack {
            return FmTrack(parcel)
        }

        override fun newArray(size: Int): Array<FmTrack?> {
            return arrayOfNulls(size)
        }
    }
}