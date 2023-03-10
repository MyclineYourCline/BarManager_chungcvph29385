package com.example.barmanage;

import static android.util.Log.d;
import static com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.barmanage.Adapter.UnitAdapter;
import com.example.barmanage.Db_helper.UnitDao;
import com.example.barmanage.modle.unitProduct;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class Unit_management extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private UnitAdapter adapter;
    private FloatingActionButton mBtnAdd;
    private UnitDao mUnitDao;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_management);
        //
        mUnitDao = new UnitDao(Unit_management.this);
        getSupportActionBar().setTitle("Tên đơn vị");
        mRecyclerView = findViewById(R.id.unit_recycleView);
        adapter = new UnitAdapter(Unit_management.this, new UnitAdapter.listenerClickItem() {
            @Override
            public void sendItem(unitProduct item) {
                ClickImageDelete(item);
            }
        });
        getAdapter(getList());
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0){
                    mBtnAdd.hide();
                }
                else{
                    mBtnAdd.show();
                }
            }
        });
        mBtnAdd = findViewById(R.id.unit_add);
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItems();
            }
        });

    }
    private void ClickImageDelete(unitProduct items){
        Dialog dialog = new Dialog(Unit_management.this, androidx.appcompat.R.style.Theme_AppCompat_Dialog);
        dialog.setContentView(R.layout.dialog_update_unit);
        EditText mNameUnit;
        Button mBtnUpdate, mBtn_cancel;
        mNameUnit = dialog.findViewById(R.id.unit_update_name);
        mBtnUpdate = dialog.findViewById(R.id.unit_update_btnThem);
        mBtn_cancel = dialog.findViewById(R.id.unit_update_btnHuy);
        mNameUnit.setText(items.getUnitName());
        mBtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNameUnit.getText().toString().isEmpty()){
                    mNameUnit.setError("Không được để trống phần tên");
                    return;
                }
                else{
                    unitProduct itemsUpdate = new unitProduct();
                    itemsUpdate.setUnitID(items.getUnitID());
                    itemsUpdate.setUnitName(mNameUnit.getText().toString().trim());
                    mUnitDao.updateUnit(itemsUpdate);
                    getAdapter(getList());
                    dialog.cancel();
                    Toast.makeText(Unit_management.this, "Update thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mBtn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }
    private void addItems(){
        Dialog dialog = new Dialog(Unit_management.this, androidx.appcompat.R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        dialog.setContentView(R.layout.dialog_add_unit);
        EditText mNameUnit;
        Button mBtndd ,mBtn_cancelAdd;
        mNameUnit = dialog.findViewById(R.id.unit_add_name);
        mBtndd = dialog.findViewById(R.id.unit_add_btnThem);
        mBtn_cancelAdd = dialog.findViewById(R.id.unit_add_btnHuy);
        mBtndd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNameUnit.getText().toString().isEmpty()){
                    mNameUnit.setError("Không được để trống phần tên");
                    return;
                }
                else{
                    unitProduct items = new unitProduct();
                    items.setUnitName(mNameUnit.getText().toString().trim());
                    mUnitDao.insertUnit(items);
                    d("ca" + "chung", "onClick: "+items.getUnitID());
                    dialog.cancel();
                    Toast.makeText(Unit_management.this, "ADD thành công", Toast.LENGTH_SHORT).show();
                    getAdapter(getList());
                }
            }
        });
        mBtn_cancelAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }


    private List<unitProduct> getList() {
        List<unitProduct> list = mUnitDao.getAll();
        return list;
    }
    public void getAdapter(List<unitProduct> mlist){
        adapter.setList(mlist);
        adapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem menuItem = menu.findItem(R.id.search_action);
        androidx.appcompat.widget.SearchView searchView =
                (androidx.appcompat.widget.SearchView) menuItem.getActionView();
        searchView.setQueryHint("Tên đơn vị");
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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