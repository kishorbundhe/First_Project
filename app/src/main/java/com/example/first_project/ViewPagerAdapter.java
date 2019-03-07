package com.example.first_project;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
public ViewPagerAdapter(FragmentManager fm){
    super(fm);
}
    private final List<Fragment> fragmentList=new ArrayList<>();
    private final List<String>listtitle=new ArrayList<>();

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).

        return fragmentList.get(position);
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        //return super.getPageTitle(position);

        return listtitle.get(position);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return fragmentList.size();
    }




    public void AddFragment(Fragment fragment, String title){
        fragmentList.add(fragment);
        listtitle.add(title);
    }
}
