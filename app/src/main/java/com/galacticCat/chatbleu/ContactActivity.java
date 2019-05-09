package com.galacticCat.chatbleu;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.galacticCat.chatbleu.adapter.ContactAdapter;
import com.galacticCat.chatbleu.model.Contact;

import java.util.ArrayList;

public class ContactActivity extends AppCompatActivity {
    private ListView lvItem;
    private ContactAdapter adaptador;
    private ArrayList<Contact> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        GetArrayItems();
        lvItem = findViewById(R.id.lvItem);
        adaptador = new ContactAdapter(this, listItems);
        lvItem.setAdapter(adaptador);
    }

    private void GetArrayItems() {
        listItems = new ArrayList<>();

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String[] nombre = new String[5];
        String[] numero = new String[5];

        nombre[0] = sharedPreferences.getString("C1", "contacto1");
        nombre[1] = sharedPreferences.getString("C2", "contacto2");
        nombre[2] = sharedPreferences.getString("C3", "contacto3");
        nombre[3] = sharedPreferences.getString("C4", "contacto4");
        nombre[4] = sharedPreferences.getString("C5", "contacto5");

        numero[0] = sharedPreferences.getString("N1", "numero1");
        numero[1] = sharedPreferences.getString("N2", "numero2");
        numero[2] = sharedPreferences.getString("N3", "numero3");
        numero[3] = sharedPreferences.getString("N4", "numero4");
        numero[4] = sharedPreferences.getString("N5", "numero5");

        for (int i = 0; i < 5; i++) {
            listItems.add(new Contact(R.drawable.ic_launcher_background, nombre[i], numero[i]));
        }
    }

//    public void saveData () {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//
//        editor.putInt("STEPS", steps);
//        editor.putInt("DISTANCE", distance);
//        editor.putInt("WEIGHT", weight);
//        editor.putInt("HOURS", timeHours);
//        editor.putInt("MINUTES", timeMinutes);
//        editor.putInt("SECONDS", timeSeconds);
//
//        editor.commit();
//    }


//    @Override
//    public void applyTexts(String nombre, String numero, int position) {
//        listItems.add(position, new Entidad(nombre, numero));
//        lvItem.setAdapter(adaptador);
//    }
}

