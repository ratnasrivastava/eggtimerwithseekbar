package com.ratnasrivastava.eggtimer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView timerTextView;
    SeekBar seekBar;
    Button goAndStopButton;
    private CountDownTimer countdownTimer;
    private boolean counterIsActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        goAndStopButton = findViewById(R.id.goAndStopButton);
        seekBar = findViewById(R.id.seekbar);
        seekBar.setMax(3599);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(final SeekBar seekBar, int i, boolean b) {
                updateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void updateTimer(int i) {
            int minutes = i / 60;
            int seconds = i % 60;

            if (minutes < 10 && seconds < 10)
                timerTextView.setText("0" + minutes + ":" + "0" + seconds);
            else if (minutes < 10)
                timerTextView.setText("0" + minutes + ":" + seconds);
            else if (seconds < 10)
                timerTextView.setText("" + minutes + ":" + "0" + seconds);
            else
                timerTextView.setText(minutes + ":" + seconds);


    }

    private void resetTimer() {
        countdownTimer.cancel();
        counterIsActive = false;
        seekBar.setEnabled(true);
        goAndStopButton.setText("Go!!");
    }

    public void startCountDownTimer(View view) {
        if (counterIsActive) {
            resetTimer();
        } else if (!counterIsActive) {
            counterIsActive = true;
            seekBar.setEnabled(false);
            goAndStopButton.setText("Stop !!");
            countdownTimer = new CountDownTimer(seekBar.getProgress() * 1000 + 100, 1000) {
                long value1 = 0;
                long value2 = 0;

                @Override
                public void onTick(long l) {
                    l = l / 1000;
                    value1 = l / 60;
                    value2 = l % 60;
                    if (value1 < 10 && value2 < 10)
                        timerTextView.setText("0" + value1 + ":" + "0" + value2);
                    else if (value1 < 10)
                        timerTextView.setText("0" + value1 + ":" + value2);
                    else if (value2 < 10)
                        timerTextView.setText("" + value1 + ":" + "0" + value2);
                    else
                        timerTextView.setText(value1 + ":" + value2);
                }

                @Override
                public void onFinish() {
                    MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.airhorn);
                    mediaPlayer.start();
                    resetTimer();
                }
            }.start();
        }
    }
}