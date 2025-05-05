package com.android.arka_resto;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MenuPagerAdapter extends FragmentStateAdapter {

    public MenuPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Create a new fragment for each tab
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putInt("menu_type", position);  // 0 for makanan, 1 for minuman
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2; // We have 2 tabs: Makanan and Minuman
    }
}