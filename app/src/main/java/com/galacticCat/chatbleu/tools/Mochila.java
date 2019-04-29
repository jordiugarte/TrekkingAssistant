package com.galacticCat.chatbleu.tools;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.galacticCat.chatbleu.ItemList;
import com.galacticCat.chatbleu.R;

import java.util.ArrayList;
import java.util.List;

public class Mochila extends AppCompatActivity {

    private Button addBtton;
    private TextView textView;
    private ListView listView;

    private List<ItemList> items;
    private ArrayList<String> itemList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mochila);

        addBtton = (Button)findViewById(R.id.addItemButton);
        textView = (TextView) findViewById(R.id.itemName);
        listView = (ListView)findViewById(R.id.listsView);

        itemList = new ArrayList<>();
        items = new ArrayList<>();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, itemList);


        addBtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemList.add(textView.getText().toString());
                adapter.notifyDataSetChanged();
                textView.setText("");

            }
        });
        listView.setAdapter(adapter);


    }

}
