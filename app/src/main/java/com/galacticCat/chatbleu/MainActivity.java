package com.galacticCat.chatbleu;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    //Data
    private String time;
    private String date;
    private boolean sosActive;
    private float[] gravity = new float[3];
    private float[] geomagnetic = new float[3];
    private float azimuth = 0f;
    private float currectAzimuth = 0f;
    private SensorManager sensorManager;

    //Listeners
        //Background
    private ConstraintLayout layout;
        //Text Viewers
    private TextView timeView;
    private TextView dateView;
    private TextView stepsView;
    private TextView distanceView;
    private TextView weightView;
        //Buttons
    private ToggleButton flashlightButton;
    private ToggleButton campingButton;
    private ToggleButton sosButton;
        //Images
    private ImageView compass;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Basic Activity Set
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        context = getApplicationContext();
        setListeners();
        context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        final SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, d MMMM yyyy");
        final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        Thread t = new Thread(){
            @Override
            public void run(){
                try {
                    while (!isInterrupted()){
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
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
        //FlashLight
        boolean flashAvailable = getPackageManager().hasSystemFeature(getPackageManager().FEATURE_CAMERA_FLASH);
        boolean cameraLight = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.CAMERA}, 60);
        final CameraManager cameraManager =  (CameraManager) getSystemService(CAMERA_SERVICE);

        //Flashlight
        flashlightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String cameraID = cameraManager.getCameraIdList()[0];
                    if (flashlightButton.isChecked()) {
                        cameraManager.setTorchMode(cameraID, true);
                    } else {
                        cameraManager.setTorchMode(cameraID, false);
                    }
                } catch (CameraAccessException e){
                    e.printStackTrace();
                }
            }
        });

        //S.O.S.
        sosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sosActive) {
                    sosActive = false;
                } else {
                    sosActive = true;
                }
                try {
                    final String cameraID = cameraManager.getCameraIdList()[0];
                    while (sosActive) {
                        sosLantern(cameraManager, cameraID);
                    }
                } catch (CameraAccessException e){
                    e.printStackTrace();
                }
            }
        });

        //Set Camping Mode
        campingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (campingButton.isChecked()) {
                    campingMode(true);
                } else {
                    campingMode(false);
                }
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    private void setListeners() {
        timeView = (TextView)findViewById(R.id.clock);
        dateView = (TextView)findViewById(R.id.date);
        flashlightButton = (ToggleButton) findViewById(R.id.flashlight_btn);
        sosButton = (ToggleButton) findViewById(R.id.sos_btn);
        campingButton = (ToggleButton) findViewById(R.id.camping_toggle);
        layout = (ConstraintLayout) findViewById(R.id.mainLayout);
        compass = (ImageView) findViewById(R.id.compass);
        stepsView = (TextView) findViewById(R.id.stepsView);
        distanceView = (TextView) findViewById(R.id.distanceView);
        weightView = (TextView) findViewById(R.id.weightView);
        //TODO
        campingMode(false);
    }

    private void makeToast(String message) {
        Toast t = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        t.show();
    }

    private void sosLantern(CameraManager cameraManager, String cameraID){
        try {
            cameraManager.setTorchMode(cameraID, true);
            Thread.sleep(300);
            cameraManager.setTorchMode(cameraID, false);
            Thread.sleep(300);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void campingMode(boolean active) {
        int defaultColorText = 0;
        int defaultColorBackground = 0;
        if (active) {
            Calendar rightNow = Calendar.getInstance();
            int currentHourIn24Format = rightNow.get(Calendar.HOUR_OF_DAY);

            if (currentHourIn24Format > 17 || currentHourIn24Format < 7){
                defaultColorText = getResources().getColor(R.color.defaultWhite);
                defaultColorBackground = getResources().getColor(R.color.defaultBlack);
            } else if (currentHourIn24Format < 18 || currentHourIn24Format > 6){
                defaultColorText = getResources().getColor(R.color.defaultBlack);
                defaultColorBackground = getResources().getColor(R.color.defaultWhite);
            }
            layout.setBackgroundColor(defaultColorBackground);
        } else {
            defaultColorText = getResources().getColor(R.color.defaultWhite);
            layout.setBackground(getResources().getDrawable(R.drawable.forest_background));
        }
        timeView.setTextColor(defaultColorText);
        dateView.setTextColor(defaultColorText);
        stepsView.setTextColor(defaultColorText);
        distanceView.setTextColor(defaultColorText);
        weightView.setTextColor(defaultColorText);

        //flashlightButton.setBackgroundColor(defaultColorText);
        //sosButton.setBackgroundColor(defaultColorText);
        //compass.setBackgroundColor(defaultColorText);

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        final float alpha = 0.97f;
        synchronized (this){
            if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                gravity[0] = alpha*gravity[0] + (1 - alpha)*sensorEvent.values[0];
                gravity[1] = alpha*gravity[1] + (1 - alpha)*sensorEvent.values[1];
                gravity[2] = alpha*gravity[2] + (1 - alpha)*sensorEvent.values[2];
            }

            if(sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
                geomagnetic[0] = alpha*geomagnetic[0] + (1 - alpha)*sensorEvent.values[0];
                geomagnetic[1] = alpha*geomagnetic[1] + (1 - alpha)*sensorEvent.values[1];
                geomagnetic[2] = alpha*geomagnetic[2] + (1 - alpha)*sensorEvent.values[2];
            }

            float R[] = new float[9];
            float I[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, gravity, geomagnetic);
            if (success) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);
                azimuth = (float)Math.toDegrees(orientation[0]);
                azimuth = (azimuth + 360)%360;

                Animation anim = new RotateAnimation(-currectAzimuth, -azimuth, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5F);
                currectAzimuth = azimuth;

                anim.setDuration(500);
                anim.setRepeatCount(0);
                anim.setFillAfter(true);

                compass.startAnimation(anim);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
