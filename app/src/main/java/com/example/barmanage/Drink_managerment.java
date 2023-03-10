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
import com.example.barmanage.Db_helper.DrinksHelper;
import com.example.barmanage.Db_helper.UnitDao;
import com.example.barmanage.modle.drinks;
import com.example.barmanage.modle.unitProduct;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Drink_managerment extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private DrinkAdapter adapter;
    private FloatingActionButton mButton_add;
    private UnitDao mUnitDao;
    private DrinksHelper drinksHelperDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_managerment);
        getSupportActionBar().setTitle("Tên đồ uống");
        mRecyclerView = findViewById(R.id.drink_recycleView);
        mButton_add = findViewById(R.id.drink_add);
        //
        mUnitDao = new UnitDao(Drink_managerment.this);
        drinksHelperDao = new DrinksHelper(Drink_managerment.this);
        //
        adapter = new DrinkAdapter(Drink_managerment.this, new DrinkAdapter.clickListener() {
            @Override
            public void sendData(drinks item) {
                upDateDrinks(item);

            }
        });
        getAdapter(getListDrinks());
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
                            mDrinkName.setError("Không được để trống tên");
                            return;
                        }
                        if (mSpinner.getSelectedItemPosition() == 0){
                            Toast.makeText(Drink_managerment.this,
                                    "Bạn chưa chọn đon vị tính", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        dialog.cancel();
                        unitProduct item = (unitProduct) mSpinner.getSelectedItem();
                        drinks itemDrink = new drinks();
                        itemDrink.setUnitID(Integer.parseInt(item.getUnitID()));
                        itemDrink.setDinkName(mDrinkName.getText().toString().trim());
                        drinksHelperDao.insertDrinks(itemDrink);
                        //
                        getAdapter(getListDrinks());
                        //
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
        //

        //
        mDrinkName.setText(item.getDinkName());
        unitProductNameAdpater adapter = new unitProductNameAdpater(Drink_managerment.this,
                R.layout.item_selected,getListNameUnit(String.valueOf(item.getUnitID())));
        mSpinner.setAdapter(adapter);
        mSpinner.setSelection(0);
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
                if (mSpinner.getSelectedItemPosition() == 1){
                    Toast.makeText(Drink_managerment.this, "Bạn chưa chọn đơn vị tính",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                //
               unitProduct unitItem = (unitProduct) mSpinner.getSelectedItem();
                drinks items = new drinks();
                items.setDrinkID(item.getDrinkID());
                items.setDinkName(mDrinkName.getText().toString().trim());//
                items.setUnitID(Integer.parseInt(unitItem.getUnitID()));

                drinksHelperDao.updateDrinks(items);
                getAdapter(getListDrinks());
                Toast.makeText(Drink_managerment.this, "Cập nhập thành công",
                        Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        dialog.show();
    }
    private void getAdapter(List<drinks> list){
        adapter.setmList(list);
        adapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(adapter);
    }

    private List getListNameUnit(String unitID) {
        List<unitProduct> list = new ArrayList<>();
        unitProduct UnitByID = mUnitDao.getByID(unitID);
        list.clear();
        List<unitProduct> listGetAll = getListNameUnit();
        list.add(UnitByID);
        for(unitProduct x: listGetAll){
            list.add(x);
        }
        return list;
    }
    public List<drinks> getListDrinks(){
        List<drinks> list = drinksHelperDao.getAll();
        return list;
    }
    private List getListNameUnit() {
        List<unitProduct> mlist = mUnitDao.getAll();
        List<unitProduct> mlist1 = new ArrayList<>();

        mlist1.clear();
        mlist1.add(new unitProduct("Chọn Đơn vị tính","-1"));
        for (unitProduct x: mlist){
            mlist1.add(x);
        }
        return mlist1;
    }
}