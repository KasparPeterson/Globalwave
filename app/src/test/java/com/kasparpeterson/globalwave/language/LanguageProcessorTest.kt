package com.kasparpeterson.globalwave.language

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by kaspar on 26/02/2017.
 */
class LanguageProcessorTest {

    private val languageProcessor: LanguageProcessor = LanguageProcessor()

    @Test
    fun searchType_UNKNOWN() {
        val result = languageProcessor.process("play my mock")
        assertEquals(result.searchType, ProcessorResult.SearchType.UNKNOWN)
        assertEquals("my mock", result.name)
    }

    @Test
    fun searchType_TRACK() {
        val result = languageProcessor.process("play song my mock song")
        assertEquals(result.searchType, ProcessorResult.SearchType.TRACK)
        assertEquals("my mock song", result.name)
    }

    @Test
    fun searchType_ALBUM() {
        val result = languageProcessor.process("play album my mock")
        assertEquals(result.searchType, ProcessorResult.SearchType.ALBUM)
        assertEquals("my mock", result.name)
    }

    @Test
    fun searchType_ARTIST() {
        val result = languageProcessor.process("play artist my mock")
        assertEquals(result.searchType, ProcessorResult.SearchType.ARTIST)
        assertEquals("my mock", result.name)
    }

}