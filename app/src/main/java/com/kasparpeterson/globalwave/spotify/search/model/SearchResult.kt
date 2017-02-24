package com.kasparpeterson.globalwave.spotify.search.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SearchResult {

    @SerializedName("artists")
    @Expose
    var artists: Artists? = null

}