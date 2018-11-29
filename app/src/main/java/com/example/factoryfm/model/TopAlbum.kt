package com.example.factoryfm.model

import android.os.Parcel
import android.os.Parcelable

data class TopAlbum(
    val name: String,
    val playcount: Int,
    val mbid: String,
    val url: String,
    val artist: Artist,
    val image: List<FmImage>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Artist::class.java.classLoader),
        parcel.createTypedArrayList(FmImage)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(playcount)
        parcel.writeString(mbid)
        parcel.writeString(url)
        parcel.writeParcelable(artist, flags)
        parcel.writeTypedList(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TopAlbum> {
        override fun createFromParcel(parcel: Parcel): TopAlbum {
            return TopAlbum(parcel)
        }

        override fun newArray(size: Int): Array<TopAlbum?> {
            return arrayOfNulls(size)
        }
    }
}