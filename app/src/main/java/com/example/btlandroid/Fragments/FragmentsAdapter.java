package com.example.btlandroid.Fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class FragmentsAdapter extends FragmentStatePagerAdapter {

    public FragmentsAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Home();
            case 1:
                return new Alarm();
            case 2:
                return new Weather();
            default:
                return new Home();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
