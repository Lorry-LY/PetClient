package com.lorry.petclient.fragment.my;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
 * Use the {@link CollectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CollectFragment extends androidx.fragment.app.Fragment {

    List<Fragment> fragmentList;

    public CollectFragment() {
        // Required empty public constructor
    }

    public static CollectFragment newInstance(Bundle args) {
        CollectFragment fragment = new CollectFragment();
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
        View view = inflater.inflate(R.layout.fragment_collect, container, false);

        Boolean is_left = true;
        for (Fragment item : fragmentList) {
            FragmentManager fragmentManager = getActivity().getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(is_left ? R.id.fragment_collect_show_left : R.id.fragment_collect_show_right, item);
            fragmentTransaction.commit();
            is_left = !is_left;
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getPosts();
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
                    for (android.app.Fragment item : fragmentList) {
                        FragmentManager fragmentManager = getActivity().getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.add(is_left ? R.id.fragment_collect_show_left : R.id.fragment_collect_show_right, item);
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
}