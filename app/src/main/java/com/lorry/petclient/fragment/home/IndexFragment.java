package com.lorry.petclient.fragment.home;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.lorry.petclient.R;
import com.lorry.petclient.util.component.fragment.ShowItemFragment;
import com.lorry.petclient.util.http.HttpMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IndexFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IndexFragment extends androidx.fragment.app.Fragment {

    List<Fragment> fragmentList;

    public IndexFragment() {
        // Required empty public constructor
    }

    public static IndexFragment newInstance(Bundle args) {
        IndexFragment fragment = new IndexFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        fragmentList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_index, container, false);
        ScrollView scrollView = view.findViewById(R.id.fragment_index_scrollView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scrollView.setOnScrollChangeListener((view1, i, i1, i2, i3) -> {
                if (view1.getScrollY() <= 0) {
                    clearPosts();
                    getPosts();
                } else if (view1.getScrollY() + view1.getHeight() - view1.getPaddingTop() - view1.getPaddingBottom() == ((ScrollView) view1).getChildAt(0).getHeight()) {
                    System.out.println("bottom");
                }
            });
        }
        getPosts();
        return view;
    }

    private void clearPosts() {
        LinearLayout view_left = getView().findViewById(R.id.fragment_index_show_left);
        LinearLayout view_right = getView().findViewById(R.id.fragment_index_show_right);
        view_left.removeAllViews();
        view_right.removeAllViews();
    }

    private List<JSONObject> getPosts() {
        List<JSONObject> jsonObjects = new ArrayList<>();
        HttpMapper.getPostInfo(null, (res) -> {
            try {
                if (res.getInt("code") == 200) {
                    JSONArray data = res.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        jsonObjects.add((JSONObject) data.get(i));
                    }
                    fragmentList = new ArrayList<>();
                    for (JSONObject post : jsonObjects) {
                        fragmentList.add(ShowItemFragment.newInstance(post));
                    }
                    boolean is_left = true;
                    for (Fragment item : fragmentList) {
                        FragmentManager fragmentManager = getActivity().getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.add(is_left ? R.id.fragment_index_show_left : R.id.fragment_index_show_right, item);
                        fragmentTransaction.commit();
                        is_left = !is_left;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        return jsonObjects;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}