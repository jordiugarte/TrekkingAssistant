package com.galacticCat.chatbleu.tools;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.galacticCat.chatbleu.ItemList;
import com.galacticCat.chatbleu.R;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class Mochila extends AppCompatActivity {

    private Button addBtton;
    private TextView nameView;
    private TextView weightView;
    private ListView listView;

    private String name;
    private float weight;

    private ArrayList<String> itemList;

    final String mTitle[] = {"Barra energética", "Polera", "Pantalón", "Sleeping", "Bolsa de manís", "Botellón de agua"};
    final float mWeight[] = {0.1f, 0.2f, 0.3f, 0.5f, 0.6f, 1.5f};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mochila);

        addBtton = (Button)findViewById(R.id.addItemButton);
        nameView = (TextView) findViewById(R.id.itemName);
        weightView = (TextView) findViewById(R.id.itemWeight);
        listView = (ListView)findViewById(R.id.listsView);

        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);

        itemList = new ArrayList<>();

        for(int i = 0; i < itemList.size(); i++){
            weight += mWeight[i];
        }

        addBtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameView.getText().toString();
                weight = Integer.parseInt(weightView.getText().toString());

                itemList.add(name);
                itemList.add(weight + "kg");

                nameView.setText("");
                weightView.setText("");

            }
        });



    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mTitle.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.food_item, null);
            TextView textView_name = (TextView)convertView.findViewById(R.id.nameItem);
            TextView textView_weight = (TextView)convertView.findViewById(R.id.weightItem);

            textView_name.setText(mTitle[position]);
            textView_weight.setText(mWeight[position]+"kg");

            return convertView;
        }
    }
}
