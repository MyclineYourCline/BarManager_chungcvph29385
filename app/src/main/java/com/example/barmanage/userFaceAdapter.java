package com.example.barmanage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class userFaceAdapter extends RecyclerView.Adapter<userFaceAdapter.userViewHolder>{
    private Context mContext;
    private List<main_userFace> list;
    private ClickItem clickItem;
    public interface ClickItem{
        void senData(main_userFace userFace);
    }
    public userFaceAdapter(Context mContext,ClickItem listener) {
        this.mContext = mContext;
        this.clickItem = listener;
    }
    public void setList(List<main_userFace> list){
        this.list = list;
        notifyDataSetChanged();

    }
    @NonNull
    @Override
    public userViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_face_user,parent,false);
        return new userViewHolder(mView);
    }
    @Override
    public void onBindViewHolder(@NonNull userViewHolder holder, int position) {
       final main_userFace item = list.get(position);
        if (item == null){
            return;
        }
        holder.mTextView.setText(item.getTextUser());
        holder.mImageView.setImageResource(item.getImage_user());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickItem.senData(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list != null){
            return list.size();
        }
        return 0;
    }

    public class userViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTextView;
        public userViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.img_face);
            mTextView = itemView.findViewById(R.id.text_face);
        }
    }
}
