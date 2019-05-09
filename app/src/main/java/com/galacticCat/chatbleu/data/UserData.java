package com.galacticCat.chatbleu.data;

import android.content.Context;
import android.content.SharedPreferences;

import static com.galacticCat.chatbleu.data.Stats.SHARED_PREFS;

public class UserData {

    private String userName;
    private int weight;
    private int age;
    private Context context;

    public UserData (Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        this.context = context;

        userName = sharedPreferences.getString("USER", "");
        weight = sharedPreferences.getInt("WEIGHT_USER", 0);
        age = sharedPreferences.getInt("AGE_USER", 0);
    }

    public void saveData () {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("USER", userName);
        editor.putInt("WEIGHT_USER", weight);
        editor.putInt("AGE_USER", age);

        editor.commit();
    }

    public String getUserName() {
        return userName;
    }

    public int getWeight() {
        return weight;
    }

    public int getAge() {
        return age;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
