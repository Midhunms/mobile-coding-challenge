package audiobooks.codingchallenge.data.dto

import android.os.Parcel
import android.os.Parcelable

data class ResponseData(
    val id: Int,
    val total: Int,
    val name: String,
    val has_next: Boolean,
    val podcasts: ArrayList<PodCastData>,

    )
data class PodCastData(
    var isFavourite: Boolean,
    val id: String?,
    val thumbnail: String?,
    val title: String?,
    val publisher: String?,
    val description: String?,

    ):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (isFavourite) 1 else 0)
        parcel.writeString(id)
        parcel.writeString(thumbnail)
        parcel.writeString(title)
        parcel.writeString(publisher)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PodCastData> {
        override fun createFromParcel(parcel: Parcel): PodCastData {
            return PodCastData(parcel)
        }

        override fun newArray(size: Int): Array<PodCastData?> {
            return arrayOfNulls(size)
        }
    }
}