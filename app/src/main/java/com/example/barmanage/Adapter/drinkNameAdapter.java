package com.example.barmanage.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.barmanage.R;
import com.example.barmanage.modle.drinks;

import java.util.List;

public class drinkNameAdapter extends ArrayAdapter<drinks> {
    private int position;
    public drinkNameAdapter(@NonNull Context context, int resource, @NonNull List<drinks> objects) {
        super(context, resource, objects);
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        this.position = position;
        convertView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_selected,parent,false);
        TextView drinkName = convertView.findViewById(R.id.unitName_item_selected);
        drinks item = this.getItem(position);
        if (item != null){
            drinkName.setText(item.getDinkName());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        this.position = position;
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spiner_category,parent,false);
        TextView drinkName = convertView.findViewById(R.id.category_textView);
        drinks item = this.getItem(position);
        if (item!=null){
            drinkName.setText(item.getDinkName());
        }

        return convertView;
    }
}
