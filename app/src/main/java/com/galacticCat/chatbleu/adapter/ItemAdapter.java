package com.galacticCat.chatbleu.adapter;

import com.galacticCat.chatbleu.R;
import com.galacticCat.chatbleu.model.Item;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemAdapter extends BaseAdapter {

    private Activity context;
    private ArrayList<Item> items;
    private static LayoutInflater inflater = null;

    public ItemAdapter(Activity context, ArrayList<Item> items) {
        this.context = context;
        this.items = items;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        itemView = (itemView == null) ? inflater.inflate(R.layout.layout_item, null): itemView;
        TextView textViewName = (TextView)itemView.findViewById(R.id.nameItem);
        TextView textViewWeight = (TextView)itemView.findViewById(R.id.weightItem);
        CheckBox checkboxView = (CheckBox) itemView.findViewById(R.id.checkBox);
        Item selectedItem = items.get(position);
        textViewName.setText(selectedItem.getName());
        textViewWeight.setText(selectedItem.getWeight() + "kg");
        checkboxView.setChecked(false);
        return itemView;
    }

}
