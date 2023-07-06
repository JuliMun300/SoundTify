package com.example.soundtify

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.widget.Toast

class MusicService : Service() {
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val cancion = intent?.getParcelableExtra<Cancion>("CancionService")
        if (cancion != null) {
            mediaPlayer = MediaPlayer.create(this, cancion.cancion)
        }
        mediaPlayer.start()

        return START_STICKY

    }

    override fun onDestroy() {
        super.onDestroy()
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            mediaPlayer.seekTo(0)
        }
    }


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}