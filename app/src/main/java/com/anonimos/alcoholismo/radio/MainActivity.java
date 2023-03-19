package com.anonimos.alcoholismo.radio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer player;
    private String url = "https://stream-51.zeno.fm/2azmr4qh31zuv";
    private boolean isPlaying = false;
    private boolean isMuted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeMediaPlayer();
        startPlaying();
        Button pauseButton = findViewById(R.id.button1);
        Button muteButton = findViewById(R.id.button2);
        ImageButton button = findViewById(R.id.button3);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Url_Group = "https://chat.whatsapp.com/JpzstP8gJpVGZB24ezJWAj";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Url_Group));
                startActivity(intent);
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    pausePlaying();
                } else {
                    resumePlaying();
                }
            }
        });

        muteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMuted) {
                    unmutePlaying();
                } else {
                    mutePlaying();
                }
            }
        });
    }
    private void initializeMediaPlayer() {
        player = new MediaPlayer();

        player.setOnBufferingUpdateListener(new OnBufferingUpdateListener() {

            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                Log.i("Buffering", "" + percent);
            }
        });
    }

    public void startPlaying() {

        try {

            Toast.makeText(getApplicationContext(),
                    "Conectando con la radio, espere unos segundos...",
                    Toast.LENGTH_LONG).show();

            player.reset();
            player.setDataSource(url);

            player.setOnPreparedListener(new OnPreparedListener() {

                public void onPrepared(MediaPlayer mp) {

                    player.start();
                    isPlaying = true;

                }
            });
            player.prepareAsync();

        } catch (IllegalArgumentException | SecurityException
                 | IllegalStateException | IOException e) {
            Toast.makeText(getApplicationContext(),
                    "Error al conectar con la radio", Toast.LENGTH_LONG).show();
        }
    }
    public void pausePlaying() {
        player.pause();
        isPlaying = false;
    }

    public void resumePlaying() {
        player.start();
        isPlaying = true;
    }

    public void mutePlaying() {
        if (player.isPlaying()) {
            player.setVolume(0, 0);
        }
        isMuted = true;
    }

    public void unmutePlaying() {
        if (player.isPlaying()) {
            player.setVolume(1, 1);
        }
        isMuted = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
            player = null;
        }
    }
}
