package com.example.media_player;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button rewind,play,forward;
    MediaPlayer mediaplayer;
    double startTime=0;
    double endTime = 0;
    Handler handler = new Handler();
    SeekBar seekBar;
    TextView total,current;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        rewind = findViewById(R.id.button);
        play = findViewById(R.id.button2);
        forward = findViewById(R.id.button3);
        seekBar = findViewById(R.id.seekBar);
        current = findViewById(R.id.currenttime);
        total = findViewById(R.id.totaltime);

        mediaplayer = MediaPlayer.create(this, R.raw.audo1);
        mediaplayer.setOnPreparedListener(mp->{
            endTime = mediaplayer.getDuration();
            seekBar.setMax((int) endTime);
            total.setText(formatTime(endTime));
        });

        play.setOnClickListener(v->{
            if(mediaplayer.isPlaying()){
                mediaplayer.pause();
                play.setText("Play");
            }else {
                mediaplayer.start();
                play.setText("Pause");
                updateSeekBar();
            }
        });

        rewind.setOnClickListener(v->{
            int cur = mediaplayer.getCurrentPosition();
            int neww = Math.max(cur-10000,0);
            mediaplayer.seekTo(neww);
        });
        forward.setOnClickListener(v -> {
            int currentPosition = mediaplayer.getCurrentPosition();
            int newPosition = Math.min(currentPosition + 10000, (int) endTime); // Forward 10 seconds
            mediaplayer.seekTo(newPosition);
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaplayer.seekTo(seekBar.getProgress());
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaplayer.seekTo(progress);
                }
            }
        });
    }
    private void updateSeekBar() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startTime = mediaplayer.getCurrentPosition();
                seekBar.setProgress((int) startTime);
                current.setText(formatTime(startTime));
                handler.postDelayed(this, 1000);
            }
        }, 1000);
    }
    private String formatTime(double time) {
        int minutes = (int) (time / 1000) / 60;
        int seconds = (int) (time / 1000) % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}