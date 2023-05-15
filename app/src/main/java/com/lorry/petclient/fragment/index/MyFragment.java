package com.lorry.petclient.fragment.index;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.lorry.petclient.R;
import com.lorry.petclient.fragment.my.CollectFragment;
import com.lorry.petclient.fragment.my.SupportFragment;
import com.lorry.petclient.util.http.HttpMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class MyFragment extends Fragment {

    private List<Fragment> fragments;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragments = new ArrayList<>();
        fragments.add(new CollectFragment());
        fragments.add(new SupportFragment());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        TabLayout mHeaderTabs = view.findViewById(R.id.tab_my_tab);
        ViewPager2 mViewPager = view.findViewById(R.id.fragment_my_viewPager);
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

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        HttpMapper.getUserInfo("user_10001", (res) -> {
            System.out.println(res.toString());
            try {
                if (res.getInt("code") == 200) {
                    JSONObject data = res.getJSONObject("data");
                    String name = data.getString("name");
                    String number = data.getString("number");
                    String description = data.getString("description");
                    getActivity().runOnUiThread(() -> {
                        TextView view_name = view.findViewById(R.id.fragment_my_view_name);
                        view_name.setText(name);
                        TextView view_number = view.findViewById(R.id.fragment_my_view_number);
                        view_number.setText(String.format("小宠吧号:%s", number));
                        TextView view_description = view.findViewById(R.id.fragment_my_view_description);
                        view_description.setText(description);
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        HttpMapper.getUserAvatar("user_10001", (res) -> {
            System.out.println(res);
            try {
                if (res.getInt("code") == 200) {
                    JSONObject data = res.getJSONObject("data");
                    String base64 = data.getString("base64");
                    getActivity().runOnUiThread(() -> {
                        ImageFilterView avatar = view.findViewById(R.id.fragment_my_image_avatar);
                        byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        avatar.setImageBitmap(decodedByte);
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        HttpMapper.getUserBackground("user_10001", (res) -> {
            System.out.println(res);
            try {
                if (res.getInt("code") == 200) {
                    JSONObject data = res.getJSONObject("data");
                    String base64 = data.getString("base64");
                    getActivity().runOnUiThread(() -> {
                        LinearLayout avatar = view.findViewById(R.id.fragment_my_image_background);
                        byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//                            avatar.setBackgroundResource();
//                            avatar.setImageBitmap(decodedByte);
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }


}