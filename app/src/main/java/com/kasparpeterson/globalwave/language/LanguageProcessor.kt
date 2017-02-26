package com.kasparpeterson.globalwave.language

/**
 * Created by kaspar on 25/02/2017.
 */

class LanguageProcessor {

    public fun process(search: String): ProcessorResult {
        var searchType = ProcessorResult.SearchType.UNKNOWN
        var name = ""

        var words = search.split(" ")
        if (words[0] == "play") {
            words = words.subList(1, words.size)
        }

        if (words[0] == "song") {
            searchType = ProcessorResult.SearchType.TRACK
        } else if (words[0] == "album") {
            searchType = ProcessorResult.SearchType.ALBUM
        } else if (words[0] == "artist") {
            searchType = ProcessorResult.SearchType.ARTIST
        }

        if (searchType != ProcessorResult.SearchType.UNKNOWN) {
            words = words.subList(1, words.size)
        }

        if (words.size > 1) {
            var i = 0
            for (word in words) {
                name += word
                i++
                if (i < words.size) {
                    name += " "
                }
            }
        }

        return ProcessorResult(searchType, name)
    }

}
