package com.sports.musicplayer

import SongAdapter
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var listView: ListView
    private lateinit var playButton: Button
    private lateinit var pauseButton: Button
    private lateinit var stopButton: Button

    private val songs = listOf(
        Song("Song 1", R.raw.song1),
        Song("Song 2", R.raw.song2),
        Song("Song 3", R.raw.song3),
        Song("Song 4", R.raw.song4),
        Song("Song 5", R.raw.song5),
        Song("Song 6", R.raw.song6),
        Song("Song 7", R.raw.song7),
        Song("Song 8", R.raw.song8),
        Song("Song 9", R.raw.song9),
        Song("Song 10", R.raw.song10),
        Song("Song 11", R.raw.song11)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)
        playButton = findViewById(R.id.playButton)
        pauseButton = findViewById(R.id.pauseButton)
        stopButton = findViewById(R.id.stopButton)

        val adapter = SongAdapter(this, songs) { position ->
            playSong(position)
        }
        listView.adapter = adapter

        mediaPlayer = MediaPlayer()
        mediaPlayer.setOnCompletionListener {
            // Handle song completion if needed
        }

        playButton.setOnClickListener {
            if (!mediaPlayer.isPlaying) {
                mediaPlayer.start()
            }
        }

        pauseButton.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
            }
        }

        stopButton.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
                mediaPlayer.prepare()
            }
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            playSong(position)
        }
    }

    private fun playSong(position: Int) {
        val song = songs[position]
        try {
            mediaPlayer.reset()
            val assetFileDescriptor = resources.openRawResourceFd(song.resourceId)
            mediaPlayer.setDataSource(
                assetFileDescriptor.fileDescriptor,
                assetFileDescriptor.startOffset,
                assetFileDescriptor.length
            )
            mediaPlayer.prepare()
            mediaPlayer.start()
        } catch (e: Exception) {
            Toast.makeText(this, "Error playing song", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}
