package com.kasparpeterson.globalwave.spotify.search.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Artists {

    @SerializedName("href")
    @Expose
    var href: String? = null

    @SerializedName("items")
    @Expose
    var items: List<Item>? = null

    @SerializedName("limit")
    @Expose
    var limit: Int? = null

    @SerializedName("offset")
    @Expose
    var offset: Int? = null

    @SerializedName("total")
    @Expose
    var total: Int? = null

}