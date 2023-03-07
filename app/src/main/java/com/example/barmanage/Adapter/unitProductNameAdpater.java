package com.example.barmanage.Adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.barmanage.R;
import com.example.barmanage.modle.unitProduct;

import java.util.List;

public class unitProductNameAdpater extends ArrayAdapter<unitProduct> {
    private int position;
    public unitProductNameAdpater(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        this.position = position;
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected,parent,false);
        TextView mUnitName = convertView.findViewById(R.id.unitName_item_selected);
        unitProduct  items =  this.getItem(position);
        if (items!= null){
            mUnitName.setText(items.getUnitName());
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spiner_category,parent,false);
        TextView mUnitName = convertView.findViewById(R.id.category_textView);
        unitProduct item = this.getItem(position);
        if (item != null){
            mUnitName.setText(item.getUnitName());
        }
        return convertView;
    }

}
