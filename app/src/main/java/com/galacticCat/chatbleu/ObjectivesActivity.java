package com.galacticCat.chatbleu;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.solver.widgets.ConstraintAnchor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.galacticCat.chatbleu.adapter.ObjectivesAdapter;
import com.galacticCat.chatbleu.adapter.RecyclerItemClickListener;

import java.util.ArrayList;

import com.galacticCat.chatbleu.db.DataBase2Helper;
import com.galacticCat.chatbleu.db.DataBase3Helper;
import com.galacticCat.chatbleu.model.Item;
import com.galacticCat.chatbleu.model.ObjectivesItem;
import com.galacticCat.chatbleu.services.Notification;


import static com.galacticCat.chatbleu.data.Stats.SHARED_PREFS;

public class ObjectivesActivity extends AppCompatActivity implements RecyclerView.OnItemTouchListener {

    private RecyclerView listView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<ObjectivesItem> items;
    private TextView objectivesText;
    private TextView title;
    private TextView descriptionView;
    private TextView descriptionField;
    private Button addButton;
    private ConstraintLayout background;

    private DataBase3Helper dbobjetivos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objectives);
        dbobjetivos = new DataBase3Helper(getApplicationContext());
        items = populateList();
        setLiteners();

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
                new RecyclerItemClickListener(this, listView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        removeItem(items.get(position), position);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
    }

    public void setLiteners() {
        listView = (RecyclerView) findViewById(R.id.objectiveView);
        objectivesText = findViewById(R.id.descriptionField);
        addButton = findViewById(R.id.addObjectiveButton);
        background = findViewById(R.id.objectivesBackground);
        title = findViewById(R.id.title_list_objectives);
        descriptionView = findViewById(R.id.description);
        descriptionField = findViewById(R.id.descriptionField);

        Bundle extras = getIntent().getExtras();
        boolean campMode = extras.getBoolean("campMode");
        boolean day = extras.getBoolean("day");
        if (campMode) {
            if (day) {
                addButton.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.plus_black));
                objectivesText.setTextColor(getResources().getColor(R.color.defaultBlack));
                title.setTextColor(getResources().getColor(R.color.defaultBlack));
                background.setBackground(getResources().getDrawable(R.color.defaultWhite));
                descriptionView.setTextColor(getResources().getColor(R.color.defaultBlack));
                descriptionField.setTextColor(getResources().getColor(R.color.defaultBlack));
            } else {
                addButton.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.plus));
                objectivesText.setTextColor(getResources().getColor(R.color.defaultWhite));
                title.setTextColor(getResources().getColor(R.color.defaultWhite));
                background.setBackground(getResources().getDrawable(R.color.defaultBlack));
                descriptionView.setTextColor(getResources().getColor(R.color.defaultWhite));
                descriptionField.setTextColor(getResources().getColor(R.color.defaultWhite));
            }
        }
    }

    public void addObjective(String name) {
        ObjectivesItem item = new ObjectivesItem(name);
        items.add(item);
        listView.setAdapter(adapter);
        dbobjetivos.insert(item);
    }

    public void removeItem(ObjectivesItem item, int position) {
        String name = item.getObjetivos();
        items.remove(item);
        listView.setAdapter(adapter);
        dbobjetivos.delete(item);
        new Notification(this, name + " " + getResources().getString(R.string.removed_item), R.drawable.objectives);
    }

    private ArrayList<ObjectivesItem> populateList() {
        ArrayList<ObjectivesItem> l = (ArrayList<ObjectivesItem>) dbobjetivos.getAll();
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

