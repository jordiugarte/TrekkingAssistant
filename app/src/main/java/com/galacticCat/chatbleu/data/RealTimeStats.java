package com.galacticCat.chatbleu.data;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;


public class RealTimeStats {

    private TextView stepsPerHourView;
    private TextView speedView;

    private float speed;
    private float inicialDistance;
    private float lastDistance;
    private int inicialSteps;
    private int lastSteps;
    private float distance;
    private int steps;

    public RealTimeStats(Context context, final TextView stepsPerHour, final TextView speedView, final Activity activity, final Stats stats) {
        this.stepsPerHourView = stepsPerHour;
        this.speedView = speedView;

        speedView.setText("Distance per hour: 0");
        Thread t1 = new Thread(){
            @Override
            public void run(){
                try {
                    while (!isInterrupted()){
                        inicialDistance = stats.getDistance();
                        Thread.sleep(10000);
                        lastDistance = stats.getDistance();
                        distance = lastDistance - inicialDistance;

                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                speed = distance / 10;
                                speedView.setText("Distance per hour: " + speed * 360);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        stepsPerHour.setText("Steps per hour: 0");
        Thread t2 = new Thread(){
            @Override
            public void run(){
                try {
                    while (!isInterrupted()){
                        inicialSteps = stats.getSteps();
                        Thread.sleep(10000);
                        lastSteps = stats.getSteps();
                        steps = lastSteps - inicialSteps;

                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                stepsPerHour.setText("Steps per hour: " + speed * 360);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        t1.start();
        t2.start();
    }


}
