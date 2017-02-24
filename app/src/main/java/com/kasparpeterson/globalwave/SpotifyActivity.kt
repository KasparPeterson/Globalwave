package com.kasparpeterson.globalwave

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.spotify.sdk.android.authentication.AuthenticationResponse
import com.spotify.sdk.android.player.*

/**
 * Created by kaspar on 04/02/2017.
 */
abstract class SpotifyActivity: AppCompatActivity(), Player.NotificationCallback, ConnectionStateCallback {

    private val TAG = SpotifyActivity::class.java.simpleName

    private val REQUEST_CODE = 1337

    private val CLIENT_ID = "7ae219a9b2854c35942e8273f25989de"
    private val REDIRECT_URI = "kaspar://spotifylogin"

    private var player: Player? = null

    abstract fun onSpotifyInitialised()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        openLogin()
    }

    override fun onDestroy() {
        super.onDestroy()
        Spotify.destroyPlayer(this)
    }

    private fun openLogin() {
        val builder = AuthenticationRequest.Builder(
                CLIENT_ID,
                AuthenticationResponse.Type.TOKEN,
                REDIRECT_URI)
        builder.setScopes(arrayOf("user-read-private", "streaming"))
        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, builder.build())
    }

    private fun onPlayerInitialized(spotifyPlayer: SpotifyPlayer) {
        player = spotifyPlayer
        player!!.addConnectionStateCallback(this@SpotifyActivity)
        player!!.addNotificationCallback(this@SpotifyActivity)
    }

    protected fun play(artist: String, song: String) {
        player!!.playUri(object : Player.OperationCallback {
            override fun onSuccess() {
                Log.e(TAG, "playUri onSuccess()")
            }

            override fun onError(p0: Error?) {
                Log.e(TAG, "playUri onError()")
            }

        }, "spotify:track:3vBJYnujT3yxLjLEG1jtDS", 0, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.e(TAG, "onActivityResult, requestCode: " + requestCode + ", resultCode: " + resultCode)

        if (requestCode == REQUEST_CODE) {
            val response = AuthenticationClient.getResponse(resultCode, data)

            Log.e(TAG, "response: " + response +
                    "\nerror: " + response.error +
                    "\ntoken: " + response.accessToken)

            if (response.type == AuthenticationResponse.Type.TOKEN) {
                val playerConfig = Config(this, response.accessToken, CLIENT_ID)
                Spotify.getPlayer(playerConfig, this, object : SpotifyPlayer.InitializationObserver {
                    override fun onInitialized(player: SpotifyPlayer?) {
                        Log.e(TAG, "onInitialized");
                        if (player != null) {
                            onPlayerInitialized(player)
                        }
                    }

                    override fun onError(error: Throwable?) {
                        Log.e(TAG, "Couldn't initialize player, message: " + error?.message)
                    }
                })
            }
        }
    }

    override fun onPlaybackError(p0: Error?) {
        Log.e(TAG, "onPlaybackError()")
    }

    override fun onPlaybackEvent(p0: PlayerEvent?) {
        Log.e(TAG, "onPlaybackEvent()")
    }

    override fun onConnectionMessage(p0: String?) {
        Log.e(TAG, "onConnectionMessage()")
    }

    override fun onLoginFailed(p0: Error?) {
        Log.e(TAG, "onLoginFailed()")
    }

    override fun onLoggedOut() {
        Log.e(TAG, "onLoggedOut()")
    }

    override fun onLoggedIn() {
        Log.e(TAG, "onLoggedIn()")
        onSpotifyInitialised()
    }

    override fun onTemporaryError() {
        Log.e(TAG, "onTemporaryError()")
    }

}