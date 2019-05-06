package com.galacticCat.chatbleu.tools;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.galacticCat.chatbleu.MainActivity;
import com.galacticCat.chatbleu.data.Stats;

public class Pedometer extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor countSensor;
    private TextView stepsView;
    private TextView distanceView;
    private int steps;
    private int distance;
    private Stats stats;
    private boolean campingMode;

    public Pedometer(Context context, TextView stepsView, TextView distanceView, Stats stats, boolean campingMode) {
        sensorManager = (SensorManager)context.getSystemService(context.SENSOR_SERVICE);
        countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

        this.stepsView = stepsView;
        this.distanceView = distanceView;
        this.stats = stats;
        distance = stats.getDistance();
        steps = stats.getSteps();

        distanceView.setText("Distance: " + distance + "m");
        stepsView.setText("" + steps);
        stats.setSteps(steps);
        stats.setDistance(distance);
    }

    public void resume(){
        sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_FASTEST);

    }

    public void pasue(){
//        sensorManager.unregisterListener(this, countSensor);

    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (campingMode) {
            Sensor sensor = event.sensor;
            float[] values = event.values;
            int value = -1;

            if (values.length > 0) {
                value = (int) values[0];
            }
            if (sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
                steps++;
                distance += steps / 2;
            }
            distanceView.setText(distance + "m");
            stepsView.setText("" + steps);
            stats.setSteps(steps);
            stats.setDistance(distance);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}