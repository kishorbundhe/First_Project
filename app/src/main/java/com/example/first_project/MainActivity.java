package com.example.first_project;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements Home.SendMessage {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());


        adapter.AddFragment(new Home(), "Home");
        adapter.AddFragment(new Graphic(), "Graphic");
        adapter.AddFragment(new Video(), "Video");
        viewPager.setAdapter(adapter);
        int limit = (adapter.getCount() > 1 ? adapter.getCount() - 1 : 1);
        viewPager.setOffscreenPageLimit(limit);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_panorama);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_video);
    }

    public void sendData(String message) {
        String tag = "android:switcher:" + R.id.viewpager + ":" + 1;
        Graphic f = (Graphic) getSupportFragmentManager().findFragmentByTag(tag);
        f.displayReceivedData(message);
    }
}
