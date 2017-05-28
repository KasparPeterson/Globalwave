package com.kasparpeterson.globalwave.recognition

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import com.google.gson.Gson
import com.kasparpeterson.globalwave.R
import com.kasparpeterson.globalwave.language.LanguageProcessor
import com.kasparpeterson.globalwave.spotify.SpotifyActivity
import com.kasparpeterson.globalwave.spotify.search.model.Item
import com.kasparpeterson.globalwave.spotify.search.SpotifySearchManager
import kotlinx.android.synthetic.main.activity_speech_recognition.*
import rx.Observer
import rx.android.schedulers.AndroidSchedulers

/**
 * Created by kaspar on 31/01/2017.
 */
class SpeechRecognitionActivity : SpotifyActivity(), RecognitionListener, Observer<Item?> {

    private val TAG = SpeechRecognitionActivity::class.java.simpleName

    private val LISTENING_DELAY = 2000L

    private val handler: Handler = Handler()
    private val languageProcessor = LanguageProcessor()

    var speechRecognizer: SpeechRecognizer? = null
    var recognizerIntent: Intent? = null

    var lastText = ""
    var spotifySearchManager: SpotifySearchManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_speech_recognition)
        spotifySearchManager = SpotifySearchManager()
    }

    override fun onSpotifyInitialised() {
        speechRecognizer = getRecognizer()
        recognizerIntent = getSpeechRecognizerIntent()
        startListening()
    }

    private fun getRecognizer(): SpeechRecognizer {
        val recognizer = SpeechRecognizer.createSpeechRecognizer(this)
        recognizer.setRecognitionListener(this)
        return recognizer
    }

    private fun getSpeechRecognizerIntent(): Intent {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en")
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.packageName)
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH)
        return intent
    }

    override fun onRmsChanged(rmsdB: Float) {
        Log.d(TAG, "onRmsChanged")
    }

    override fun onEndOfSpeech() {
        Log.d(TAG, "onEndOfSpeech")
    }

    override fun onReadyForSpeech(params: Bundle?) {
        Log.d(TAG, "onReadyForSpeech")
        speech_recognition_result_text_view.text = getString(R.string.speech_recognition_listening)
        speech_recognition_last_text_text_view.text = lastText
    }

    override fun onBufferReceived(buffer: ByteArray?) {
        Log.d(TAG, "onBufferReceived")
    }

    override fun onPartialResults(partialResults: Bundle?) {
        Log.d(TAG, "onPartialResults")
    }

    override fun onEvent(eventType: Int, params: Bundle?) {
        Log.d(TAG, "onEvent")
    }

    override fun onBeginningOfSpeech() {
        Log.d(TAG, "onBeginningOfSpeech")
    }

    override fun onError(error: Int) {
        val errorText = getErrorText(error)
        speech_recognition_result_text_view.text = errorText
        Log.e(TAG, "onError, message: " + errorText)
        startListening()
    }

    override fun onResults(results: Bundle?) {
        if (results != null)
            tryToRecognise(results)
        startListening()
    }

    private fun tryToRecognise(results: Bundle) {
        val matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        showBestMatch(matches)
        parseSpeech(matches[0])
        printMatches(matches)
        lastText = matches[0]
    }

    private fun printMatches(matches: List<String>) {
        var text = ""
        for (match in matches)
            text += match + "\n"
        Log.d(TAG, "Result: " + text)
    }

    private fun showBestMatch(matches: List<String>) {
        speech_recognition_result_text_view.text = matches[0]
    }

    private fun startListening() {
        speechRecognizer?.cancel()
        handler.postDelayed({
            speechRecognizer?.startListening(recognizerIntent)
        }, LISTENING_DELAY)
    }

    private fun parseSpeech(result: String) {
        Log.e(TAG, "parseSpeech: " + result)
        spotifySearchManager!!.getBestMatch(languageProcessor.process(result))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this)
    }

    override fun onError(e: Throwable?) {
        Log.e(TAG, "onError", e)
    }

    override fun onNext(item: Item?) {
        Log.d(TAG, "onNext, item: " + Gson().toJson(item))
        if (item != null && item.uri != null)
            play(item.uri!!)
    }

    override fun onCompleted() {}

}