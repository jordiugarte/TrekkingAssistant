package com.galacticCat.chatbleu;

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
import com.galacticCat.chatbleu.model.Item;

import java.util.ArrayList;

import com.galacticCat.chatbleu.services.Notification;
import com.google.gson.Gson;

public class MochilaActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Item> items = new ArrayList<>();

    private TextView nameText;
    private TextView weightText;
    private TextView currentWeightView;
    private Button addButton;
    private ItemAdapter adapter;

    private Gson gson = new Gson();

    private int weightInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mochila);
        populateListView();
        setLiteners();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String name = nameText.getText().toString();
                    int pueba =  Integer.parseInt(weightText.getText().toString());
                    String weight = weightText.getText().toString();
                    addItem(name, weight);
                } catch (Exception e){
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
    }

    public void populateListView() {
//        items.add(new Item("asdasd", "asdasd"));
//        adapter = new ItemAdapter(this, items);
//        listView.setAdapter(adapter);
    }

    public void setLiteners(){
        listView = (ListView)findViewById(R.id.listsView);
        nameText = findViewById(R.id.nameField);
        weightText = findViewById(R.id.weightField);
        addButton = findViewById(R.id.addItemButton);
        currentWeightView = findViewById(R.id.weightCurrent);
    }

    public void addItem(String name, String weight){
        items.add(new Item(name, weight, false));
        adapter = new ItemAdapter(this, items);
        weightInt += Integer.parseInt(weight);
        currentWeightView.setText(weightInt + "kg");
        listView.setAdapter(adapter);
    }

    public void removeItem(Item item) {
        String name = item.getName();
        weightInt -= Integer.parseInt(item.getWeight());
        currentWeightView.setText(weightInt + "kg");
        items.remove(item);
        adapter = new ItemAdapter(this, items);
        listView.setAdapter(adapter);
        new Notification(this, name + " removed", R.drawable.mochila);
    }
}
