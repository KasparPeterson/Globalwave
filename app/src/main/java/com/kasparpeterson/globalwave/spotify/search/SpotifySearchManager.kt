package com.kasparpeterson.globalwave.spotify.search

import android.util.Log
import com.kasparpeterson.globalwave.spotify.search.model.SearchResult
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable

/**
 * Created by kaspar on 24/02/2017.
 */
class SpotifySearchManager {

    private val TAG = SpotifySearchManager::class.java.simpleName

    private val BASE_URL = "https://api.spotify.com/v1/"
    private val service: SpotifyService

    init {
        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

        service = retrofit.create(SpotifyService::class.java)
    }

    fun search(searchTerm: String): Observable<SearchResult> {
        Log.e(TAG, "search, searchTerm: " + searchTerm)
        if (searchTerm != null && searchTerm.length > 1) {
            return service.search(searchTerm.substring(1, searchTerm.length), searchTerm.substring(0, 1))
        } else {
            return Observable.error(Exception("Search term is too short"))
        }
    }

}