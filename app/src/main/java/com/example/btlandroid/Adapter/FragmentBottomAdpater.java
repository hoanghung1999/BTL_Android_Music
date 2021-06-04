package com.example.btlandroid.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.btlandroid.fragment.Fragment_CaNhan;
import com.example.btlandroid.fragment.Fragment_KhamPha;
import com.example.btlandroid.fragment.Fragment_User;
import com.example.btlandroid.fragment.Fragment_ZingChart;

public class FragmentBottomAdpater extends FragmentStatePagerAdapter {
    int numpage=4;
    public FragmentBottomAdpater(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
       switch (position){
           case 0: return new Fragment_CaNhan();
           case 1: return  new Fragment_KhamPha();
           case 2: return new Fragment_ZingChart();
           case 3: return new Fragment_User();
           default: return new Fragment_CaNhan();
       }
    }
    @Override
    public int getCount() {
        return numpage;
    }
}
