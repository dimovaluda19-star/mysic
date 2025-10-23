package com.example.mysic;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private Button toggleButton, pauseButton, playButton, rewindButton, forwardButton;
    private boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toggleButton = findViewById(R.id.btn_toggle_music);
        pauseButton = findViewById(R.id.btn_pause);
        playButton = findViewById(R.id.btn_play);
        rewindButton = findViewById(R.id.btn_rew);
        forwardButton = findViewById(R.id.btn_ff);

        // Создаем MediaPlayer
        mediaPlayer = MediaPlayer.create(this, R.raw.music);

        // Включение/выключение
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPlaying) {
                    mediaPlayer.start();
                    isPlaying = true;
                    toggleButton.setText("Выключить музыку");
                } else {
                    mediaPlayer.pause();
                    isPlaying = false;
                    toggleButton.setText("Включить музыку");
                }
            }
        });

        // Пауза
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
            }
        });

        // Плей (запуск с начала)
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    mediaPlayer.seekTo(0);      // Перемещаемся в начало
                    mediaPlayer.start();        // Запускаем
                    isPlaying = true;
                    toggleButton.setText("Выключить музыку");
                }
            }
        });

        // Перемотка назад
        rewindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    int currentPosition = mediaPlayer.getCurrentPosition();
                    int rewindPosition = currentPosition - 10000; // 10 секунд назад
                    if (rewindPosition < 0) rewindPosition = 0;
                    mediaPlayer.seekTo(rewindPosition);
                }
            }
        });

        // Перемотка вперед
        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    int currentPosition = mediaPlayer.getCurrentPosition();
                    int forwardPosition = currentPosition + 10000; // 10 секунд вперед
                    if (forwardPosition > mediaPlayer.getDuration()) {
                        forwardPosition = mediaPlayer.getDuration();
                    }
                    mediaPlayer.seekTo(forwardPosition);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
