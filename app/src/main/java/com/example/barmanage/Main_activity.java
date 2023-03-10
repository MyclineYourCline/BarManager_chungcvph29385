package com.example.barmanage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import com.example.barmanage.Db_helper.myDB;

import java.util.ArrayList;
import java.util.List;

public class Main_activity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private userFaceAdapter adapter;
    private Intent intent;
    private myDB mMyDB;
    private SQLiteDatabase database;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.main_recycleView);
        mMyDB = new myDB(Main_activity.this);
        database = mMyDB.getWritableDatabase();
        database.close();

        adapter = new userFaceAdapter(this, new userFaceAdapter.ClickItem() {
            @Override
            public void senData(main_userFace items) {
              switch (items.getTextUser().trim()){
                  case "Tên đơn vị":
                      intent = new Intent(Main_activity.this,Unit_management.class);
                      startActivity(intent);
                      break;
                  case "Tên đồ uống":
                      intent = new Intent(Main_activity.this,Drink_managerment.class);
                      startActivity(intent);
                      break;
                  case "Nhập hàng":
                      intent = new Intent(Main_activity.this,Impoted_management.class);
                      startActivity(intent);
                      break;
                  case "Tiêu thụ":
                      intent = new Intent(Main_activity.this,Cosume_management.class);
                      startActivity(intent);
                      break;
                  case "Quản lý":
                      intent = new Intent(Main_activity.this,Management_home.class);
                      startActivity(intent);
                      break;
                  case "Hết hàng":
                      intent = new Intent(Main_activity.this,Out_of_stock_management.class);
                      startActivity(intent);
                      break;
                  case "Doanh thu":
                      intent = new Intent(Main_activity.this,Revennu_management.class);
                      startActivity(intent);
                      break;
                  case "Chi phí":
                      intent = new Intent(Main_activity.this,Expence_management.class);
                      startActivity(intent);
                      break;
                  case "Lợi nhuận":
                      intent = new Intent(Main_activity.this,Profit_management.class);
                      startActivity(intent);
                      break;
                  default:
                      intent = new Intent(Main_activity.this,Selling_management.class);
                      startActivity(intent);
                      break;
              }
            }
        });
        adapter.setList(getList());
        mRecyclerView.setAdapter(adapter);

    }
    private List<main_userFace> getList() {
        List<main_userFace> list = new ArrayList<>();
        list.add(new main_userFace(R.drawable.unit_img,"Tên đơn vị"));
        list.add(new main_userFace(R.drawable.menu_icon,"Tên đồ uống"));
        list.add(new main_userFace(R.drawable.imported_icon,"Nhập hàng"));
        list.add(new main_userFace(R.drawable.sharp_filter,"Tiêu thụ"));
        list.add(new main_userFace(R.drawable.baseline_home_24,"Quản lý"));
        list.add(new main_userFace(R.drawable.baseline_filter_alt_off_24,"Hết hàng"));
        list.add(new main_userFace(R.drawable.baseline_leaderboard_24,"Doanh thu"));
        list.add(new main_userFace(R.drawable.baseline_calculate_24,"Chi phí"));
        list.add(new main_userFace(R.drawable.baseline_favorite_24,"Lợi nhuận"));
        list.add(new main_userFace(R.drawable.baseline_flash_on_24,"Bán chạy"));

    return list;
    }
}