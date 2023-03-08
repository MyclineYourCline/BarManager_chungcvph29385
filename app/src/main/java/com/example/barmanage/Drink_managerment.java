package com.example.barmanage;

import static android.util.Log.d;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barmanage.Adapter.DrinkAdapter;
import com.example.barmanage.Adapter.unitProductNameAdpater;
import com.example.barmanage.modle.drinks;
import com.example.barmanage.modle.unitProduct;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Drink_managerment extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private DrinkAdapter adapter;
    private FloatingActionButton mButton_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_managerment);
        getSupportActionBar().setTitle("Tên đồ uống");
        mRecyclerView = findViewById(R.id.drink_recycleView);
        mButton_add = findViewById(R.id.drink_add);
        String name1 = " djt con me may may thang link ";
        adapter = new DrinkAdapter(Drink_managerment.this, new DrinkAdapter.clickListener() {
            @Override
            public void sendData(drinks item) {
                upDateDrinks(item);
            }
        });
        getRecylerView(setList());
        mButton_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(Drink_managerment.this, androidx.appcompat.R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                dialog.setContentView(R.layout.dialog_add_drink);
                TextView mDrinkName = dialog.findViewById(R.id.diaLog_add_drinkName);
                Spinner mSpinner = dialog.findViewById(R.id.dialog_add_spinerDinks);
                Button mButton_add = dialog.findViewById(R.id.drinks_add_btnThem);
                Button mButton_huy = dialog.findViewById(R.id.drinks_add_btnHuy);
                unitProductNameAdpater adpater1 = new unitProductNameAdpater(
                        Drink_managerment.this,R.layout.item_selected,getListNameUnit());
                mSpinner.setAdapter(adpater1);
                mSpinner.setSelection(0);
                mButton_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mDrinkName.getText().toString().isEmpty()
                            ){
                            mDrinkName.setError("Không được để trốn tên");
                            return;
                        }
                        if (mSpinner.getSelectedItemPosition() == 0){
                            Toast.makeText(Drink_managerment.this,
                                    "Bạn chưa chọn đon vị tính", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        dialog.cancel();
                        unitProduct item = (unitProduct) mSpinner.getSelectedItem();
                        Toast.makeText(Drink_managerment.this, "Thêm thành công",
                                Toast.LENGTH_SHORT).show();

                    }
                });
                mButton_huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy >0){
                    mButton_add.hide();
                }
                else{
                    mButton_add.show();
                }
            }
        });
    }
    public void getRecylerView(List<drinks> list){
        adapter.setmList(list);
        mRecyclerView.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem menuItem = menu.findItem(R.id.search_action);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Tên đồ uống...");
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
    private void upDateDrinks(drinks item){
        Dialog dialog = new Dialog(Drink_managerment.this,
                androidx.appcompat.R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        dialog.setContentView(R.layout.dialong_update_drinks);
        EditText mDrinkName = dialog.findViewById(R.id.diaLog_update_drinkName);
        Spinner mSpinner = dialog.findViewById(R.id.dialog_update_spinerDinks);
        Button mBtn_update = dialog.findViewById(R.id.drinks_update_btnThem);
        Button mBtn_cancel = dialog.findViewById(R.id.drinks_update_btnHuy);
        mDrinkName.setText(item.getDinkName());
        unitProductNameAdpater adpater = new unitProductNameAdpater(Drink_managerment.this,
                R.layout.item_selected,getListNameUnit(item.getUnitName()));
        mSpinner.setAdapter(adpater);
        mSpinner.setSelection(1);
        String valueToSelect = item.getUnitName();
        mBtn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        mBtn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrinkName.getText().toString().isEmpty()){
                    mDrinkName.setError("Không được để trống phần tên");
                    return;
                }
                if (mSpinner.getSelectedItemPosition() == 0){
                    Toast.makeText(Drink_managerment.this, "Bạn chưa chọn đơn vị tính",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                dialog.cancel();
                Toast.makeText(Drink_managerment.this, "Cập nhập thành công",
                        Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }

    private List getListNameUnit(String unitName) {
        List<unitProduct> list = new ArrayList<>();
        list.add(new unitProduct("Đơn vị tính"));
        list.add(new unitProduct(unitName));
        list.add(new unitProduct("lo"));
        list.add(new unitProduct("lon"));
        list.add(new unitProduct("djtme"));
        return list;
    }
    public List<drinks> setList(){
        List<drinks> list = new ArrayList<>();
        list.add(new drinks("cocacola","lon"));
        list.add(new drinks("number one","chai"));
        list.add(new drinks("dau tay","chai"));
        list.add(new drinks("sting","thung"));
        list.add(new drinks("bear","kien"));
        list.add(new drinks("tiger","lit"));
        list.add(new drinks("bo huc","lit"));
        list.add(new drinks("nuoc dai","binh"));
        list.add(new drinks("nuoc oi","can"));
        list.add(new drinks("vcl","can"));
        list.add(new drinks("nhu quan que","xach"));
        list.add(new drinks("nuoc nguoi yeu cu","khoi"));
        return list;
    }
    private List getListNameUnit() {
        List<unitProduct> mlist = new ArrayList<>();
        mlist.add(new unitProduct("Đơn vị tính"));
        mlist.add(new unitProduct("chai"));
        mlist.add(new unitProduct("lo"));
        mlist.add(new unitProduct("lon"));
        mlist.add(new unitProduct("djtme"));
        mlist.add(new unitProduct("buoi"));
        mlist.add(new unitProduct("Đơn vị tính"));
        mlist.add(new unitProduct("chai"));
        mlist.add(new unitProduct("lo"));
        mlist.add(new unitProduct("lon"));
        mlist.add(new unitProduct("djtme"));
        mlist.add(new unitProduct("buoi"));
        return mlist;
    }
}