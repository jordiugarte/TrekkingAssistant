package com.galacticCat.chatbleu;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.galacticCat.chatbleu.data.Stats;
import com.galacticCat.chatbleu.tools.Clock;
import com.galacticCat.chatbleu.tools.Compass;
import com.galacticCat.chatbleu.tools.Flashlight;
import com.galacticCat.chatbleu.tools.SOSFlashlight;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Stats stats = new Stats();
    //Tools
    private Clock clockTool;
    private Compass compassTool;
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
        context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        setListeners();

        clockTool = new Clock(dateView, timeView, MainActivity.this, stats);
        compassTool = new Compass(context, compass);

        //Flashlight
        flashlightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flashlightButton.isChecked()){
                    new Flashlight(MainActivity.this, context, true);
                } else {
                    new Flashlight(MainActivity.this, context, false);
                }
            }
        });

        //S.O.S.
        sosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sosButton.isChecked()){
                    SOSFlashlight.getInstance().flashLight(MainActivity.this, context);
                } else {
                    SOSFlashlight.getInstance().stopFlashLight();
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
        compassTool.resume();


    }

    @Override
    protected void onPause() {
        super.onPause();
        compassTool.pause();
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


}
