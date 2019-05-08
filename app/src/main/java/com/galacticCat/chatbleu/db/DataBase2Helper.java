package com.galacticCat.chatbleu.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.galacticCat.chatbleu.model.Item;

import java.util.ArrayList;
import java.util.List;

public class DataBase2Helper {
    private SQLiteDatabase mDatabase;

    /**
     * Adicionar funciones de la DB para que no esten en las Activities
     *
     * @param context
     */
    public DataBase2Helper(Context context) {
        DataBaseMochila instancia = new DataBaseMochila(context);
        this.mDatabase = instancia.getWritableDatabase();
    }

    /**
     * Insertar el nuevo usuario en la DB
     *
     * @param item Obj de Usuario llenado desde Register
     */
    public void insert(Item item) {
        //Key: coliumn, Value: valor
        ContentValues contentValues = new ContentValues();
        contentValues.put("nombre", item.getName());
        contentValues.put("peso", item.getWeight());

        //Insertar el usuario
        this.mDatabase.insert("items", //Tabla
                null,
                contentValues); // Valores
        this.mDatabase.close(); //Cerrar
    }

    public List<Item> getAll() {
        List<Item> results = new ArrayList<>();
        Cursor cursor = this.mDatabase.rawQuery("SELECT " +
                " id," + //0
                " name," + //1
                " peso" + //5
                " FROM Items", null);

        if (cursor.moveToFirst()) {
            do {
                //Extraemos los datos
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String peso = cursor.getString(5);

                //Llnear objeto de tipo user
                Item item = new Item();

                item.setName(name);
                item.setWeight(peso);


                //Adicionar a la lista
                results.add(item);
            } while (cursor.moveToNext());
        }
        return results;
    }

    public int getCount() {
        Cursor cursor = this.mDatabase.rawQuery("SELECT COUNT(0) FROM usuarios", null);
        if (cursor.moveToFirst()) {
            int cantidad = cursor.getInt(0);
            return cantidad;
        } else {
            return 0;
        }
    }

    public void update(Item item) {

    }

    public void delete(int id) {
        String[] params = new String[1];
        params[0] = String.valueOf(id);

        mDatabase.delete("usuarios", "id=?", params);
    }
}


