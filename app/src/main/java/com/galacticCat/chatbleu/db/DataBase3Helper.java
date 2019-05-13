package com.galacticCat.chatbleu.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.galacticCat.chatbleu.model.Item;
import com.galacticCat.chatbleu.model.ObjectivesItem;

import java.util.ArrayList;
import java.util.List;

public class DataBase3Helper {
    private SQLiteDatabase mDatabase;

    /**
     * Adicionar funciones de la DB para que no esten en las Activities
     *
     * @param context
     */
    public DataBase3Helper(Context context) {
        DataBaseObjetivos instancia = new DataBaseObjetivos(context);
        this.mDatabase = instancia.getWritableDatabase();
    }

    /**
     * Insertar el nuevo usuario en la DB
     *
     * @param item Obj de Usuario llenado desde Register
     */
    public void insert(ObjectivesItem item) {
        //Key: coliumn, Value: valor
        String iName =  item.getObjetivos();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", iName);

        long inserted = this.mDatabase.insert("objetivos",null,contentValues);
        //this.mDatabase.close();
        //Insertar el itemthis.mDatabase.insert ("items",null, contentValues);
        //this.mDatabase.execSQL("INSERT INTO items(name, weight) VALUES("+iName+","+iWeight+")");
    }

    public List<ObjectivesItem> getAll() {
        List<ObjectivesItem> results = new ArrayList<>();
        Cursor cursor = this.mDatabase.rawQuery("SELECT " +
                " id," + //0
                " name" + //1
                " FROM Objetivos", null);

        if (cursor.moveToFirst()) {
            do {
                //Extraemos los datos
                int id = cursor.getInt(0);
                String name = cursor.getString(1);

                //Llnear objeto de tipo user
                ObjectivesItem item = new ObjectivesItem();

                item.setObjetivos(name);


                //Adicionar a la lista
                results.add(item);
            } while (cursor.moveToNext());
        }
        return results;
    }

    public int getCount() {
        Cursor cursor = this.mDatabase.rawQuery("SELECT COUNT(0) FROM objetivos", null);
        if (cursor.moveToFirst()) {
            int cantidad = cursor.getInt(0);
            return cantidad;
        } else {
            return 0;
        }
    }

    public void update(Item item) {

    }

    public void delete(ObjectivesItem item) {
        String name = item.getObjetivos();
        /*String[] params = new String[1];
        params[0] = name;

        int deleted = mDatabase.delete("items", "name=?", params);*/
        int deleted = mDatabase.delete("objetivos", "name='"+name+"'", null);
        Log.d("Deleted items", ""+deleted);
    }
}


