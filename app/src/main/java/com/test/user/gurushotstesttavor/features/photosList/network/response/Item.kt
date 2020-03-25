package com.test.user.gurushotstesttavor.features.photosList.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Item (

    @SerializedName("id")
    @Expose
    var id: String? = null,

    @SerializedName("member_id")
    @Expose
    var memberId: String? = null,

    @SerializedName("title")
    @Expose
    var title: String? = null,

    @SerializedName("views")
    @Expose
    var views: Int? = 0,

    @SerializedName("adult")
    @Expose
    var adult: Boolean? = false,

    @SerializedName("event_id")
    @Expose
    var eventId: String? = null,

    @SerializedName("width")
    @Expose
    var width: Int? = null,

    @SerializedName("height")
    @Expose
    var height: Int? = null,

    @SerializedName("upload_date")
    @Expose
    var uploadDate: Int? = null,

    @SerializedName("labels")
    @Expose
    var labels: ArrayList<String>? = ArrayList(),

    @SerializedName("ratio")
    @Expose
    var ratio: Double? = null,

    @SerializedName("likes")
    @Expose
    var likes: Int? = null

)