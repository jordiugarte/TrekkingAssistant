package com.galacticCat.chatbleu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.galacticCat.chatbleu.adapter.ItemAdapter;
import com.galacticCat.chatbleu.adapter.RecyclerItemClickListener;
import com.galacticCat.chatbleu.data.Stats;
import com.galacticCat.chatbleu.db.DataBase2Helper;
import com.galacticCat.chatbleu.db.DataBaseMochila;
import com.galacticCat.chatbleu.model.Item;

import java.util.ArrayList;

import com.galacticCat.chatbleu.services.Notification;
import com.google.gson.Gson;


import static com.galacticCat.chatbleu.data.Stats.SHARED_PREFS;

public class MochilaActivity extends AppCompatActivity implements RecyclerView.OnItemTouchListener {

    private RecyclerView listView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<Item> items;

    private TextView nameText;
    private TextView weightText;

    private TextView currentWeightView;
    private Button addButton;

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

        layoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(layoutManager);
        adapter = new ItemAdapter(items);
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
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.data_alert), Toast.LENGTH_SHORT).show();
                }
            }
        });

        listView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, listView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        removeItem(items.get(position));
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
        currentWeightView.setText(weightFloat + "kg");
    }

    public ArrayList<Item> populateListView() {
        ArrayList<Item> l = (ArrayList<Item>) dbmochila.getAll();
        return l;
    }

    public void setLiteners() {
        listView = (RecyclerView) findViewById(R.id.listView);
        nameText = findViewById(R.id.nameField);
        weightText = findViewById(R.id.weightField);
        addButton = findViewById(R.id.addItemButton);
        currentWeightView = findViewById(R.id.weightCurrent);
//        listView.setHasFixedSize(true);
    }

    public void addItem(String name, String weight) {
        Item item = new Item(name, weight);
        items.add(item);
        float weightItem = Float.parseFloat(weight);
        adapter = new ItemAdapter(items);
        weightFloat += weightItem;
        listView.setAdapter(adapter);
        dbmochila.insert(item);
        addWeightPreferences();
    }

    public void removeItem(Item item) {
        String name = item.getName();
        float weightItem = Float.parseFloat(item.getWeight());
        weightFloat -= weightItem;
        items.remove(item);
        listView.setAdapter(adapter);
        new Notification(this, name + getResources().getString(R.string.removed_item), R.drawable.mochila);
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

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean b) {

    }
}
