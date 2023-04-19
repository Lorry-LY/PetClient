package com.lorry.petclient.fragment.index;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.lorry.petclient.R;
import com.lorry.petclient.fragment.home.CityFragment;
import com.lorry.petclient.fragment.home.ConcernFragment;
import com.lorry.petclient.fragment.home.IndexFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private List<Fragment> fragments;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragments = new ArrayList<>();
        fragments.add(new ConcernFragment());
        fragments.add(new IndexFragment());
        fragments.add(new CityFragment());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        TabLayout mHeaderTabs = view.findViewById(R.id.tab_home_tab);
        ViewPager2 mViewPager = view.findViewById(R.id.home_viewPager);
        mViewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return fragments.get(position);
            }

            @Override
            public int getItemCount() {
                return fragments.size();
            }
        });
        mViewPager.setOffscreenPageLimit(ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT);
        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mHeaderTabs.selectTab(mHeaderTabs.getTabAt(position));
            }
        });

        mHeaderTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int selectedTabPosition = mHeaderTabs.getSelectedTabPosition();
                mViewPager.setCurrentItem(selectedTabPosition, true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }

}