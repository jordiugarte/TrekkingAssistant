package com.galacticCat.chatbleu.adapter;

import com.galacticCat.chatbleu.R;
import com.galacticCat.chatbleu.model.Item;
import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private ArrayList<Item> items;
    private static LayoutInflater inflater = null;

    public ItemAdapter(ArrayList<Item> items) {
        this.items = items;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView weight;

        public ItemViewHolder (View v) {
            super(v);
            name = (TextView)v.findViewById(R.id.nameItem);
            weight = (TextView)v.findViewById(R.id.weightItem);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_item, viewGroup, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder viewHolder, int i) {
        viewHolder.name.setText(items.get(i).getName());
        viewHolder.weight.setText(items.get(i).getWeight() + "kg");
    }

}
