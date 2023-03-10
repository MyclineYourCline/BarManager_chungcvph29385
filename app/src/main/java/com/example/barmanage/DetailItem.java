package com.example.barmanage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.barmanage.Adapter.tabLayoutAdapter;
import com.example.barmanage.modle.importedItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class DetailItem extends AppCompatActivity {
    private Intent mIntent;
    private Bundle mBundle;
    private ViewPager mViewPager;
    private tabLayoutAdapter adapter;
    private BottomNavigationView mBTN;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_item);
        mIntent = getIntent();
        importedItem ItemReceive = (importedItem) mIntent.getSerializableExtra("item");
//        getSupportActionBar().setTitle("Chi tiết về: "+ItemReceive.getDrinkName());
        mViewPager = findViewById(R.id.detail_ViewPager);
        mBTN = findViewById(R.id.detail_btnN);
        adapter = new tabLayoutAdapter(getSupportFragmentManager()
                , FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewPager.setAdapter(adapter);
        mBTN.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_bt_conSume:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.menu_bt_importItem:
                        mViewPager.setCurrentItem(0);
                        break;
                }
                return true;
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                    switch (position){
                        case 1:
                            mBTN.getMenu().findItem(R.id.menu_bt_conSume).setChecked(true);
                            break;
                        case 0:
                            mBTN.getMenu().findItem(R.id.menu_bt_importItem).setChecked(true);
                            break;
                    }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}