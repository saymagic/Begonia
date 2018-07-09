package cn.saymagic.begonia.pojo

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable

class DataSourceParam(val type: String, private val bundle: Bundle = Bundle()) : Parcelable {

    constructor(parcel: Parcel) : this(parcel.readString(), parcel.readBundle())

    fun getString(key: String): String? = bundle.getString(key)

    fun putString(key: String, value: String): DataSourceParam = this.apply {
        bundle.putString(key, value)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(type)
        parcel.writeBundle(bundle)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DataSourceParam> {
        override fun createFromParcel(parcel: Parcel): DataSourceParam {
            return DataSourceParam(parcel)
        }

        override fun newArray(size: Int): Array<DataSourceParam?> {
            return arrayOfNulls(size)
        }
    }

}