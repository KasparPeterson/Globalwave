package com.kasparpeterson.globalwave.language

/**
 * Created by kaspar on 26/02/2017.
 */
class ProcessorResult(val searchType: SearchType, val name: String) {

    enum class SearchType(val type: String) {
        TRACK("track"),
        ALBUM("album"),
        ARTIST("artist"),
        UNKNOWN("unknown");

        companion object {
            fun getSearchType(type: String): SearchType {
                if (type.toLowerCase() == "song") return TRACK

                return SearchType.values().firstOrNull {
                    type.toLowerCase() == it.type
                } ?: UNKNOWN
            }
        }
    }
}