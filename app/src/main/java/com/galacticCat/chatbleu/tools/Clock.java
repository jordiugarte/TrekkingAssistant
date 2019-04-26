package com.galacticCat.chatbleu.tools;

import android.app.Activity;
import android.widget.TextView;

import com.galacticCat.chatbleu.data.Stats;

import java.text.SimpleDateFormat;

public class Clock {

    private String time;
    private String date;
    private Stats stats;

    public Clock (final TextView dateView, final TextView timeView, final Activity activity, Stats stats) {
        this.stats = stats;
        final SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, d MMMM yyyy");
        final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        Thread t = new Thread(){
            @Override
            public void run(){
                try {
                    while (!isInterrupted()){
                        Thread.sleep(1000);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                long systemTime = System.currentTimeMillis();
                                date = dateFormat.format(systemTime);
                                time = timeFormat.format(systemTime);
                                timeView.setText(time);
                                dateView.setText(date);
                            }
                        });

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }
}
