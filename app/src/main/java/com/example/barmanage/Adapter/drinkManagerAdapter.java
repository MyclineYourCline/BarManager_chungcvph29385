package com.example.barmanage.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barmanage.Db_helper.DrinksHelper;
import com.example.barmanage.Db_helper.UnitDao;
import com.example.barmanage.R;
import com.example.barmanage.modle.drinks;
import com.example.barmanage.modle.importedItem;

import java.util.ArrayList;
import java.util.List;

public class drinkManagerAdapter extends RecyclerView.Adapter<drinkManagerAdapter.dinkManagerViewholder>
                                    implements Filterable {
    private Context mContext;
    private List<importedItem> mList;
    private List<importedItem> mListOld;
    private senData listener;
    private DrinksHelper mDrinksHelper;
    private UnitDao mUnitDao;

    public drinkManagerAdapter(Context mContext, senData listener) {
        this.mContext = mContext;
        this.listener = listener;
        mDrinksHelper = new DrinksHelper(mContext);
        mUnitDao = new UnitDao(mContext);
    }
    public void setmList(List<importedItem> mList){
        this.mList = mList;
        this.mListOld = this.mList;
        notifyDataSetChanged();
    }

    public interface senData{
        void sendImportedItem(importedItem item);
    }

    @NonNull
    @Override
    public dinkManagerViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_manager, parent,false);

        return new dinkManagerViewholder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull dinkManagerViewholder holder, int position) {
        importedItem item = mList.get(position);
        if (item ==  null){
            return;
        }
        holder.mDrinkName.setText(getDrinkName(String.valueOf(item.getDrinkID())));
        holder.mRemaining.setText("Hiện tại còn: "+getRemanining());
        holder.mDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.sendImportedItem(item);
            }
        });

    }

    private String getRemanining() {
        return String.valueOf(1);
    }

    private String getDrinkName(String DrinkID) {
        drinks result = mDrinksHelper.getByID(DrinkID);
        return result.getDinkName();
    }

    @Override
    public int getItemCount() {
        if (mList != null){
            return mList.size();
        }
        return 0;
    }

    public final class dinkManagerViewholder extends RecyclerView.ViewHolder {
        TextView mDrinkName, mRemaining,mDetail;
        public dinkManagerViewholder(@NonNull View itemView) {
            super(itemView);
            mDrinkName = itemView.findViewById(R.id.item_manager_drinkName);
            mRemaining = itemView.findViewById(R.id.item_manager_totalCount);
            mDetail = itemView.findViewById(R.id.item_manager_detail);
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
                    List<importedItem> list = new ArrayList<>();
                    for (importedItem item : mListOld){
                        if (getDrinkName(String.valueOf(item.getDrinkID())).toLowerCase().contains(search.toLowerCase())){
                            list.add(item);
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
                    mList = (List<importedItem>) results.values;
                    notifyDataSetChanged();
            }
        };
    }
}
