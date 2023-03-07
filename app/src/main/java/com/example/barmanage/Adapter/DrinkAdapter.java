package com.example.barmanage.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barmanage.R;
import com.example.barmanage.modle.drinks;

import java.util.ArrayList;
import java.util.List;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.drinkViewHolder> implements Filterable {
    private Context mContext;
    private List<drinks> mList;
    private List<drinks> mListOld;
    private clickListener listener;

    public DrinkAdapter(Context mContext, clickListener listener) {
        this.mContext = mContext;
        this.listener = listener;
    }
    public void setmList(List<drinks> list){
        this.mList = list;
        this.mListOld = mList;
        notifyDataSetChanged();
    }


    public interface clickListener{
        void sendData(drinks item);
    }

    @NonNull
    @Override
    public drinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drink,parent,false);
        return new drinkViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull drinkViewHolder holder, int position) {
           final drinks item = mList.get(position);
            if (item == null){
                return;
            }
            holder.drinkName.setTypeface(Typeface.DEFAULT_BOLD);
            holder.drinkName.setText(item.getDinkName());
            holder.mUnitName.setText("Đơn vị: "+item.getUnitName());
            holder.mUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.sendData(item);
                }
            });
    }

    @Override
    public int getItemCount() {
        if (mList != null){
            return mList.size();
        }
        return 0;
    }

    public class drinkViewHolder extends RecyclerView.ViewHolder{
        TextView drinkName, mUnitName;
        ImageView mDelete, mUpdate;
        public drinkViewHolder(@NonNull View itemView) {
            super(itemView);
            drinkName = itemView.findViewById(R.id.drink_item_name);
            mUnitName = itemView.findViewById(R.id.drink_item_unit);
            mDelete = itemView.findViewById(R.id.drink_item_delete);
            mUpdate = itemView.findViewById(R.id.drink_item_update);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String search = constraint.toString();
                if (search.isEmpty()){
                    mList = mListOld;
                }
                else{
                    List<drinks> list = new ArrayList<>();
                    for (drinks x: mListOld){
                        if (x.getDinkName().toLowerCase().contains(search.toLowerCase())){
                            list.add(x);
                        }
                    }
                    mList = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mList = (List<drinks>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
