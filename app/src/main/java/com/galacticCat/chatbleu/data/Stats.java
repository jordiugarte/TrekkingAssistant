package com.galacticCat.chatbleu.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class Stats {

    private int steps;
    private int distance;
    private int weight;
    private int timeHours;
    private int timeMinutes;
    private int timeSeconds;

    private Context context;

    public static final String SHARED_PREFS = "sharedPrefs";

    public Stats (Context context) {
        this.context = context;

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        steps = sharedPreferences.getInt("STEPS", 0);
        distance = sharedPreferences.getInt("DISTANCE", 0);
        weight = sharedPreferences.getInt("WEIGHT", 0);
        timeHours = sharedPreferences.getInt("HOURS", 0);
        timeMinutes = sharedPreferences.getInt("MINUTES", 0);
        timeSeconds = sharedPreferences.getInt("SECONDS", 0);
    }

    public void saveData () {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("STEPS", steps);
        editor.putInt("DISTANCE", distance);
        editor.putInt("WEIGHT", weight);
        editor.putInt("HOURS", timeHours);
        editor.putInt("MINUTES", timeMinutes);
        editor.putInt("SECONDS", timeSeconds);

        editor.commit();
    }

    public int getSteps(){
        return steps;
    }

    public void setSteps(int s){
        steps = s;
    }

    public void setDistance(int d){
        distance = d;
    }

    public int getDistance(){
        return distance;
    }

    public void setWeight(int weight){
        this.weight = weight;
    }

    public void setTimeHours(int h) {
        timeHours = h;
    }

    public void setTimeMinutes(int m) {
        timeMinutes = m;
    }

    public void setTimeSeconds(int s) {
        timeSeconds = s;
    }

    public int getTimeHours() {
        return timeHours;
    }

    public int getTimeMinutes() {
        return timeMinutes;
    }

    public int getTimeSeconds() {
        return timeSeconds;
    }
}
