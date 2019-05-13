package com.galacticCat.chatbleu;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.galacticCat.chatbleu.adapter.ContactAdapter;
import com.galacticCat.chatbleu.model.Contact;

import java.util.ArrayList;

public class ContactActivity extends AppCompatActivity implements ContactEditor.ExampleDialogListener {

    private ListView lvItem;
    private ContactAdapter adaptador;
    private ArrayList<Contact> listItems;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private TextView title;
    private ConstraintLayout background;

    private int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        setListeners();
        sharedPreferences = getApplicationContext().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        lvItem = findViewById(R.id.lvItem);
        GetArrayItems();
        adaptador = new ContactAdapter(this, listItems);
        lvItem.setAdapter(adaptador);
        lvItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("ANAME", listItems.get(position).getNombre());
                editor.putString("ANUMBER", listItems.get(position).getNumero());
                editor.apply();

                sharedPreferences.getString("ANUMBER", "");

                ContactEditor contactEditor = new ContactEditor();
                contactEditor.show(getSupportFragmentManager(), "example dialog");
                currentPosition = position;
            }
        });
    }

    private void GetArrayItems() {
        listItems = new ArrayList<>();

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
            listItems.add(new Contact(nombre[i], numero[i]));
        }

    }

    @Override
    public void applyTexts(String nombre, String numero) {
        listItems.set(currentPosition, new Contact(nombre, numero));
        adaptador = new ContactAdapter(this, listItems);
        lvItem.setAdapter(adaptador);

        String keyContact = "C" + (currentPosition + 1) + "";
        String keyNumber = "N" + (currentPosition + 1) + "";
        Toast.makeText(getApplicationContext(), keyContact + " " + keyNumber, Toast.LENGTH_LONG).show();
        editor.putString(keyContact, nombre);
        editor.putString(keyNumber, numero);
        editor.commit();
    }

    public void setListeners(){
        title = findViewById(R.id.textView);
        background = findViewById(R.id.backgroundContacts);

        Bundle extras = getIntent().getExtras();
        boolean campMode = extras.getBoolean("campMode");
        boolean day = extras.getBoolean("day");
        if (campMode) {
            if (day) {
                title.setTextColor((int) R.color.defaultBlack);
                background.setBackground(getResources().getDrawable(R.color.defaultWhite));
            } else {
                title.setTextColor((int) R.color.defaultWhite);
                background.setBackground(getResources().getDrawable(R.color.defaultBlack));
            }
        }
    }
}
