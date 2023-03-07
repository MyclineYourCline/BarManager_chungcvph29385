package com.example.barmanage;

import static android.util.Log.d;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
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
import com.example.barmanage.modle.drinks;
import com.example.barmanage.modle.importedItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Impoted_management extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private importedAdapter adapter;
    private TextView mTotalPrice;
    private FloatingActionButton mButton_add;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impoted_management);
        getSupportActionBar().setTitle("Nhập hàng");
        mRecyclerView = findViewById(R.id.imported_recycleView);
        mTotalPrice = findViewById(R.id.imported_totalPrice);
        mButton_add = findViewById(R.id.imported_add);
         mTotalPrice = findViewById(R.id.imported_totalPrice);
        //
        adapter = new importedAdapter(Impoted_management.this, new importedAdapter.Listener() {
            @Override
            public void sendData(importedItem items) {
                updateImported(items);
            }
        });
        adapter.setmList(getmList());
        totalPrice();
        mRecyclerView.setAdapter(adapter);
        mButton_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addImported();
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0){
                    mButton_add.hide();
                    mTotalPrice.setVisibility(View.INVISIBLE);
                }
                else {
                    mButton_add.show();
                    mTotalPrice.setVisibility(View.VISIBLE);
                }
            }
        });


    }
    private void addImported(){
        Dialog dialog = new Dialog(Impoted_management.this,
                androidx.appcompat.R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        dialog.setContentView(R.layout.dialog_add_imported);
        Spinner mSpinner = dialog.findViewById(R.id.dialog_add_importedSpinner);
        EditText count = dialog.findViewById(R.id.dialog_add_imported_Count);
        EditText date = dialog.findViewById(R.id.dialog_add_imported_txtDate);
        TextView unitName = dialog.findViewById(R.id.dialog_add_imported_unitName);
        EditText price = dialog.findViewById(R.id.dialog_add_imported_Price);
        ImageView imageView = dialog.findViewById(R.id.dialog_add_imported_imgDate);
        Button btn_Cancel = dialog.findViewById(R.id.dialog_add_imported_cancel);
        Button btn_add = dialog.findViewById(R.id.dialog_add_imported_add);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Impoted_management.this);
                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String inputDate = year+"-"+(month+1)+"-"+dayOfMonth;
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
                        try {
                            Date date1 = dateFormat.parse(inputDate);
                            SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-mm-dd");
                            date.setText(dateFormat1.format(date1));
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        datePickerDialog.cancel();
                    }
                });
                datePickerDialog.show();
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
                        Toast.makeText(Impoted_management.this, "Bạn chưa chọn tên sản phẩm"
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
                    Toast.makeText(Impoted_management.this, "Bạn đã thêm thành công"
                            , Toast.LENGTH_SHORT).show();
                }
                dialog.cancel();
            }
        });
        drinkNameAdapter adapter1 = new drinkNameAdapter
                (Impoted_management.this,R.layout.item_selected,setListDrinkName());
        mSpinner.setAdapter(adapter1);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                drinks items = (drinks) mSpinner.getSelectedItem();
                unitName.setText(items.getUnitName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //

        //
        dialog.show();

    }

    private void updateImported(importedItem item) {

        Dialog dialog = new Dialog(Impoted_management.this,
                androidx.appcompat.R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        dialog.setContentView(R.layout.dialog_update_imported);
        Spinner mSpinner = dialog.findViewById(R.id.dialog_update_importedSpinner);
        EditText count = dialog.findViewById(R.id.dialog_update_imported_Count);
        EditText date = dialog.findViewById(R.id.dialog_update_imported_txtDate);
        TextView unitName = dialog.findViewById(R.id.dialog_update_imported_unitName);
        EditText price = dialog.findViewById(R.id.dialog_update_imported_Price);
        ImageView imageView = dialog.findViewById(R.id.dialog_update_imported_imgDate);
        Button btn_Cancel = dialog.findViewById(R.id.dialog_update_imported_cancel);
        Button btn_add = dialog.findViewById(R.id.dialog_update_imported_add);
        drinkNameAdapter adapter1 = new drinkNameAdapter
                (Impoted_management.this,R.layout.item_selected,setListDrinkName(item.getDrinkName()));
        mSpinner.setAdapter(adapter1);
        mSpinner.setSelection(1);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                //todo
                dialog.cancel();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Impoted_management.this);
                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String inputDate = year+"-"+(month+1)+"-"+dayOfMonth;
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
                        try {
                            Date date1 = dateFormat.parse(inputDate);
                            SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-mm-dd");
                            date.setText(dateFormat1.format(date1));
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        datePickerDialog.cancel();
                    }
                });
                datePickerDialog.show();
            }
        });
        count.setText(item.getUnitCount());
        date.setText(item.getDateAdd());
        unitName.setText("lon");
        price.setText(item.getDrinkPrice());
        dialog.show();

    }

    private List<drinks> setListDrinkName() {
        List<drinks> list = new ArrayList<>();
        list.add(new drinks("Chọn tên đồ uống","đơn vị"));
        list.add(new drinks("cocacola","lon"));
        list.add(new drinks("number one","chai"));
        list.add(new drinks("dau tay","chai"));
        list.add(new drinks("sting","thung"));
        return list;
    }
    private List<drinks> setListDrinkName(String item) {
        List<drinks> list = new ArrayList<>();
        list.add(new drinks("Chọn tên đồ uống","đơn vị"));
        list.add(new drinks(item,"lon"));
        list.add(new drinks("cocacola","lon"));
        list.add(new drinks("number one","chai"));
        list.add(new drinks("dau tay","chai"));
        list.add(new drinks("sting","thung"));
        return list;
    }

    private List<importedItem> getmList() {
        List<importedItem> list = new ArrayList<>();
        list.add(new importedItem("bo hoc","12000", "3", "21/11/2002"));
        list.add(new importedItem("bo hoc","300", "5", "23/01/2030"));
        list.add(new importedItem("bo askda","200", "1", "21/03/2015"));
        list.add(new importedItem("bo hoc","12000", "1", "21/11/2002"));
        list.add(new importedItem("bo hoc","300", "2", "23/01/2030"));
        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem menuItem = menu.findItem(R.id.search_action);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) menuItem.getActionView();
        searchView.setQueryHint("Nhập ngày tháng muốn tìm....");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                totalPrice();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                    totalPrice();
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
    private void totalPrice (){
        int totalPrice = 0;
        List<importedItem> list = adapter.getmList();
        if (list == null){
            mTotalPrice.setText("Tổng tiền: "+totalPrice+" VND");
            return;
        }
        for (importedItem x: list){
            totalPrice += x.sumMonny();
        }
        mTotalPrice.setText("Tổng tiền: "+totalPrice+" VND");
    }
}