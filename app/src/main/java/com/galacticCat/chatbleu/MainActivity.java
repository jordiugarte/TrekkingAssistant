package com.galacticCat.chatbleu;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.renderscript.Sampler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    //Data
    private String time;
    private String date;
    private boolean sosActive;

    //Listeners
        //Background
    private ConstraintLayout layout;
        //Text Viewers
    private TextView timeView;
    private TextView dateView;
        //Buttons
    private ToggleButton flashlightButton;
    private ToggleButton campingButton;

    private Button sosButton;


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

    private void setListeners() {
        timeView = (TextView)findViewById(R.id.clock);
        dateView = (TextView)findViewById(R.id.date);
        flashlightButton = (ToggleButton) findViewById(R.id.flashlight_btn);
        sosButton = (Button) findViewById(R.id.sos_btn);
        campingButton = (ToggleButton) findViewById(R.id.camping_toggle);
        layout = (ConstraintLayout) findViewById(R.id.mainLayout);
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
    }
}
