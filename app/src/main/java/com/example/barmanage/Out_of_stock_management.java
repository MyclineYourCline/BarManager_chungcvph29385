package com.example.barmanage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Out_of_stock_management extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_of_stock_management);
        getSupportActionBar().setTitle("Hết hàng");
    }
}