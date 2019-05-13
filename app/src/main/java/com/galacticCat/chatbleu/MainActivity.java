package com.galacticCat.chatbleu;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.BatteryManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.VideoView;

import com.galacticCat.chatbleu.data.RealTimeStats;
import com.galacticCat.chatbleu.data.Stats;
import com.galacticCat.chatbleu.data.UserData;
import com.galacticCat.chatbleu.map.MapsActivity;
import com.galacticCat.chatbleu.services.Notification;
//import com.galacticCat.chatbleu.tools.Altitude;
import com.galacticCat.chatbleu.tools.Clock;
import com.galacticCat.chatbleu.tools.Compass;
import com.galacticCat.chatbleu.tools.Flashlight;
import com.galacticCat.chatbleu.tools.Gps;
import com.galacticCat.chatbleu.tools.Pedometer;
import com.galacticCat.chatbleu.tools.SOSFlashlight;
import com.galacticCat.chatbleu.tools.Timer.Pop_up_activity;
import com.galacticCat.chatbleu.tools.Timer.Timer;
import com.galacticCat.chatbleu.tools.Timer.TimerI;


import java.util.Calendar;
import java.util.Locale;

import static com.galacticCat.chatbleu.data.Stats.SHARED_PREFS;

public class MainActivity extends AppCompatActivity implements TimerI {

    //Tools
    private Clock clockTool;
    private Compass compassTool;
    private Pedometer pedometer;
    private SOSFlashlight sos;
    private Flashlight flash;

        private Gps gps;

    //Background
    private ConstraintLayout layout,
            battery;
    //Text Viewers
    private TextView timeView, dateView,
            stepsView,
            distanceView,
            weightView,
            timeOfTravelView,
            stepsPerHourView,
            speedView,
            batteryView,
            userView,
            ageView,
            uWeightView,
            textViewCountDown,
            locationView;

    //Toggle Buttons
    private ToggleButton flashlightButton,
            campingButton,
            sosButton;

    //Buttons
    private Button listsButton, personalDataButton,
            mapsButton,
            contactsbutton,
            objectivesbutton;

    //Images
    private ImageView compass,
            steps;

    private Context context;
    private Stats stats;

    //Timer
    private long timeLeft = 0;
    private UserData userData;
    private RealTimeStats rStats;
    private SharedPreferences sharedPreferences;

