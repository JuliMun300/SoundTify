package com.example.soundtify

import android.os.Parcel
import android.os.Parcelable

data class Cancion(
    var titulo: String?,
    var autor: String?,
    var cancion: Int,
    var imagen: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(titulo)
        parcel.writeString(autor)
        parcel.writeInt(cancion)
        parcel.writeString(imagen)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Cancion> {
        override fun createFromParcel(parcel: Parcel): Cancion {
            return Cancion(parcel)
        }

        override fun newArray(size: Int): Array<Cancion?> {
            return arrayOfNulls(size)
        }
    }
}


