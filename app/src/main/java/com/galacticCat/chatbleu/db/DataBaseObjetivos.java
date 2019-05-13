package com.galacticCat.chatbleu.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.galacticCat.chatbleu.Constants;

public class DataBaseObjetivos extends SQLiteOpenHelper {

    public DataBaseObjetivos(Context context) {
        super(context,
                Constants.DATABASE3_NAME,
                null,
                Constants.DATABASE3_VERSION);
    }

    //Primer vez
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE objetivos (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "name VARCHAR NOT NULL)");
        Log.d("Database3", "Created");
    }


    // Migracion
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
