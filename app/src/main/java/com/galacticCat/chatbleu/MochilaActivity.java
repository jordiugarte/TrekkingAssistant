package com.galacticCat.chatbleu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.galacticCat.chatbleu.adapter.ItemAdapter;
import com.galacticCat.chatbleu.data.Stats;
import com.galacticCat.chatbleu.db.DataBase2Helper;
import com.galacticCat.chatbleu.db.DataBaseMochila;
import com.galacticCat.chatbleu.model.Item;

import java.util.ArrayList;

import com.galacticCat.chatbleu.services.Notification;
import com.google.gson.Gson;

import static com.galacticCat.chatbleu.data.Stats.SHARED_PREFS;

public class MochilaActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Item> items = new ArrayList<>();

    private TextView nameText;
    private TextView weightText;
    private TextView currentWeightView;
    private Button addButton;
    private ItemAdapter adapter;
    private DataBase2Helper dbmochila;
    private Context mContext = this;

    private float weightFloat;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mochila);
        dbmochila = new DataBase2Helper(this.mContext);

        setLiteners();
        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        items = populateListView();
        adapter = new ItemAdapter(this, items);
        listView.setAdapter(adapter);

        weightFloat = sharedPreferences.getFloat("W", 0);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String name = nameText.getText().toString();
                    int pueba = Integer.parseInt(weightText.getText().toString());
                    String weight = weightText.getText().toString();
                    addItem(name, weight);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Ingresa datos validos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = items.get(position);
                removeItem(item);
            }
        });
        currentWeightView.setText(weightFloat + "kg");

    }

    public ArrayList<Item> populateListView() {
        ArrayList<Item> l = (ArrayList<Item>) dbmochila.getAll();
        return l;
    }

    public void setLiteners() {
        listView = (ListView) findViewById(R.id.listsView);
        nameText = findViewById(R.id.nameField);
        weightText = findViewById(R.id.weightField);
        addButton = findViewById(R.id.addItemButton);
        currentWeightView = findViewById(R.id.weightCurrent);
    }

    public void addItem(String name, String weight) {
        items.add(new Item(name, weight, false));
        float weightItem = Float.parseFloat(weight);
        adapter = new ItemAdapter(this, items);
        weightFloat += weightItem;
        listView.setAdapter(adapter);
        Item item = new Item(name, weight, false);
        dbmochila.insert(item);
        addWeightPreferences();
    }

    public void removeItem(Item item) {
        String name = item.getName();
        float weightItem = Float.parseFloat(item.getWeight());
        weightFloat -= weightItem;
        items.remove(item);
        listView.setAdapter(adapter);
        new Notification(this, name + " removed", R.drawable.mochila);
        dbmochila.delete(item);
        addWeightPreferences();
    }

    public void addWeightPreferences() {
//        Stats stats = new Stats(getApplicationContext());
//        stats.setWeight(weightFloat);
//        stats.saveData();
        currentWeightView.setText(weightFloat + "kg");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("W", weightFloat);
        editor.commit();
    }
}
