package com.galacticCat.chatbleu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.galacticCat.chatbleu.R;
import com.galacticCat.chatbleu.model.Contact;

import java.util.ArrayList;

public class ContactAdapter extends BaseAdapter {
    private Context context;

    public ContactAdapter(Context context, ArrayList<Contact> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    private ArrayList<Contact>listItems;
    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Contact Item= (Contact) getItem(position);

        convertView= LayoutInflater.from(context).inflate(R.layout.contact_item,null);
        ImageView imgFoto = convertView.findViewById(R.id.imgFoto);
        TextView nombre=convertView.findViewById(R.id.nombre);
        //  TextView numero = convertView.findViewById(R.id.numero);
        imgFoto.setImageResource(Item.getImgFoto());
        nombre.setText(Item.getNombre());
        //  numero.setText(Item.getNumero());
        return convertView;
    }
}

