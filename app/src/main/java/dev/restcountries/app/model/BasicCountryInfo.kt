package dev.restcountries.app.model

import android.os.Parcel
import android.os.Parcelable


data class BasicCountryInfo(

    val name: String,
    val alpha2Code: String,
    val population: Int,
    val area: Double,
    val nativeName: String
) : Parcelable {

    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readInt(),
        source.readDouble(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(name)
        writeString(alpha2Code)
        writeInt(population)
        writeDouble(area)
        writeString(nativeName)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<BasicCountryInfo> = object : Parcelable.Creator<BasicCountryInfo> {
            override fun createFromParcel(source: Parcel): BasicCountryInfo = BasicCountryInfo(source)
            override fun newArray(size: Int): Array<BasicCountryInfo?> = arrayOfNulls(size)
        }
    }
}