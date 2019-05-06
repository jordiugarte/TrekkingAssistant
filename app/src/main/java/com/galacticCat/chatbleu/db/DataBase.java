package com.galacticCat.chatbleu.db;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.galacticCat.chatbleu.Constants;

public class DataBase extends SQLiteOpenHelper {

    public DataBase(Context context) {
        super(context,
                Constants.DATABASE_NAME,
                null,
                Constants.DATABASE_VERSION);
    }

    //Primer vez
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE usuarios (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " usuario VARCHAR NOT NULL," +
                " password VARCHAR NOT NULL," +
                " edad INTEGER," +
                " email VARCHAR NOT NULL," +
                " codigoUpb INTEGER NOT NULL)");
        Log.d("Database", "Created");
    }


    // Migracion
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}