package com.kasparpeterson.globalwave.language

/**
 * Created by kaspar on 26/02/2017.
 */
class ProcessorResult(val searchType: SearchType, val name: String) {

    enum class SearchType(type: String) {
        TRACK("track"),
        ALBUM("album"),
        ARTIST("album"),
        UNKNOWN("unknown");
    }

}