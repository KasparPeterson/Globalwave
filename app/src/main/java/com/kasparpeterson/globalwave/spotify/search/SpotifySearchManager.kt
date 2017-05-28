package com.kasparpeterson.globalwave.spotify.search

import android.util.Log
import com.google.gson.Gson
import com.kasparpeterson.globalwave.language.LanguageProcessor
import com.kasparpeterson.globalwave.language.ProcessorResult
import com.kasparpeterson.globalwave.spotify.search.model.Item
import com.kasparpeterson.globalwave.spotify.search.model.SearchResult
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

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

    fun search(processorResult: ProcessorResult): Observable<SearchResult> {
        Log.d(TAG, "search, processorResult: " + processorResult)
        return service.search(processorResult.name, ProcessorResult.SearchType.ARTIST.type)
    }

    fun getBestMatch(processorResult: ProcessorResult): Observable<Item> {
        return Observable.just(processorResult)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap { processorResult -> search(processorResult) }
                .map { searchResult -> getBestMatch(searchResult) }
    }

    private fun getBestMatch(result: SearchResult): Item? {
        var mostPopularInt = 0
        var mostPopularItem: Item? = null

        if (result.artists != null && result.artists!!.items != null) {
            for (item in result.artists!!.items!!) {
                if (item.popularity != null && item.popularity!! > mostPopularInt) {
                    mostPopularInt = item.popularity!!
                    mostPopularItem = item
                }
            }
        }

        return mostPopularItem
    }

}