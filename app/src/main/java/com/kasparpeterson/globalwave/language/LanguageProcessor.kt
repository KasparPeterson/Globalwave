package com.kasparpeterson.globalwave.language

/**
 * Created by kaspar on 25/02/2017.
 */

class LanguageProcessor {

    fun process(search: String): ProcessorResult {
        val words = getSearchWords(search)
        val searchType = getSearchType(words)
        val wordsWithoutType = getWordsWithoutType(words, searchType)
        val name = getNameIfNotEmpty(wordsWithoutType)
        return ProcessorResult(searchType, name)
    }

    private fun getSearchWords(search: String): List<String> {
        val words = search.split(" ")
        if (words[0] == "play")
            return words.subList(1, words.size)
        return words
    }

    private fun getSearchType(words: List<String>): ProcessorResult.SearchType {
        if (words.isNotEmpty())
            return ProcessorResult.SearchType.getSearchType(words[0])
        return ProcessorResult.SearchType.UNKNOWN
    }

    private fun getWordsWithoutType(words: List<String>,
                                    searchType: ProcessorResult.SearchType): List<String> {
        if (searchType != ProcessorResult.SearchType.UNKNOWN)
            return words.subList(1, words.size)
        return words
    }

    private fun getNameIfNotEmpty(words: List<String>): String {
        if (words.isNotEmpty())
            return getName(words)
        return ""
    }

    private fun getName(words: List<String>): String {
        var name = ""
        for (i in words.indices)
            name += getWord(words, i)
        return name
    }

    private fun getWord(words: List<String>, i: Int): String {
        if (i < words.size - 1)
            return words[i] + " "
        return words[i]
    }

}
