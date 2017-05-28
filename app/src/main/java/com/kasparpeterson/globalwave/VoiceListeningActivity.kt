package com.kasparpeterson.globalwave

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import edu.cmu.pocketsphinx.*
import java.io.File

/**
 * Created by kaspar on 27/01/2017.
 */
@Deprecated("This is just a test class to see PocketSphinx capabilities")
class VoiceListeningActivity: AppCompatActivity(), RecognitionListener {

    private val TAG = VoiceListeningActivity::class.java.simpleName
    private val KWS_SEARCH = "wakeup"
    private val KEYPHRASE = "hello"
    private val PHONE_SEARCH = "phones"

    var speechRecognizer: SpeechRecognizer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voice_listening)
        val assets = Assets(this)
        val assetDir = assets.syncAssets()
        speechRecognizer = getRecognizer(assetDir)
    }

    private fun getRecognizer(assetsDir: File): SpeechRecognizer {
        val speechRecognizer = getDefaultRecognizer(assetsDir)
        speechRecognizer.addListener(this)
        speechRecognizer.addKeyphraseSearch(KWS_SEARCH, KEYPHRASE)
        speechRecognizer.addAllphoneSearch(PHONE_SEARCH, getPhoneticModel(assetsDir))
        speechRecognizer.startListening(KWS_SEARCH)
        return speechRecognizer
    }

    private fun getDefaultRecognizer(assetsDir: File): SpeechRecognizer {
        return SpeechRecognizerSetup.defaultSetup()
                .setAcousticModel(File(assetsDir, "en-us-ptm"))
                .setDictionary(File(assetsDir, "cmudict-en-us.dict"))
                .setRawLogDir(assetsDir).setKeywordThreshold(1e-20f)
                .recognizer
    }

    private fun getPhoneticModel(assetsDir: File): File {
        return File(assetsDir, "en-phone.dmp")
    }

    // Pocket Sphinx functions
    override fun onPartialResult(p0: Hypothesis?) {
        Log.d(TAG, "onPartialResult, hypstr: " + p0?.hypstr)
        val result = p0?.hypstr
        if (result.equals(KEYPHRASE)) {
            speechRecognizer!!.startListening(PHONE_SEARCH, 100000)
        }
    }

    override fun onTimeout() {
        Log.d(TAG, "onTimeout")
    }

    override fun onEndOfSpeech() {
        Log.d(TAG, "onEndOfSpeech")
    }

    override fun onResult(p0: Hypothesis?) {
        Log.d(TAG, "onResult, hypst: " + p0?.hypstr)
    }

    override fun onBeginningOfSpeech() {
        Log.d(TAG, "onBeginningOfSpeech")
    }

    override fun onError(p0: Exception?) {
        Log.d(TAG, "onError")
    }
}