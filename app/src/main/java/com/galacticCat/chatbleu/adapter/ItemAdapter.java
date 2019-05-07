package com.galacticCat.chatbleu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.TextView;

import com.galacticCat.chatbleu.MochilaActivity;
import com.galacticCat.chatbleu.R;
import com.galacticCat.chatbleu.model.Item;

import java.util.ArrayList;

public class ItemAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Item> listItem;

    public ItemAdapter(Context context, ArrayList<Item> listItems) {
        this.context = context;
        this.listItem = listItems;
    }

    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public Object getItem(int position) {
        return listItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Item item = (Item) getItem(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.food_item, null);
        TextView name = (TextView)convertView.findViewById(R.id.nameItem);
        TextView weight = (TextView)convertView.findViewById(R.id.weightItem);
        CheckBox checkBox = (CheckBox)convertView.findViewById(R.id.checkBox);

        name.setText(item.getName());
        weight.setText(item.getWeight());
        checkBox.setChecked(item.isCheck());

        return convertView;
    }
}
