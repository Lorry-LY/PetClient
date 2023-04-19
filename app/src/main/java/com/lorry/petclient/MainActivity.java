package com.lorry.petclient;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lorry.petclient.fragment.index.HomeFragment;
import com.lorry.petclient.fragment.index.MessageFragment;
import com.lorry.petclient.fragment.index.MyFragment;
import com.lorry.petclient.fragment.index.NewFragment;
import com.lorry.petclient.fragment.index.ShopFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView mBottomNavigationView = findViewById(R.id.nav_bottom);
        ViewPager2 mViewPager = findViewById(R.id.main_viewPager);

        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new ShopFragment());
        fragments.add(new NewFragment());
        fragments.add(new MessageFragment());
        fragments.add(new MyFragment());

        mViewPager.setAdapter(new FragmentStateAdapter(this) {
            @Override
            public int getItemCount() {
                return fragments.size();
            }

            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return fragments.get(position);
            }
        });

        mViewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
//        mViewPager.setOffscreenPageLimit(1);
        mViewPager.registerOnPageChangeCallback(
                new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        super.onPageSelected(position);
                        int itemID = R.id.navigation_item1;
                        switch (position) {
                            case 0:
                                itemID = R.id.navigation_item1;
                                break;
                            case 1:
                                itemID = R.id.navigation_item2;
                                break;
                            case 2:
                                itemID = R.id.navigation_item3;
                                break;
                            case 3:
                                itemID = R.id.navigation_item4;
                                break;
                            case 4:
                                itemID = R.id.navigation_item5;
                                break;
                            default:
                                break;
                        }
                        mBottomNavigationView.setSelectedItemId(itemID);
                    }
                });
        mBottomNavigationView.setOnItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.navigation_item1:
                            mViewPager.setCurrentItem(0, true);
                            mViewPager.setUserInputEnabled(false);
                            return true;
                        case R.id.navigation_item2:
                            mViewPager.setCurrentItem(1, true);
                            mViewPager.setUserInputEnabled(true);
                            return true;
                        case R.id.navigation_item3:
                            mViewPager.setCurrentItem(2, true);
                            mViewPager.setUserInputEnabled(true);
                            return true;
                        case R.id.navigation_item4:
                            mViewPager.setCurrentItem(3, true);
                            mViewPager.setUserInputEnabled(true);
                            return true;
                        case R.id.navigation_item5:
                            mViewPager.setCurrentItem(4, true);
                            mViewPager.setUserInputEnabled(true);
                            return true;
                    }
                    return false;
                });

    }

}