package com.example.barmanage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barmanage.Adapter.drinkNameAdapter;
import com.example.barmanage.Adapter.importedAdapter;
import com.example.barmanage.Db_helper.DrinksHelper;
import com.example.barmanage.Db_helper.ReceiptsHelper;
import com.example.barmanage.Db_helper.SalesslipHelper;
import com.example.barmanage.Db_helper.UnitDao;
import com.example.barmanage.modle.drinks;
import com.example.barmanage.modle.importedItem;
import com.example.barmanage.modle.unitProduct;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Cosume_management extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private FloatingActionButton btn_add;
    private importedAdapter adapter;
    private UnitDao mUnitDao;
    private ReceiptsHelper mReceiptsHelper;
    private DrinksHelper mDrinksHelper;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cosume_management);
        getSupportActionBar().setTitle("Tiêu thụ");
        mRecyclerView = findViewById(R.id.consume_recycleView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        //
        mUnitDao = new UnitDao(Cosume_management.this);
        mReceiptsHelper = new ReceiptsHelper(Cosume_management.this);
        mDrinksHelper = new DrinksHelper(Cosume_management.this);
        //
        btn_add = findViewById(R.id.consum_add);
        adapter = new importedAdapter(Cosume_management.this, new importedAdapter.Listener() {
            @Override
            public void sendData(importedItem items) {
                    updatItems(items);
            }
        });

        adapter.setmList(getList());

        mRecyclerView.setAdapter(adapter);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItems();
            }
        });


    }
    // chungcv: add items
    private void addItems(){
        Dialog dialog = new Dialog(Cosume_management.this,
                androidx.appcompat.R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        dialog.setContentView(R.layout.dialog_add_consume);
        Spinner mSpinner = dialog.findViewById(R.id.dialog_add_conSumeSpinner);
        EditText count = dialog.findViewById(R.id.dialog_add_conSume_Count);
        EditText date = dialog.findViewById(R.id.dialog_add_conSume_txtDate);
        TextView unitName = dialog.findViewById(R.id.dialog_add_conSume_unitName);
        EditText price = dialog.findViewById(R.id.dialog_add_conSume_Price);
        ImageView imageView = dialog.findViewById(R.id.dialog_add_conSume_imgDate);
        Button btn_Cancel = dialog.findViewById(R.id.dialog_add_conSume_cancel);
        Button btn_add = dialog.findViewById(R.id.dialog_add_conSume_add);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              getDate(date);
            }
        });
        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSpinner.getSelectedItemPosition()== 0 || count.getText().toString().isEmpty()
                        || price.getText().toString().isEmpty()|| date.getText().toString().isEmpty()){
                    if (mSpinner.getSelectedItemPosition()== 0){
                        Toast.makeText(Cosume_management.this, "Bạn chưa chọn tên sản phẩm"
                                , Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if(count.getText().toString().isEmpty()){
                        count.setError("Bạn chưa nhập số lượng");
                        return;
                    }
                    else if(price.getText().toString().isEmpty()){
                        price.setError("Bạn chưa nhập giá");
                        return;
                    }
                    else {
                        date.setError("Bạn chưa chọn ngày tháng");
                        return;
                    }
                }
                else{
                    Toast.makeText(Cosume_management.this, "Bạn đã thêm thành công"
                            , Toast.LENGTH_SHORT).show();
                }
                dialog.cancel();
            }
        });
        drinkNameAdapter adapter1 = new drinkNameAdapter
                (Cosume_management.this,R.layout.item_selected,setListDrinkName());
        mSpinner.setAdapter(adapter1);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                drinks items = (drinks) mSpinner.getSelectedItem();
                unitName.setText(getUnitName(String.valueOf(items.getUnitID())));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //

        //
        dialog.show();

    }

    private String getUnitName(String unitID) {
       unitProduct  unitItem = mUnitDao.getByID(unitID);
       return unitItem.getUnitName();
    }

    // chungcv: update items cho unit
    private void updatItems(importedItem item) {
        Dialog dialog = new Dialog(Cosume_management.this,
                androidx.constraintlayout.widget.R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        dialog.setContentView(R.layout.dialog_update_consume);
        Spinner mSpinner = dialog.findViewById(R.id.dialog_update_conSumeSpinner);
        EditText count = dialog.findViewById(R.id.dialog_update_conSume_Count);
        EditText date = dialog.findViewById(R.id.dialog_update_conSume_txtDate);
        TextView unitName = dialog.findViewById(R.id.dialog_update_conSume_unitName);
        EditText price = dialog.findViewById(R.id.dialog_update_conSume_Price);
        ImageView imageView = dialog.findViewById(R.id.dialog_update_conSume_imgDate);
        Button btn_Cancel = dialog.findViewById(R.id.dialog_update_conSume_cancel);
        Button btn_add = dialog.findViewById(R.id.dialog_update_conSume_add);
        // set gia tri
        count.setText(item.getUnitCount());
        date.setText(item.getDateAdd());
        price.setText(item.getDrinkPrice());

        //

        drinkNameAdapter adapter1 = new drinkNameAdapter(Cosume_management.this
                ,R.layout.item_selected,setListDrinkName());
        mSpinner.setAdapter(adapter1);
        // xu li click spiner
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                drinks items = (drinks) mSpinner.getSelectedItem();
                unitName.setText(getUnitName(String.valueOf(items.getUnitID())));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // todo...

            }
        });
        // su ly click ImageDateView
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDate(date);
            }
        });
        // xu ly add
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSpinner.getSelectedItemPosition()== 0 || count.getText().toString().isEmpty()
                        || price.getText().toString().isEmpty()|| date.getText().toString().isEmpty()){
                    if (mSpinner.getSelectedItemPosition()== 0){
                        Toast.makeText(Cosume_management.this, "Bạn chưa chọn tên sản phẩm"
                                , Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if(count.getText().toString().isEmpty()){
                        count.setError("Bạn chưa nhập số lượng");
                        return;
                    }
                    else if(price.getText().toString().isEmpty()){
                        price.setError("Bạn chưa nhập giá");
                        return;
                    }
                    else {
                        date.setError("Bạn chưa chọn ngày tháng");
                        return;
                    }
                }
                else{
                    Toast.makeText(Cosume_management.this, "Bạn đã thêm thành công"
                            , Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
                //
            }
        });
        //xu ly cancel
        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });


        dialog.show();
    }

    private void getDate(EditText editText) {
        DatePickerDialog pickerDialog = new DatePickerDialog(Cosume_management.this);
        pickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String dateChoice = year +"-"+(month+1)+"-"+dayOfMonth;
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
                try {
                    Date date = dateFormat.parse(dateChoice);
                    SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-mm-dd");
                    editText.setText(dateFormat1.format(date));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        pickerDialog.show();
    }

    private List<drinks> setListDrinkName() {
        List<drinks> list =mDrinksHelper.getAll();
        return list;
    }
    // chungcv setListData cho thang consume

    private List<importedItem> getList() {
        SalesslipHelper mSalesslipHelper = new SalesslipHelper(Cosume_management.this);
        List<importedItem> list = mSalesslipHelper.getAll();
        return list;
    }

    //chungcv: phần thể hiện phần search View
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem menuItem = menu.findItem(R.id.search_action);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Nhập ngày tháng để tìm kiếm");
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