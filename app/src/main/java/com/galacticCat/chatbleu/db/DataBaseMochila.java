package com.galacticCat.chatbleu.db;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.galacticCat.chatbleu.Constants;

public class DataBaseMochila extends SQLiteOpenHelper {

    public DataBaseMochila(Context context) {
        super(context,
                Constants.DATABASE2_NAME,
                null,
                Constants.DATABASE2_VERSION);
    }

    //Primer vez
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE items (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "name VARCHAR NOT NULL," +
                "peso FLOAT NOT NULL)");
        Log.d("Database2", "Created");
    }


    // Migracion
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}