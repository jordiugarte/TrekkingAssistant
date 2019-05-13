package com.galacticCat.chatbleu.adapter;
import com.galacticCat.chatbleu.R;
import com.galacticCat.chatbleu.model.Item;
import com.galacticCat.chatbleu.model.ObjectivesItem;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import org.w3c.dom.Text;
import java.util.ArrayList;
public class ObjectivesAdapter extends RecyclerView.Adapter<ObjectivesAdapter.ObjectiveViewHolder> {
    private ArrayList<ObjectivesItem> items;
    private static LayoutInflater inflater = null;
    public ObjectivesAdapter(ArrayList<ObjectivesItem> items) {
        this.items = items;
    }
    public static class ObjectiveViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ObjectiveViewHolder (View v) {
            super(v);
            name = (TextView)v.findViewById(R.id.objective_item_view);
        }
    }
    @Override
    public int getItemCount() {
        return items.size();
    }
    @Override
    public ObjectiveViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.objective_layout, viewGroup, false);
        return new ObjectiveViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ObjectiveViewHolder viewHolder, int i) {
        viewHolder.name.setText(items.get(i).getObjetivos());
    }
}