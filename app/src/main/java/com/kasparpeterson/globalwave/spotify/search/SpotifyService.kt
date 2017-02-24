package com.kasparpeterson.globalwave.spotify.search

import com.kasparpeterson.globalwave.spotify.search.model.SearchResult
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/**
 * Created by kaspar on 24/02/2017.
 */
interface SpotifyService {

    @GET("search")
    fun search(@Query("q") searchTerm: String,
               @Query("type") type: String)
            : Observable<SearchResult>

}