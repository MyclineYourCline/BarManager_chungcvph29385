package com.example.barmanage.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.barmanage.FragmentManager.conSume_layout_fragment;
import com.example.barmanage.FragmentManager.imported_layout_fragment;

public class tabLayoutAdapter extends FragmentStatePagerAdapter {
    public tabLayoutAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1:
                return new conSume_layout_fragment();
            case 0:
                return new imported_layout_fragment();
            default:
                return new imported_layout_fragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
