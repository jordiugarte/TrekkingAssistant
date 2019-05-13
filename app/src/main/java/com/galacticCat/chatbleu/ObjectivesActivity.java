package com.galacticCat.chatbleu;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.galacticCat.chatbleu.adapter.ObjectivesAdapter;
import com.galacticCat.chatbleu.adapter.RecyclerItemClickListener;

import java.util.ArrayList;

import com.galacticCat.chatbleu.model.ObjectivesItem;
import com.galacticCat.chatbleu.services.Notification;


import static com.galacticCat.chatbleu.data.Stats.SHARED_PREFS;

public class ObjectivesActivity extends AppCompatActivity implements RecyclerView.OnItemTouchListener {

    private RecyclerView listView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<ObjectivesItem> items;
    private TextView objectivesText;
    private Button addButton;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objectives);

        items = populateList();
        setLiteners();
        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        layoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(layoutManager);
        adapter = new ObjectivesAdapter(items);
        listView.setAdapter(adapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = objectivesText.getText().toString();
                addObjective(name);
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
    }

    public void setLiteners() {
        listView = (RecyclerView) findViewById(R.id.objectiveView);
        objectivesText = findViewById(R.id.descriptionField);
        addButton = findViewById(R.id.addObjectiveButton);
    }

    public void addObjective(String name) {
        ObjectivesItem item = new ObjectivesItem(name);
        items.add(item);
        listView.setAdapter(adapter);
        new Notification(this, name + " " + getResources().getString(R.string.add), R.drawable.mochila);
    }

    public void removeItem(ObjectivesItem item) {
        String name = item.getObjetivos();
        items.remove(item);
        listView.setAdapter(adapter);
        new Notification(this, name + " " + getResources().getString(R.string.removed_item), R.drawable.mochila);
    }

    private ArrayList<ObjectivesItem> populateList(){
        ArrayList<ObjectivesItem> l = new ArrayList<ObjectivesItem>();
        return l;
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

