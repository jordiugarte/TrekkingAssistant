package com.galacticCat.chatbleu;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import com.galacticCat.chatbleu.tools.Altitude;
import com.galacticCat.chatbleu.tools.Clock;
import com.galacticCat.chatbleu.tools.Compass;
import com.galacticCat.chatbleu.tools.Flashlight;
import com.galacticCat.chatbleu.tools.Pedometer;
import com.galacticCat.chatbleu.tools.SOSFlashlight;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Stats stats = new Stats();
    //Tools
    private Clock clockTool;
    private Compass compassTool;
    private Altitude altitude;
    private Pedometer pedometer;
    //Listeners
        //Background
    private ConstraintLayout layout;
        //Text Viewers
    private TextView timeView;
    private TextView dateView;
    private TextView stepsView;
    private TextView distanceView;
    private TextView weightView;
    private TextView altitudeView;
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
        altitude = new Altitude(altitudeView, context);
        pedometer = new Pedometer(context, stepsView);

        //Flashlight
        flashlightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flashlightButton.isChecked()){
                    new Flashlight(MainActivity.this, context, true);
                    makeToast("Flashligh: ON");
                } else {
                    new Flashlight(MainActivity.this, context, false);
                    makeToast("Flashlight: OFF");
                }
            }
        });

        //S.O.S.
        sosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sosButton.isChecked()){
                    SOSFlashlight.getInstance().flashLight(MainActivity.this, context);
                    makeToast("SOS Flashlight: ON");
                } else {
                    SOSFlashlight.getInstance().stopFlashLight();
                    makeToast("SOS Flashlight: OFF");
                }
            }
        });

        //Set Camping Mode
        campingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (campingButton.isChecked()) {
                    campingMode(true);
                    makeToast("Camping Mode: ON");
                } else {
                    campingMode(false);
                    makeToast("Camping Mode: OFF");
                }
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        compassTool.resume();
        pedometer.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        compassTool.pause();
        pedometer.pasue();
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
        altitudeView = (TextView) findViewById(R.id.altitudeView);
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

            //Night
            if (currentHourIn24Format > 17 || currentHourIn24Format < 7){
                defaultColorText = getResources().getColor(R.color.defaultWhite);
                defaultColorBackground = getResources().getColor(R.color.defaultBlack);

                compass.setImageResource(R.drawable.compass_black);
                //flashlightButton.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.flashlight_black));
                //sosButton.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.sos_black));
                //campingButton.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.camp_mode_black));

                //Day
            } else if (currentHourIn24Format < 18 || currentHourIn24Format > 6){
                defaultColorText = getResources().getColor(R.color.defaultBlack);
                defaultColorBackground = getResources().getColor(R.color.defaultWhite);

                compass.setImageResource(R.drawable.compass);
                //flashlightButton.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.flashlight));
                //sosButton.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.sos));
                //campingButton.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.camp_mode));
            }
            layout.setBackgroundColor(defaultColorBackground);
        } else {
            defaultColorText = getResources().getColor(R.color.defaultWhite);
            layout.setBackground(getResources().getDrawable(R.drawable.forest_background));
        }
        timeView.setTextColor(getColor(defaultColorText));
        dateView.setTextColor(getColor(defaultColorText));
        layout.setBackgroundColor(getColor(defaultColorBackground));
        stepsView.setTextColor(getColor(defaultColorText));
        distanceView.setTextColor(getColor(defaultColorText));
        weightView.setTextColor(getColor(defaultColorText));
        altitudeView.setTextColor(getColor(defaultColorText));

    }


}
