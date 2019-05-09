package com.galacticCat.chatbleu.tools.Timer;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Vibrator;

import com.galacticCat.chatbleu.MainActivity;


public class Timer {
    private static Timer instancia = new Timer();

    private CountDownTimer mCountDownTimer;
    private long mStartTime;
    private boolean mTimerRunnig;
    private long mTimeLeft = 0;

    private TimerI callback;

    public static Timer getInstance(){
        return instancia;
    }

    public void setCallback(TimerI callback){
        this.callback = callback;
    }

    public void startTimer() {

        mCountDownTimer = new CountDownTimer(mTimeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(callback!=null){
                    callback.onTimeChanged(millisUntilFinished);
                    mTimeLeft = millisUntilFinished;

                }
            }

            @Override
            public void onFinish() {
                mTimerRunnig = false;
                if(callback!=null){
                    callback.onFinish();
                }


            }
        }.start();
        mTimerRunnig = true;
    }

    public void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunnig = false;
    }

    public void resetTimer() {
        mTimeLeft = mStartTime;
        mTimerRunnig =false;
    }
    public void  setTime (long milliseconds){
        mStartTime = milliseconds;
        mTimeLeft = mStartTime;
    }
}


