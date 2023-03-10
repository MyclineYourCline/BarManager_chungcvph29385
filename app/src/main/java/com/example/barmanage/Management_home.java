package com.example.barmanage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.example.barmanage.Adapter.drinkManagerAdapter;
import com.example.barmanage.Db_helper.DrinksHelper;
import com.example.barmanage.Db_helper.ReceiptsHelper;
import com.example.barmanage.modle.importedItem;

import java.util.ArrayList;
import java.util.List;

public class Management_home extends AppCompatActivity {
    RecyclerView mRecyclerView;
    private ReceiptsHelper mReceiptsHelper;
    drinkManagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_home);
        getSupportActionBar().setTitle("Quản lý");
        mRecyclerView = findViewById(R.id.manager_recycleView);
        mReceiptsHelper = new ReceiptsHelper(Management_home.this);
        LinearLayoutManager manager = new LinearLayoutManager(Management_home.this);
        mRecyclerView.setLayoutManager(manager);
        adapter = new drinkManagerAdapter(Management_home.this, new drinkManagerAdapter.senData() {
            @Override
            public void sendImportedItem(importedItem item) {
                Intent intent = new Intent(Management_home.this,DetailItem.class);
                intent.putExtra("item",item);
                startActivity(intent);
            }
        });
        adapter.setmList(getList());
        mRecyclerView.setAdapter(adapter);


    }

    private List<importedItem> getList() {
        List<importedItem> list = mReceiptsHelper.getAll();
        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem menuItem = menu.findItem(R.id.search_action);
        SearchView mSearchView = (SearchView) menuItem.getActionView();
        mSearchView.setQueryHint("Nhập tên đồ uống...");
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}