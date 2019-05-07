package com.galacticCat.chatbleu;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.BatteryManager;
import android.provider.Settings;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.galacticCat.chatbleu.data.RealTimeStats;
import com.galacticCat.chatbleu.data.Stats;
import com.galacticCat.chatbleu.map.MapsActivity;
import com.galacticCat.chatbleu.services.Notification;
import com.galacticCat.chatbleu.tools.Altitude;
import com.galacticCat.chatbleu.tools.Clock;
import com.galacticCat.chatbleu.tools.Compass;
import com.galacticCat.chatbleu.tools.Flashlight;
import com.galacticCat.chatbleu.tools.Pedometer;
import com.galacticCat.chatbleu.tools.SOSFlashlight;
import android.provider.Settings;


import org.w3c.dom.Text;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    //Tools
    private Clock clockTool;
    private Compass compassTool;
    private Altitude altitude;
    private Pedometer pedometer;
    private SOSFlashlight sos;
    //Listeners
        //Background
    private ConstraintLayout layout;
    private ConstraintLayout battery;
        //Text Viewers
    private TextView timeView;
    private TextView dateView;
    private TextView stepsView;
    private TextView distanceView;
    private TextView weightView;
    private TextView timeOfTravelView;
    private TextView altitudeView;
    private TextView stepsPerHourView;
    private TextView speedView;
    private TextView batteryView;
        //Buttons
    private ToggleButton flashlightButton;
    private ToggleButton campingButton;
    private ToggleButton sosButton;
    private Button listsButton;
    private Button iniciarSesionButton;
    private Button mapsButton;
        //Images
    private ImageView compass;
    private ImageView steps;

    private Context context;
    private Stats stats;
    private RealTimeStats rStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Basic Activity Set
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        context = getApplicationContext();
        context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        stats = new Stats(context);

        setListeners();

        rStats = new RealTimeStats(context, stepsPerHourView, speedView, MainActivity.this, stats);
        clockTool = new Clock(dateView, timeView, timeOfTravelView, MainActivity.this, stats);
        compassTool = new Compass(context, compass);
        altitude = new Altitude(altitudeView, context);
        pedometer = new Pedometer(context, stepsView, distanceView, stats);
        sos = new SOSFlashlight(MainActivity.this, context);

        this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        //Flashlight
        flashlightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flashlightButton.isChecked()){
                    sos = null;
                    new Flashlight(MainActivity.this, context, true);
                    new Notification(context, "Flashlight: ON", R.drawable.flashlight);
                } else {
                    sos = new SOSFlashlight(MainActivity.this, context);
                    new Flashlight(MainActivity.this, context, false);
                    new Notification(context, "Flashlight: OFF", R.drawable.flashlight);
                }
            }
        });

        //S.O.S.
        sosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sosButton.isChecked()){
                    sos.turnFlashLight();
                    new Notification(context, "SOS Flashlight: ON", R.drawable.sos);
                } else {
                    sos.stopFlashLight();
                    new Notification(context, "SOS Flashlight: OFF", R.drawable.sos);
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

        //Iniciar Sesion
       iniciarSesionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              openActivityLogin(); }
        });

        //Mochila
        listsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MochilaActivity.class);
                startActivity(intent); }
        });
        //Mapas
        mapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent); }
        });

    }

    @Override
    protected void onResume(){
        super.onResume();
        compassTool.resume();
        pedometer.resume();
        weightView.setText("Weight: " + 3.2f + "kg");
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
        listsButton = (Button)findViewById(R.id.mochila_btn);
        iniciarSesionButton = (Button) findViewById(R.id.buttonUser);
        steps = (ImageView)findViewById(R.id.iconSteps);
        mapsButton = (Button)findViewById(R.id.maps_btn);
        battery = findViewById(R.id.battery);

        stepsView = (TextView) findViewById(R.id.stepscountView);
        distanceView = (TextView) findViewById(R.id.distanceView);
        weightView = (TextView) findViewById(R.id.weightView);
        altitudeView = (TextView) findViewById(R.id.altitudeView);
        timeOfTravelView = (TextView) findViewById(R.id.timeOfTravelView);
        speedView = (TextView) findViewById(R.id.speedView);
        stepsPerHourView = (TextView) findViewById(R.id.stepsPerHourView);
        batteryView = findViewById(R.id.battery_view);

    }

    private void makeToast(String message) {
        Toast t = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        t.show();
    }

    private void campingMode(boolean active) {
        int defaultColorText = 0;
        int defaultColorBackground = 0;

        int defaultCompass = 0;
        int defaultFlashlight = 0;
        int defaultSos = 0;
        int defaultCamping = 0;
        int defaultSteps = 0;
        int defaultBattery = 0;

        if (active) {
            //Settings
//            SetAirplaneMode();
            Calendar rightNow = Calendar.getInstance();
            int currentHourIn24Format = rightNow.get(Calendar.HOUR_OF_DAY);

            //Night
            if (currentHourIn24Format > 17 || currentHourIn24Format < 7){
                defaultColorText = getResources().getColor(R.color.defaultWhite);
                defaultColorBackground = getResources().getColor(R.color.defaultBlack);

                layout.setBackgroundColor(defaultColorBackground);

                defaultCompass = R.drawable.compass;
                defaultFlashlight = R.drawable.flashlight;
                defaultSos = R.drawable.sos;
                defaultCamping = R.drawable.camp_mode;
                defaultSteps = R.drawable.steps;
                defaultBattery = R.drawable.battery;

                //Day
            } else if (currentHourIn24Format < 18 || currentHourIn24Format > 6){
                defaultColorText = getResources().getColor(R.color.defaultBlack);
                defaultColorBackground = getResources().getColor(R.color.defaultWhite);

                layout.setBackgroundColor(defaultColorBackground);

                defaultCompass = R.drawable.compass_black;
                defaultFlashlight = R.drawable.flashlight_black;
                defaultSos = R.drawable.sos_black;
                defaultCamping = R.drawable.camp_mode_black;
                defaultSteps = R.drawable.steps_black;
                defaultBattery = R.drawable.battery_black;

            }
            layout.setBackgroundColor(defaultColorBackground);

            new Notification(context, "Camping Mode: ON", R.drawable.camp_mode);

        } else {
//            SetAirplaneMode();
            defaultSteps = R.drawable.steps;
            defaultCompass = R.drawable.compass;
            defaultFlashlight = R.drawable.flashlight;
            defaultSos = R.drawable.sos;
            defaultCamping = R.drawable.camp_mode;
            defaultBattery = R.drawable.battery;
            defaultColorText = getResources().getColor(R.color.defaultWhite);
            layout.setBackground(getResources().getDrawable(R.drawable.forest_background));
            new Notification(context, "Camping Mode: OFF", R.drawable.camp_mode);
        }

        //Button Views
        flashlightButton.setBackgroundDrawable(this.getResources().getDrawable(defaultFlashlight));
        sosButton.setBackgroundDrawable(this.getResources().getDrawable(defaultSos));
        campingButton.setBackgroundDrawable(this.getResources().getDrawable(defaultCamping));
        battery.setBackgroundDrawable(this.getResources().getDrawable(defaultBattery));

        //Image Views
        compass.setImageResource(defaultCompass);
        steps.setImageResource(defaultSteps);

        //Text Views
        timeView.setTextColor(defaultColorText);
        dateView.setTextColor(defaultColorText);
        stepsView.setTextColor(defaultColorText);
        distanceView.setTextColor(defaultColorText);
        weightView.setTextColor(defaultColorText);
        altitudeView.setTextColor(defaultColorText);
        speedView.setTextColor(defaultColorText);
        stepsPerHourView.setTextColor(defaultColorText);
        batteryView.setTextColor(defaultColorText);
        timeOfTravelView.setTextColor(defaultColorText);

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        stats.saveData();
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }
    public void openActivityLogin(){
        Intent intent1 = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent1);
   }

//    private void SetAirplaneMode(){
//        if (android.os.Build.VERSION.SDK_INT < 17) {
//            try {
//                // read the airplane mode setting
//                boolean isEnabled = Settings.System.getInt(
//                        getContentResolver(),
//                        Settings.System.AIRPLANE_MODE_ON, 0) == 1;
//
//                // toggle airplane mode
//                Settings.System.putInt(
//                        getContentResolver(),
//                        Settings.System.AIRPLANE_MODE_ON, isEnabled ? 0 : 1);
//
//                // Post an intent to reload
//                Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
//                intent.putExtra("state", !isEnabled);
//                sendBroadcast(intent);
//            } catch (ActivityNotFoundException e) {
//
//            }
//        } else {
//            try {
//                Intent intent = new Intent(android.provider.Settings.ACTION_AIRPLANE_MODE_SETTINGS);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//            } catch (ActivityNotFoundException e) {
//                try {
//                    Intent intent = new Intent("android.settings.WIRELESS_SETTINGS");
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                } catch (ActivityNotFoundException ex) {
//
//                }
//            }
//        }
//    }

    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context ctxt, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            batteryView.setText(String.valueOf(level) + "%");
        }
    };
}
