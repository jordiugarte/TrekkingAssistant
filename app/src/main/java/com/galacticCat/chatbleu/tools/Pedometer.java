package com.galacticCat.chatbleu.tools;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.galacticCat.chatbleu.data.Stats;

public class Pedometer extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private TextView stepsView;
    private boolean running = false;
    private int steps;
    private int initialSteps;

    public Pedometer(Context context, TextView view, TextView stepsView, Stats stats) {
        sensorManager = (SensorManager)context.getSystemService(context.SENSOR_SERVICE);
        this.stepsView = stepsView;
    }

    public void resume(){
        running = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null){
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            //TODO TOAST
        }
    }

    public void pasue(){
        running = false;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(running){
            stepsView.setText(String.valueOf(event.values[0]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}