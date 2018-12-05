package com.example.factoryfm.model

import android.arch.persistence.room.*
import android.os.Parcel
import android.os.Parcelable

@Entity(tableName = "album")
data class AlbumDetails(
    var name: String = "",
    @PrimaryKey
    var mbid: String = "",
    var artist: String = "",
    var image: List<FmImage> = arrayListOf(),
    var tracks: FmTrack = FmTrack(listOf())
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArrayList(FmImage),
        parcel.readParcelable(FmTrack::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(mbid)
        parcel.writeString(artist)
        parcel.writeTypedList(image)
        parcel.writeParcelable(tracks, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AlbumDetails> {
        override fun createFromParcel(parcel: Parcel): AlbumDetails {
            return AlbumDetails(parcel)
        }

        override fun newArray(size: Int): Array<AlbumDetails?> {
            return arrayOfNulls(size)
        }
    }
}

