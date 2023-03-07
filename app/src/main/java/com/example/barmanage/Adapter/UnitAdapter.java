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

import com.example.barmanage.R;
import com.example.barmanage.modle.unitProduct;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class UnitAdapter extends RecyclerView.Adapter<UnitAdapter.unitItem> implements Filterable {
    private Context mContext;
    private List<unitProduct> list;
    private List<unitProduct> listOld;
    private listenerClickItem listenerClickItem;

    public UnitAdapter(Context mContext, listenerClickItem listener) {
        this.mContext = mContext;
        this.listenerClickItem = listener;
    }
    public void setList(List<unitProduct> mlist){
        this.list = mlist;
        this.listOld = list;
        notifyDataSetChanged();
    }
    public interface listenerClickItem{
        void sendItem(unitProduct item);
    }

    @NonNull
    @Override
    public unitItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_unit,parent,false);
        return new unitItem(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull unitItem holder, int position) {
        final unitProduct items = list.get(position);
        if (items == null){
            return;
        }
        holder.mName.setText(items.getUnitName());
        holder.mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenerClickItem.sendItem(items);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list != null){
          return  list.size();
        }
        return 0;
    }



    public class unitItem extends RecyclerView.ViewHolder{
     TextView mName;
     ImageView mUpdate, mDelete;
     public unitItem(@NonNull View itemView) {
         super(itemView);
         mName = itemView.findViewById(R.id.unit_item_name);
         mUpdate = itemView.findViewById(R.id.unit_item_update);
         mDelete = itemView.findViewById(R.id.unit_item_delete);
     }
 }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String search = constraint.toString();
                if (search.isEmpty()){
                   list = listOld;
                }
                else{
                    List<unitProduct> list1 = new ArrayList<>();
                    for (unitProduct x: listOld){
                        if (x.getUnitName().toLowerCase().contains(search.toLowerCase())){
                            list1.add(x);
                        }
                    }
                    list = list1;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = list;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list = (List<unitProduct>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
