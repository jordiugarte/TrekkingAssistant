package com.galacticCat.chatbleu.tools;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;

import com.galacticCat.chatbleu.R;
import com.galacticCat.chatbleu.data.Stats;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Clock {

    private String time;
    private String date;
    private Stats stats;

    private int secondsPassed;
    private int minutesPassed;
    private int hoursPassed;

    public Clock (final TextView dateView, final TextView timeView, final TextView timeOfTravelView, final Activity activity, final Stats stats) {
        this.stats = stats;

        hoursPassed = stats.getTimeHours();
        minutesPassed = stats.getTimeMinutes();
        secondsPassed = stats.getTimeSeconds();

        final SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, d MMMM yyyy");
        final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        Thread t = new Thread(){
            @Override
            public void run(){
                try {
                    while (!isInterrupted()){
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                long systemTime = System.currentTimeMillis();
                                date = dateFormat.format(systemTime);
                                time = timeFormat.format(systemTime);
                                timeView.setText(time);
                                dateView.setText(date);

                                secondsPassed++;
                                if (secondsPassed > 59){
                                    secondsPassed = 0;
                                    minutesPassed++;
                                }
                                if (minutesPassed > 59){
                                    minutesPassed = 0;
                                    hoursPassed++;
                                }
                                String extraCero1 = null;
                                String extraCero2 = null;
                                String extraCero3 = null;
                                if (hoursPassed < 10) {
                                    extraCero1 = "0";
                                } else {
                                    extraCero1 = "";
                                }
                                if (minutesPassed < 10) {
                                    extraCero2 = "0";
                                } else {
                                    extraCero2 = "";
                                }
                                if (secondsPassed < 10) {
                                    extraCero3 = "0";
                                } else {
                                    extraCero3 = "";
                                }

                                stats.setTimeHours(hoursPassed);
                                stats.setTimeMinutes(minutesPassed);
                                stats.setTimeSeconds(secondsPassed);

                                timeOfTravelView.setText("Time passed: " + extraCero1 + hoursPassed + ":" + extraCero2 + minutesPassed + ":" + extraCero3 + secondsPassed);
                            }
                        });
                        Thread.sleep(1000);
                        stats.saveData();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }

}
