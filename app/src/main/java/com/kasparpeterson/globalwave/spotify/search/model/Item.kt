package com.kasparpeterson.globalwave.spotify.search.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Item {

    @SerializedName("external_urls")
    @Expose
    var externalUrls: ExternalUrls? = null
    @SerializedName("genres")
    @Expose
    var genres: List<Any>? = null
    @SerializedName("href")
    @Expose
    var href: String? = null
    @SerializedName("id")
    @Expose
    var id: String? = null
    @SerializedName("images")
    @Expose
    var images: List<Image>? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("popularity")
    @Expose
    var popularity: Int? = null
    @SerializedName("type")
    @Expose
    var type: String? = null
    @SerializedName("uri")
    @Expose
    var uri: String? = null
}