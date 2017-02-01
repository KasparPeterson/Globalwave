package com.kasparpeterson.globalwave

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView

/**
 * Created by kaspar on 31/01/2017.
 */
class SpeechRecognitionActivity : AppCompatActivity(), RecognitionListener {

    val TAG = SpeechRecognitionActivity::class.java.simpleName

    val LISTENING_DELAY = 2000L

    var handler: Handler? = null

    var speechRecognizer: SpeechRecognizer? = null
    var recognizerIntent: Intent? = null

    var lastText = ""

    var resultTextView: TextView? = null
    var lastTextTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_speech_recognition)

        handler = Handler()

        resultTextView = findViewById(R.id.speech_recognition_result_text_view) as TextView
        lastTextTextView = findViewById(R.id.speech_recognition_last_text_text_view) as TextView

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        speechRecognizer!!.setRecognitionListener(this)

        recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        recognizerIntent!!.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en")
        recognizerIntent!!.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.packageName)
        recognizerIntent!!.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3)
        recognizerIntent!!.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH)

        startListening()
    }

    override fun onRmsChanged(rmsdB: Float) {
        Log.d(TAG, "onRmsChanged")
    }

    override fun onEndOfSpeech() {
        Log.d(TAG, "onEndOfSpeech")
    }

    override fun onReadyForSpeech(params: Bundle?) {
        Log.d(TAG, "onReadyForSpeech")
        resultTextView?.text = getString(R.string.speech_recognition_listening)
        lastTextTextView?.text = lastText
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
        resultTextView?.text = errorText
        Log.e(TAG, "onError, message: " + errorText)
        startListening()
    }

    override fun onResults(results: Bundle?) {
        if (results != null) {
            val matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            var text = ""
            for (match in matches) {
                text += match + "\n"
            }
            Log.e(TAG, "Result: " + text)
            resultTextView?.text = matches[0]
            lastText = matches[0]
        }
        startListening()
    }

    private fun startListening() {
        handler!!.postDelayed((Runnable {
            speechRecognizer!!.startListening(recognizerIntent)
        }), LISTENING_DELAY)
    }

}