    private boolean campModeOn;
    private boolean day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Basic Activity Set
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        context = getApplicationContext();
        context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        stats = new Stats(context);
        userData = new UserData(context);
        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);


        setListeners();
        setTools();
        setPersonalData();

        this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        //Flashlight
        flashlightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sos == null) {
                    if (flashlightButton.isChecked()) {
                        flash = new Flashlight(MainActivity.this, context, true);
                        new Notification(context,getResources().getString(R.string.flashlight_on), R.drawable.flashlight);
                    } else {
                        flash = new Flashlight(MainActivity.this, context, false);
                        new Notification(context, getResources().getString(R.string.flashlight_off), R.drawable.flashlight);
                        flash = null;
                    }
                } else {
                    makeToast("First turn off your SOS Flashlight!");
                }
            }
        });

        //S.O.S.
        sosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flash == null) {
                    if (sos == null) {
                        sos = new SOSFlashlight(MainActivity.this, context);
                    }
                    if (sosButton.isChecked()) {
                        sos.turnFlashLight();
                        new Notification(context, getResources().getString(R.string.sos_flashlight_on), R.drawable.sos);
                    } else {
                        sos.stopFlashLight();
                        sos = null;
                        new Flashlight(MainActivity.this, context, false);
                        new Notification(context, getResources().getString(R.string.sos_flashlight_off), R.drawable.sos);
                    }
                } else {
                    makeToast(getResources().getString(R.string.flashlight_alert));
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

        //Datos personales
        personalDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignUpActivity();
            }
        });

        //Mochila
        listsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MochilaActivity.class);
                intent.putExtra("campMode", campModeOn);
                intent.putExtra("day", day);
                startActivity(intent);
            }
        });
        //Mapas
        mapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MapsActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        //Pop-up timer

        textViewCountDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Pop_up_activity.class));
            }
        });
        //Contacts
        contactsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ContactActivity.class);
                intent.putExtra("campMode", campModeOn);
                intent.putExtra("day", day);
                startActivity(intent);
            }
        });
        //Objectives
        objectivesbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ObjectivesActivity.class);
                intent.putExtra("campMode", campModeOn);
                intent.putExtra("day", day);
                startActivity(intent);
            }
        });
    }

    private void setPersonalData() {
        String name = sharedPreferences.getString("USER", "");
        int weight = sharedPreferences.getInt("WEIGHT_USER", 0);
        int age = sharedPreferences.getInt("AGE_USER", 0);

        userView.setText(name);
        uWeightView.setText(getResources().getString(R.string.weight) + weight);
        ageView.setText(getResources().getString(R.string.age) + age);
    }

    @Override
    protected void onResume() {
        super.onResume();
        compassTool.resume();
        pedometer.resume();
        super.onResume();
        Timer.getInstance().setCallback(this);

        String name = sharedPreferences.getString("USER", "");
        int weight = sharedPreferences.getInt("WEIGHT_USER", 0);
        int age = sharedPreferences.getInt("AGE_USER", 0);
        float weightBag = sharedPreferences.getFloat("W", 0.0f);

        userView.setText(name);
        uWeightView.setText(getResources().getString(R.string.weight) + weight);
        ageView.setText(getResources().getString(R.string.age) + age);
        weightView.setText(getResources().getString(R.string.bag_weight) + weightBag + "kg");
    }

    @Override
    public void onTimeChanged(long millisUntilFinished) {
        timeLeft = millisUntilFinished;
        updateCountDownText();
    }

    @Override
    protected void onPause() {
        super.onPause();
        compassTool.pause();
        pedometer.pasue();
    }

    @Override
    public void onFinish() {

    }

    private void setListeners() {
        timeView = (TextView) findViewById(R.id.clock);
        dateView = (TextView) findViewById(R.id.date);
        flashlightButton = (ToggleButton) findViewById(R.id.flashlight_btn);
        sosButton = (ToggleButton) findViewById(R.id.sos_btn);
        campingButton = (ToggleButton) findViewById(R.id.camping_toggle);
        layout = (ConstraintLayout) findViewById(R.id.mainLayout);
        compass = (ImageView) findViewById(R.id.compass);
        listsButton = (Button) findViewById(R.id.mochila_btn);
        personalDataButton = (Button) findViewById(R.id.buttonUser);
        steps = (ImageView) findViewById(R.id.iconSteps);
        mapsButton = (Button) findViewById(R.id.maps_btn);
        contactsbutton = findViewById(R.id.contactsbutton);
        battery = findViewById(R.id.battery);
        textViewCountDown = findViewById(R.id.timerText);
        locationView = findViewById(R.id.locationText);
        stepsView = (TextView) findViewById(R.id.stepscountView);
        distanceView = (TextView) findViewById(R.id.distanceView);
        weightView = (TextView) findViewById(R.id.weightView);
        timeOfTravelView = (TextView) findViewById(R.id.timeOfTravelView);
        speedView = (TextView) findViewById(R.id.speedView);
        stepsPerHourView = (TextView) findViewById(R.id.stepsPerHourView);
        batteryView = findViewById(R.id.battery_view);
        userView = findViewById(R.id.user_view);
        ageView = findViewById(R.id.age_view);
        uWeightView = findViewById(R.id.personalWeight_view);
        objectivesbutton = findViewById(R.id.objectivesButton);
    }

    private void setTools() {
        rStats = new RealTimeStats(context, stepsPerHourView, speedView, MainActivity.this, stats);
        clockTool = new Clock(dateView, timeView, timeOfTravelView, MainActivity.this, stats);
        compassTool = new Compass(context, compass);
        pedometer = new Pedometer(context, stepsView, distanceView, stats);
        //gps = new Gps(locationView);
    }

    private void makeToast(String message) {
        Toast t = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        t.show();
    }

    private void campingMode(boolean active) {
        campModeOn = active;
        int defaultColorText = 0;
        int defaultColorBackground = 0;

        int defaultCompass = 0;
        int defaultFlashlight = 0;
        int defaultSos = 0;
        int defaultCamping = 0;
        int defaultSteps = 0;
        int defaultBattery = 0;
        int defaultUser = 0;
        int defaultContacts = 0;
        int defaultObjectives = 0;
        int defaultBag = 0;
        int defaultMap = 0;

        if (active) {
            //Settings
//            SetAirplaneMode();
            Calendar rightNow = Calendar.getInstance();
            int currentHourIn24Format = rightNow.get(Calendar.HOUR_OF_DAY);

            //Night
            if (currentHourIn24Format > 17 || currentHourIn24Format < 7) {
                day = false;
                defaultColorText = getResources().getColor(R.color.defaultWhite);
                defaultColorBackground = getResources().getColor(R.color.defaultBlack);

                layout.setBackgroundColor(defaultColorBackground);

                defaultCompass = R.drawable.compass;
                defaultFlashlight = R.drawable.flashlight;
                defaultSos = R.drawable.sos;
                defaultCamping = R.drawable.camp_mode;
                defaultSteps = R.drawable.steps;
                defaultBattery = R.drawable.battery;
                defaultUser = R.drawable.user;
                defaultContacts = R.drawable.contacts;
                defaultObjectives = R.drawable.objectives;
                defaultBag = R.drawable.mochila;
                defaultMap = R.drawable.mapa;

                //Day
            } else if (currentHourIn24Format < 18 || currentHourIn24Format > 6) {
                day = true;
                defaultColorText = getResources().getColor(R.color.defaultBlack);
                defaultColorBackground = getResources().getColor(R.color.defaultWhite);

                layout.setBackgroundColor(defaultColorBackground);

                defaultCompass = R.drawable.compass_black;
                defaultFlashlight = R.drawable.flashlight_black;
                defaultSos = R.drawable.sos_black;
                defaultCamping = R.drawable.camp_mode_black;
                defaultSteps = R.drawable.steps_black;
                defaultBattery = R.drawable.battery_black;
                defaultUser = R.drawable.user_black;
                defaultContacts = R.drawable.contactsblack;
                defaultObjectives = R.drawable.objectives_black;
                defaultBag = R.drawable.mochila_black;
                defaultMap = R.drawable.mapa_back;

            }
            layout.setBackgroundColor(defaultColorBackground);

            new Notification(context,getResources().getString(R.string.camping_on) , R.drawable.camp_mode);

        } else {
//            SetAirplaneMode();
            defaultSteps = R.drawable.steps;
            defaultCompass = R.drawable.compass;
            defaultFlashlight = R.drawable.flashlight;
            defaultSos = R.drawable.sos;
            defaultCamping = R.drawable.camp_mode;
            defaultBattery = R.drawable.battery;
            defaultColorText = getResources().getColor(R.color.defaultWhite);
            defaultUser = R.drawable.user;
            defaultContacts = R.drawable.contacts;
            defaultObjectives = R.drawable.objectives;
            defaultBag = R.drawable.mochila;
            defaultMap = R.drawable.mapa;

            layout.setBackground(getResources().getDrawable(R.drawable.backgroundforest));
            new Notification(context, getResources().getString(R.string.camping_off), R.drawable.camp_mode);
        }

        //Button Views
        flashlightButton.setBackgroundDrawable(this.getResources().getDrawable(defaultFlashlight));
        sosButton.setBackgroundDrawable(this.getResources().getDrawable(defaultSos));
        campingButton.setBackgroundDrawable(this.getResources().getDrawable(defaultCamping));
        battery.setBackgroundDrawable(this.getResources().getDrawable(defaultBattery));
        personalDataButton.setBackground(this.getResources().getDrawable(defaultUser));
        contactsbutton.setBackground(this.getResources().getDrawable(defaultContacts));
        objectivesbutton.setBackground(this.getResources().getDrawable(defaultObjectives));
        mapsButton.setBackground(this.getResources().getDrawable(defaultMap));
        listsButton.setBackground(this.getResources().getDrawable(defaultBag));

        //Image Views
        compass.setImageResource(defaultCompass);
        steps.setImageResource(defaultSteps);

        //Text Views
        timeView.setTextColor(defaultColorText);
        dateView.setTextColor(defaultColorText);
        stepsView.setTextColor(defaultColorText);
        distanceView.setTextColor(defaultColorText);
        weightView.setTextColor(defaultColorText);
        speedView.setTextColor(defaultColorText);
        stepsPerHourView.setTextColor(defaultColorText);
        batteryView.setTextColor(defaultColorText);
        timeOfTravelView.setTextColor(defaultColorText);
        userView.setTextColor(defaultColorText);
        ageView.setTextColor(defaultColorText);
        uWeightView.setTextColor(defaultColorText);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.exit_message))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        stats.saveData();
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    public void openSignUpActivity() {
        Intent intent1 = new Intent(MainActivity.this, SignupActivity.class);
        startActivity(intent1);
    }


    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context ctxt, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            batteryView.setText(String.valueOf(level) + "%");
            if (level < 70) {
                new Notification(context, getResources().getString(R.string.batery_75), R.drawable.flashlight);
            } else if (level < 50) {
                new Notification(context, getResources().getString(R.string.batery_50), R.drawable.flashlight);
            } else if (level < 25) {
                new Notification(context, getResources().getString(R.string.batery_25), R.drawable.flashlight);
            } else if (level < 15) {
                new Notification(context, getResources().getString(R.string.batery_15), R.drawable.flashlight);
            } else if (level < 10) {
                new Notification(context, getResources().getString(R.string.batery_10), R.drawable.flashlight);
            } else if (level < 5) {
                new Notification(context, getResources().getString(R.string.batery_5), R.drawable.flashlight);
            }
        }
    };

    private void updateCountDownText() {
        int hours = (int) (timeLeft / 1000) / 3600;
        int minutes = (int) ((timeLeft / 1000) % 3600) / 60;
        int seconds = (int) (timeLeft / 1000) % 60;

        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        }
        textViewCountDown.setText(timeLeftFormatted);
    }

}
