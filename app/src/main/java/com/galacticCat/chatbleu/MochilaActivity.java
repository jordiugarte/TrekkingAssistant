package com.galacticCat.chatbleu;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.galacticCat.chatbleu.adapter.ItemAdapter;
import com.galacticCat.chatbleu.model.Item;

import java.util.ArrayList;
import java.util.List;

public class MochilaActivity extends AppCompatActivity {

    //List Components
    private Context context;
    private ListView itemsListView;
    private ItemAdapter itemAdapter;
    private List<Item> itemList = new ArrayList<>();

    //Views
    private TextView nameItemView;
    private TextView weightItemView;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mochila);
        setListeners();

        context = getApplicationContext();
        itemAdapter = new ItemAdapter(context, GetArrayItems());
        itemsListView.setAdapter(itemAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameItemView.getText().toString();
                int weight = 0;
                try {
                    weight = Integer.parseInt(weightItemView.getText().toString());
                } catch (Exception e){
                    Toast.makeText(context, "Ingresa un valor válido para el peso", Toast.LENGTH_SHORT).show();
                }

                itemList.add(new Item(name, weight, false));
                itemAdapter.notifyDataSetChanged();
            }
        });
    }


    public void setListeners(){
        nameItemView = (TextView)findViewById(R.id.itemName);
        weightItemView = (TextView)findViewById(R.id.itemWeight);
        addButton = (Button)findViewById(R.id.addItemButton);
        itemsListView = (ListView)findViewById(R.id.listsView);
    }

    private ArrayList<Item> GetArrayItems(){
        ArrayList<Item> listItems = new ArrayList<>();
        return listItems;
    }
}
