package com.test.user.gurushotstesttavor.features.photosList.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PhotosListResponse (

    @SerializedName("items")
    @Expose
    var items: ArrayList<Item>? = ArrayList(),

    @SerializedName("success")
    @Expose
    var success: Boolean? = false
)