package com.galacticCat.chatbleu.tools.Timer;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.galacticCat.chatbleu.R;

import java.util.Locale;

public class Pop_up_activity extends AppCompatActivity implements TimerI{
    private TextView textViewCountDown;
    private EditText mEditText;

    private Button mBtnStartPause;
    private Button mBtnReset;
    private Button mBtnSet;

    private long startTime ;
    private long timeLeft = startTime;

    private boolean timerRunnig;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);

        DisplayMetrics window = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(window);

        int width = window.widthPixels;
        int heigt = window.heightPixels;
        getWindow().setLayout((int) (width * 0.85), (int) (heigt * 0.50));
        //Timer

        textViewCountDown = findViewById(R.id.timerText);
        mEditText = findViewById(R.id.edit_text_input);

        mBtnSet = findViewById(R.id.set_btn);
        mBtnStartPause = findViewById(R.id.start_pause_btn);
        mBtnReset = findViewById(R.id.reset);


        mBtnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = mEditText.getText().toString();
                if(input.length()==0){
                    Toast.makeText(Pop_up_activity.this , getResources().getString(R.string.field_error), Toast.LENGTH_SHORT).show();
                    return;
                }

                long millisInput = Long.parseLong(input)*60000;
                if(millisInput<0){
                    Toast.makeText(Pop_up_activity.this, getResources().getString(R.string.positive_error), Toast.LENGTH_LONG).show();
                    return;
                }
                setTime(millisInput);
                mEditText.setText("");
                updateCountDownText();
            }
        });

        mBtnStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timerRunnig)
                    pauseTimer();
                else {
                    if(startTime==0) {
                        Toast.makeText(Pop_up_activity.this, getResources().getString(R.string.number_error), Toast.LENGTH_LONG).show();
                        return;
                    }
                    else
                        startTimer();

                }


            }
        });
        mBtnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
                updateCountDownText();
            }
        });
        updateCountDownText();




    }

    @Override
    protected void onResume() {
        super.onResume();
        Timer.getInstance().setCallback(this);
    }

    private void startTimer() {
        Timer.getInstance().startTimer();

        timerRunnig = true;
        mBtnStartPause.setText(getResources().getString(R.string.pause));
        updateCountDownText();
        mBtnReset.setVisibility(View.INVISIBLE);
        mBtnSet.setVisibility(View.INVISIBLE);
        mEditText.setVisibility(View.INVISIBLE);
    }

    private void pauseTimer() {
        Timer.getInstance().pauseTimer();
        timerRunnig = false;
        mBtnStartPause.setText(getResources().getString(R.string.start));
        mBtnReset.setVisibility(View.VISIBLE);
        mBtnSet.setVisibility(View.VISIBLE);
        mEditText.setVisibility(View.VISIBLE);
    }


    private void resetTimer() {
        Timer.getInstance().resetTimer();
        updateCountDownText();
        mBtnReset.setVisibility(View.INVISIBLE);
        mBtnStartPause.setVisibility(View.VISIBLE);
    }
    public void  setTime (long milliseconds){
        Timer.getInstance().setTime(milliseconds);
        resetTimer();
        startTime=milliseconds;
        updateCountDownText();
    }

    private void updateCountDownText() {
        int hours = (int) (timeLeft / 1000) /3600;
        int minutes = (int) ((timeLeft / 1000) %3600) / 60;
        int seconds = (int) (timeLeft / 1000) % 60;

        String timeLeftFormatted;
        if(hours > 0){
            timeLeftFormatted = String.format(Locale.getDefault(), "%d:%02d:%02d",hours, minutes, seconds);
        }else {
            timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        }
        textViewCountDown.setText(timeLeftFormatted);
    }

    @Override
    public void onTimeChanged(long millisUntilFinished) {
        timeLeft = millisUntilFinished;
        updateCountDownText();
    }

    @Override
    public void onFinish() {
        timerRunnig = false;
        mBtnStartPause.setText(getResources().getString(R.string.start));
        mBtnStartPause.setVisibility(View.INVISIBLE);
        mBtnReset.setVisibility(View.VISIBLE);
        Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        vibrator .vibrate(3000);
    }
}

