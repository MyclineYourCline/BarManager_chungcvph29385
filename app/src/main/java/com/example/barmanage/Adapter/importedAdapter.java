package com.example.barmanage.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barmanage.Db_helper.DrinksHelper;
import com.example.barmanage.Db_helper.UnitDao;
import com.example.barmanage.R;
import com.example.barmanage.modle.drinks;
import com.example.barmanage.modle.importedItem;
import com.example.barmanage.modle.unitProduct;

import java.util.ArrayList;
import java.util.List;


public class importedAdapter extends RecyclerView.Adapter<importedAdapter.itemImportViewHolder>
                    implements Filterable {
    private Context mContext;
    private List<importedItem> mList;
    private List<importedItem> mListOld;
    private Listener mListener;
    private DrinksHelper mDrinksHelper;
    private UnitDao mUnitDao;


    public importedAdapter(Context mContext, Listener mListener) {
        this.mContext = mContext;
        this.mListener = mListener;
        mDrinksHelper = new DrinksHelper(mContext);
        mUnitDao = new UnitDao(mContext);
    }
    public void setmList(List<importedItem> list){
        this.mList = list;
        this.mListOld = mList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public itemImportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_imported,parent, false);
        return new itemImportViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull itemImportViewHolder holder, int position) {
        final importedItem item = mList.get(position);
        if (item == null){
            return;
        }
        holder.drinkName.setText(getDrinkName(String.valueOf(item.getDrinkID())));
        holder.drinkPrice.setText(item.getDrinkPrice()+" VND");
        holder.unitName.setText(item.getUnitCount()+ " "+getUnitName(String.valueOf(item.getDrinkID())));
        holder.totalUnit.setText(item.sumMonny()+" VND");
        holder.dateAdd.setText(item.getDateAdd());
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.sendData(item);
            }
        });
    }

    private String getUnitName(String drinkID) {
        drinks mDrinks = mDrinksHelper.getByID(drinkID);
        unitProduct unitItem = mUnitDao.getByID(String.valueOf(mDrinks.getUnitID()));
        return unitItem.getUnitName();
    }

    private String getDrinkName(String drinkID) {
        drinks itemDrinks = mDrinksHelper.getByID(drinkID);
        return itemDrinks.getDinkName();
    }

    @Override
    public int getItemCount() {
        if (mList != null){
            return mList.size();
        }
        return 0;
    }
    public interface  Listener{
        void sendData(importedItem items);
    }
    public class itemImportViewHolder extends RecyclerView.ViewHolder{
        TextView drinkName, drinkPrice, unitName, totalUnit, dateAdd;
        ImageView update, delete;

        public itemImportViewHolder(@NonNull View itemView) {
            super(itemView);
            drinkName = itemView.findViewById(R.id.item_import_drinkName);
            drinkPrice = itemView.findViewById(R.id.item_import_drinkPrice);
            unitName = itemView.findViewById(R.id.item_import_unitName);
            totalUnit = itemView.findViewById(R.id.item_import_totalUnit);
            update = itemView.findViewById(R.id.item_import_update);
            delete = itemView.findViewById(R.id.item_import_delete);
            dateAdd = itemView.findViewById(R.id.item_import_dateAdd);
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
                    notifyDataSetChanged();
                }
                else {
                    List<importedItem> list = new ArrayList<>();
                    for (importedItem x: mListOld){
                        if (x.getDateAdd().toLowerCase().contains(search.toLowerCase())){
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
                mList = (List<importedItem>) results.values;
                notifyDataSetChanged();
            }
        };
    }
    public List<importedItem> getmList(){
        notifyDataSetChanged();
        return mList;
    }


}
