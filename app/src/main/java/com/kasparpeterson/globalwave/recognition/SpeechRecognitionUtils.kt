package com.kasparpeterson.globalwave.recognition

import android.speech.SpeechRecognizer

/**
 * Created by kaspar on 01/02/2017.
 */
fun getErrorText(errorCode: Int): String {
    val message: String
    when (errorCode) {
        SpeechRecognizer.ERROR_AUDIO -> message = "Audio recording error"
        SpeechRecognizer.ERROR_CLIENT -> message = "Client side error"
        SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> message = "Insufficient permissions"
        SpeechRecognizer.ERROR_NETWORK -> message = "Network error"
        SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> message = "Network timeout"
        SpeechRecognizer.ERROR_NO_MATCH -> message = "No match"
        SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> message = "RecognitionService busy"
        SpeechRecognizer.ERROR_SERVER -> message = "error from server"
        SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> message = "No speech input"
        else -> message = "Didn't understand, please try again."
    }
    return message
}