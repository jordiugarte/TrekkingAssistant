package com.galacticCat.chatbleu.tools;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.galacticCat.chatbleu.MainActivity;
import com.galacticCat.chatbleu.R;
import com.galacticCat.chatbleu.data.Stats;

public class Pedometer extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor countSensor;
    private TextView stepsView;
    private TextView distanceView;
    private int steps;
    private float distance;
    private Stats stats;

    public Pedometer(Context context, TextView stepsView, TextView distanceView, Stats stats) {
        sensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
        countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

        this.stepsView = stepsView;
        this.distanceView = distanceView;
        this.stats = stats;
        distance = stats.getDistance();
        steps = stats.getSteps();

        distanceView.setText(context.getResources().getString(R.string.distance_1) + distance + "m");
        stepsView.setText("" + steps);
        stats.setSteps(steps);
        stats.setDistance(distance);
    }

    public void resume() {
        sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_FASTEST);

    }

    public void pasue() {
//        sensorManager.unregisterListener(this, countSensor);

    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        if (sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
            steps++;
            distance += 0.5;
        }
        distanceView.setText(distance + "m");
        stepsView.setText("" + steps);
        stats.setSteps(steps);
        stats.setDistance(distance);
        stats.saveData();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}