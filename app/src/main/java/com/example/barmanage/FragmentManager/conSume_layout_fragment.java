package com.example.barmanage.FragmentManager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.barmanage.Adapter.importedAdapter;
import com.example.barmanage.Db_helper.SalesslipHelper;
import com.example.barmanage.R;
import com.example.barmanage.modle.importedItem;

import java.util.ArrayList;
import java.util.List;


public class conSume_layout_fragment extends Fragment {
    private View mView;
    private RecyclerView mRecyclerView;
    private SalesslipHelper mSalesslipHelper;
    private importedAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_con_sume_layout_fragment, container, false);
        mRecyclerView = mView.findViewById(R.id.fragment_conSume_RecycleView);
        //
        mSalesslipHelper = new SalesslipHelper(getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        adapter = new importedAdapter(getContext(), new importedAdapter.Listener() {
            @Override
            public void sendData(importedItem items) {

            }
        });
        adapter.setmList(getList());
        mRecyclerView.setAdapter(adapter);
        return mView;
    }

    private List<importedItem> getList() {
        List<importedItem> list = mSalesslipHelper.getAll();
        return list;
    }
}