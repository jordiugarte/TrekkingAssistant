package com.galacticCat.chatbleu.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


import com.galacticCat.chatbleu.model.User;

 public class DataBaseHelper {
    private SQLiteDatabase mDatabase;

    /**
     * Adicionar funciones de la DB para que no esten en las Activities
     *
     * @param context
     */
    public DataBaseHelper(Context context) {
        DataBase instancia = new DataBase(context);
        this.mDatabase = instancia.getWritableDatabase();
    }

    /**
     * Insertar el nuevo usuario en la DB
     *
     * @param user Obj de Usuario llenado desde Register
     */
    public void insert(User user) {
        //Key: coliumn, Value: valor
        ContentValues contentValues = new ContentValues();
        contentValues.put("usuario", user.getNombreUsuario());
        contentValues.put("password", user.getPassword());
        contentValues.put("edad", user.getEdad());
        contentValues.put("email", user.getEmail());
        contentValues.put("peso", user.getPeso());

        //Insertar el usuario
        this.mDatabase.insert("usuarios", //Tabla
                null,
                contentValues); // Valores
        this.mDatabase.close(); //Cerrar
    }

    /**
     * Llamada desde Login Activity
     *
     * @param usuario  Usuario llenado el Login
     * @param password Password llenado el login
     * @return true/false Usuario y password existen en la db
     */
    public boolean login(String usuario, String password) {
        //Parametros en String array
        String[] params = new String[2];
        params[0] = usuario;
        params[1] = password;

        Cursor cursor = this.mDatabase.rawQuery("SELECT codigoUpb FROM usuarios" +
                " WHERE usuario=? AND password = ?", params);

        /*Cursor cursor = this.mDatabase.rawQuery("SELECT codigoUpb FROM usuarios" +
                " WHERE usuario=" + usuario + " AND password = " + password, null);*/

        if (cursor.moveToFirst()) {
            Log.d("CodigoUPB", "" + cursor.getInt(0));
            return true;
        } else {
            return false;
        }
    }

    public List<User> getAll() {
        List<User> results = new ArrayList<>();
        Cursor cursor = this.mDatabase.rawQuery("SELECT " +
                " id," + //0
                " usuario," + //1
                " password," + //2
                " edad," + //3
                " email," + //4
                " codigoUpb" + //5
                " FROM usuarios", null);

        if (cursor.moveToFirst()) {
            do {
                //Extraemos los datos
                int id = cursor.getInt(0);
                String usuario = cursor.getString(1);
                String password = cursor.getString(2); //No hacer esto solo por aprendizaje
                int edad = cursor.getInt(3);
                String email = cursor.getString(4);
                int codigoUpb = cursor.getInt(5);

                //Llnear objeto de tipo user
                User user = new User();

                user.setNombreUsuario(usuario);
                user.setPassword(password);
                user.setEdad(edad);
                user.setEmail(email);
                user.setPeso(codigoUpb);

                //Adicionar a la lista
                results.add(user);
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

    public void update(User user) {

    }

    public void delete(int id) {
        String[] params = new String[1];
        params[0] = String.valueOf(id);

        mDatabase.delete("usuarios", "id=?", params);
    }
}

