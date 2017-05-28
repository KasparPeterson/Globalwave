package com.kasparpeterson.globalwave.language

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by kaspar on 26/02/2017.
 */
class LanguageProcessorTest {

    private val languageProcessor: LanguageProcessor = LanguageProcessor()

    @Test
    fun proccess_empty() {
        val result = languageProcessor.process("")
        assertEquals(ProcessorResult.SearchType.UNKNOWN, result.searchType)
        assertEquals("", result.name)
    }

    @Test
    fun proccess_play_empty() {
        val result = languageProcessor.process("play")
        assertEquals(ProcessorResult.SearchType.UNKNOWN, result.searchType)
        assertEquals("", result.name)
    }

    @Test
    fun searchType_UNKNOWN() {
        val result = languageProcessor.process("play my mock")
        assertEquals(ProcessorResult.SearchType.UNKNOWN, result.searchType)
        assertEquals("my mock", result.name)
    }

    @Test
    fun searchType_TRACK() {
        val result = languageProcessor.process("play track my mock song")
        assertEquals(ProcessorResult.SearchType.TRACK, result.searchType)
        assertEquals("my mock song", result.name)
    }

    @Test
    fun searchType_TRACK_song() {
        val result = languageProcessor.process("play song my mock song")
        assertEquals(ProcessorResult.SearchType.TRACK, result.searchType)
        assertEquals("my mock song", result.name)
    }

    @Test
    fun searchType_ALBUM() {
        val result = languageProcessor.process("play album my mock")
        assertEquals(ProcessorResult.SearchType.ALBUM, result.searchType)
        assertEquals("my mock", result.name)
    }

    @Test
    fun searchType_ARTIST() {
        val result = languageProcessor.process("play artist my mock")
        assertEquals(ProcessorResult.SearchType.ARTIST, result.searchType)
        assertEquals("my mock", result.name)
    }

    @Test
    fun searchType_ARTIST2() {
        val result = languageProcessor.process("play artist sting")
        assertEquals(ProcessorResult.SearchType.ARTIST, result.searchType)
        assertEquals("sting", result.name)
    }

    @Test
    fun searchType_withOutPlay_UNKNOWN() {
        val result = languageProcessor.process("my mock name")
        assertEquals(ProcessorResult.SearchType.UNKNOWN, result.searchType)
        assertEquals("my mock name", result.name)
    }

    @Test
    fun searchType_withOutPlay_TRACK() {
        val result = languageProcessor.process("track my mock name")
        assertEquals(ProcessorResult.SearchType.TRACK, result.searchType)
        assertEquals("my mock name", result.name)
    }

    @Test
    fun searchType_withOutPlay_TRACK_song() {
        val result = languageProcessor.process("song my mock name")
        assertEquals(ProcessorResult.SearchType.TRACK, result.searchType)
        assertEquals("my mock name", result.name)
    }

    @Test
    fun searchType_withOutPlay_ARTIST() {
        val result = languageProcessor.process("artist my mock name")
        assertEquals(ProcessorResult.SearchType.ARTIST, result.searchType)
        assertEquals("my mock name", result.name)
    }

    @Test
    fun searchType_withOutPlay_ALBUM() {
        val result = languageProcessor.process("album my mock name")
        assertEquals(ProcessorResult.SearchType.ALBUM, result.searchType)
        assertEquals("my mock name", result.name)
    }

}